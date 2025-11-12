package use_case;

import entity.Quiz;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.List;

public class QuizImport {

    private static final String API_URL = "https://opentdb.com/api.php?";
    private static final String RESPONSE_CODE = "response_code";
    private static final int SUCCESS_CODE = 0;

    public Quiz Import(String name, int amount, String category, String difficulty, String type) {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("%samount=%s&category=%s&difficulty=%s&type=%s", API_URL, amount, category,
                        difficulty, type))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(RESPONSE_CODE) == SUCCESS_CODE) {
                JSONArray results = responseBody.getJSONArray("results");
                List<HashMap<String, Object>> questionList = new ArrayList<>();

                for (int i = 0; i < results.length(); i++) {
                    JSONObject qObj = results.getJSONObject(i);
                    HashMap<String, Object> questionMap = new HashMap<>();
                    questionMap.put("question", htmlDecode(qObj.getString("question")));
                    questionMap.put("correct_answer", qObj.getString("correct_answer"));

                    JSONArray incorrect = qObj.getJSONArray("incorrect_answers");
                    List<String> incorrectList = new ArrayList<>();
                    for (int j = 0; j < incorrect.length(); j++) {
                        incorrectList.add(incorrect.getString(j));
                    }
                    questionMap.put("incorrect_answers", incorrectList);
                    questionList.add(questionMap);
                }

                // Create Quiz object with parsed data
                return new Quiz(name, amount, category, difficulty, type, questionList);

            } else {
                throw new RuntimeException("API returned error code: " + responseBody.getInt(RESPONSE_CODE));
            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException("Failed to fetch quiz: " + e.getMessage(), e);
        }
    }

    // Helper method for HTML entity decoding
    private String htmlDecode(String text) {
        return text.replaceAll("&#039;", "'")
                .replaceAll("&quot;", "\"")
                .replaceAll("&amp;", "&");
    }
}