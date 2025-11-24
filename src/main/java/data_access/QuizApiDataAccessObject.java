package data_access;

import entity.Question;
import entity.Quiz;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.quizimport.QuizApiDataAccess;

import java.util.ArrayList;
import java.util.List;

public class QuizApiDataAccessObject implements QuizApiDataAccess {

    private static final String BASE_URL = "https://opentdb.com/api.php?";

    @Override
    public Quiz requestQuiz(String name, int amount, String category, String difficulty, String type) {
        String url = String.format("%samount=%d&category=%s&difficulty=%s&type=%s",
                BASE_URL, amount, category, difficulty, type);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            JSONObject json = new JSONObject(response.body().string());
            JSONArray results = json.getJSONArray("results");

            List<Question> questions = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject q = results.getJSONObject(i);

                JSONArray incorrectJSON = q.getJSONArray("incorrect_answers");
                List<String> incorrectList = new ArrayList<>();
                for (int j = 0; j < incorrectJSON.length(); j++)
                    incorrectList.add(htmlDecode(incorrectJSON.getString(j)));

                Question question = new Question(
                        htmlDecode(q.getString("question")),
                        htmlDecode(q.getString("correct_answer")),
                        incorrectList,
                        q.getString("type"),
                        q.getString("category"),
                        q.getString("difficulty")
                );
                questions.add(question);
            }

            return new Quiz(name, questions);

        } catch (Exception e) {
            throw new RuntimeException("API Quiz fetch failed: " + e.getMessage());
        }
    }

    private String htmlDecode(String text) {
        return text.replaceAll("&#039;", "'")
                .replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&")
                .replaceAll("&pi;", "π");
    }
}