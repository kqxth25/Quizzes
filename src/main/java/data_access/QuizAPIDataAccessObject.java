package data_access;

import entity.Question;
import entity.Quiz;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class QuizAPIDataAccessObject {

    private final OkHttpClient client = new OkHttpClient();

    public Quiz fetchQuiz(String quizName, int amount, int categoryId, String difficulty, String type) throws Exception {

        StringBuilder url = new StringBuilder("https://opentdb.com/api.php?amount=" + amount);

        if (categoryId != -1) url.append("&category=").append(categoryId);
        if (!difficulty.equals("any")) url.append("&difficulty=").append(difficulty.toLowerCase());
        if (!type.equals("any")) url.append("&type=").append(type);

        Request request = new Request.Builder().url(url.toString()).build();
        Response response = client.newCall(request).execute();

        JSONObject json = new JSONObject(response.body().string());
        JSONArray results = json.getJSONArray("results");

        List<Question> questionList = new ArrayList<>();
        String apiCategory = "";
        String apiDifficulty = "";
        String apiType = "";

        for (int i = 0; i < results.length(); i++) {
            JSONObject q = results.getJSONObject(i);

            if (i == 0) {
                apiCategory = q.getString("category");
                apiDifficulty = q.getString("difficulty");
                apiType = q.getString("type").equals("boolean") ? "Boolean" : "Multiple Choice";
            }

            String text = q.getString("question");
            String correct = q.getString("correct_answer");

            List<String> incorrect = new ArrayList<>();
            JSONArray incArr = q.getJSONArray("incorrect_answers");
            for (int j = 0; j < incArr.length(); j++)
                incorrect.add(incArr.getString(j));

            questionList.add(new Question(text, correct, incorrect));
        }

        return new Quiz(
                quizName,           // user-chosen name
                amount,             // EXACT number of questions the user entered
                apiCategory,        // actual API category
                apiDifficulty,      // actual API difficulty
                apiType,            // derived type (e.g., Boolean / Multiple Choice)
                questionList        // full list of questions
        );
    }
}
