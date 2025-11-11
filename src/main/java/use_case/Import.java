package use_case;

import entity.Quiz;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuizImport {

    private static final String API_URL = "https://opentdb.com/api.php?";
    private static final String RESPONSE_CODE = "response_code";
    private static final int SUCCESS_CODE = 0;

    public Quiz Import(int amount, String category, String difficulty, String type) {
        Quiz quiz = new Quiz(amount, category, difficulty, type,);
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("%samount=%s&category=%s&difficulty=%s&type=%s", API_URL, amount, category,
                        difficulty, type))
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(RESPONSE_CODE) == SUCCESS_CODE) {
                final JSONObject questions =
            } else {
                throw new RuntimeException("Grade could not be found for course: " + course
                        + " and username: " + username);
            }
        } catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }
