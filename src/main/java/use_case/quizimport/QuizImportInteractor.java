package use_case.quizimport;

import entity.Question;
import entity.Quiz;
import interface_adapter.quizimport.CategoryMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuizImportInteractor implements QuizImportInputBoundary {

    private final QuizImportOutputBoundary presenter;
    private final QuizRepository_import repository;
    private final OkHttpClient client = new OkHttpClient();

    public QuizImportInteractor(QuizImportOutputBoundary presenter, QuizRepository_import repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    @Override
    public void importQuiz(QuizImportInputData inputData) {
        try {
            String url = buildApiUrl(inputData);
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    presenter.presentError("HTTP error: " + response.code());
                    return;
                }

                String body = response.body().string();
                JSONObject json = new JSONObject(body);
                int responseCode = json.optInt("response_code", -1);
                if (responseCode != 0) {
                    presenter.presentError("API returned response_code=" + responseCode);
                    return;
                }

                JSONArray results = json.getJSONArray("results");
                List<Question> questions = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject q = results.getJSONObject(i);
                    String questionText = htmlDecode(q.getString("question"));
                    String correct = htmlDecode(q.getString("correct_answer"));
                    JSONArray incorrectArr = q.getJSONArray("incorrect_answers");
                    List<String> incorrect = new ArrayList<>();
                    for (int j = 0; j < incorrectArr.length(); j++) {
                        incorrect.add(htmlDecode(incorrectArr.getString(j)));
                    }
                    questions.add(new Question(questionText, correct, incorrect));
                }

                Quiz quiz = new Quiz(
                        inputData.getName(),
                        inputData.getAmount(),
                        inputData.getCategoryName(),
                        inputData.getDifficulty(),
                        inputData.getType(),
                        questions
                );

                repository.save(quiz);

                presenter.presentSuccess(quiz);
            }
        } catch (Exception e) {
            presenter.presentError("Import failed: " + e.getMessage());
        }
    }

    private String buildApiUrl(QuizImportInputData d) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://opentdb.com/api.php?amount=").append(d.getAmount());

        // category
        if (!"Any".equalsIgnoreCase(d.getCategoryName())) {
            int id = CategoryMapper.getCategoryId(d.getCategoryName());
            if (id > 0) sb.append("&category=").append(id);
        }

        // difficulty
        if (d.getDifficulty() != null && !"any".equalsIgnoreCase(d.getDifficulty())) {
            sb.append("&difficulty=").append(d.getDifficulty().toLowerCase());
        }

        // type
        if (d.getType() != null && !"any".equalsIgnoreCase(d.getType())) {
            String t = d.getType().toLowerCase();
            if (t.equals("boolean") || t.equals("true / false") || t.equals("true/false")) {
                sb.append("&type=boolean");
            } else if (t.startsWith("multi")) {
                sb.append("&type=multiple");
            } else {
                sb.append("&type=").append(t);
            }
        }

        return sb.toString();
    }

    private String htmlDecode(String s) {
        return s.replaceAll("&#039;", "'")
                .replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&")
                .replaceAll("&ldquo;", "\"")
                .replaceAll("&rdquo;", "\"");
    }
}
