package data_access;

import entity.Quiz;
import entity.Question;
import java.util.ArrayList;
import java.util.List;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuizApiDataAccessObject {

    private static final String API_URL = "https://opentdb.com/api.php?";

    public Quiz fetchQuiz(String name, int amount, String category, String difficulty, String type) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format("%samount=%s&category=%s&difficulty=%s&type=%s",
                        API_URL, amount, category, difficulty, type))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new Exception("API call failed: " + response);

            JSONObject responseBody = new JSONObject(response.body().string());
            JSONArray results = responseBody.getJSONArray("results");
            List<Question> questions = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject qObj = results.getJSONObject(i);
                JSONArray incorrect = qObj.getJSONArray("incorrect_answers");
                List<String> incorrectList = new ArrayList<>();
                for (int j = 0; j < incorrect.length(); j++) {
                    incorrectList.add(incorrect.getString(j));
                }
                questions.add(new Question(
                        htmlDecode(qObj.getString("question")),
                        qObj.getString("correct_answer"),
                        incorrectList
                ));
            }
            return new Quiz(name, amount, category, difficulty, type, questions);
        }
    }

    private String htmlDecode(String text) {
        return text.replaceAll("&#039;", "'")
                .replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&");
    }
}
