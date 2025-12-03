package use_case.quizimport;

import entity.Question;
import entity.Quiz;
import okhttp3.*;
import okio.Timeout;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;


class QuizImportInteractorTest {

    private static class CapturingRepository implements QuizRepository_import {

        final AtomicReference<Quiz> savedQuiz = new AtomicReference<>();
        int saveCallCount = 0;
        int deleteCallCount = 0;

        @Override
        public void save(Quiz quiz) {
            saveCallCount++;
            savedQuiz.set(quiz);
        }

        @Override
        public List<Quiz> getAll() {
            return new ArrayList<>();
        }

        @Override
        public Quiz getByName(String name) {
            return null;
        }

        @Override
        public void delete(String name) {
            deleteCallCount++;
        }
    }

    private static class CapturingPresenter implements QuizImportOutputBoundary {

        final AtomicReference<Quiz> successQuiz = new AtomicReference<>();
        final AtomicReference<String> errorMessage = new AtomicReference<>();

        @Override
        public void presentSuccess(Quiz quiz) {
            successQuiz.set(quiz);
        }

        @Override
        public void presentError(String message) {
            errorMessage.set(message);
        }
    }

    private static Response buildResponse(int code, String body) {
        Request req = new Request.Builder()
                .url("https://example.com/api")
                .build();
        ResponseBody responseBody =
                ResponseBody.create(body, MediaType.parse("application/json"));
        return new Response.Builder()
                .request(req)
                .protocol(Protocol.HTTP_1_1)
                .code(code)
                .message("")
                .body(responseBody)
                .build();
    }

    private static class StubCall implements Call {

        private final Response response;
        private final RuntimeException toThrow;

        StubCall(Response response) {
            this.response = response;
            this.toThrow = null;
        }

        StubCall(RuntimeException ex) {
            this.response = null;
            this.toThrow = ex;
        }

        @Override
        public Request request() {
            if (response != null) {
                return response.request();
            }
            return new Request.Builder().url("https://example.com").build();
        }

        @Override
        public Response execute() throws IOException {
            if (toThrow != null) {
                throw toThrow;
            }
            return response;
        }

        @Override
        public void enqueue(Callback responseCallback) {
            throw new UnsupportedOperationException("Not needed in tests");
        }

        @Override
        public void cancel() { }

        @Override
        public boolean isExecuted() {
            return false;
        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public Timeout timeout() {
            return new Timeout();
        }

        @Override
        public Call clone() {
            return this;
        }
    }

    private static class StubOkHttpClient extends OkHttpClient {
        private final Call call;

        StubOkHttpClient(Call call) {
            this.call = call;
        }

        @Override
        public Call newCall(Request request) {
            return call;
        }
    }

    private static QuizImportInteractor createInteractorWithClient(
            OkHttpClient client,
            QuizImportOutputBoundary presenter,
            QuizRepository_import repository
    ) {
        QuizImportInteractor interactor = new QuizImportInteractor(presenter, repository);
        try {
            Field f = QuizImportInteractor.class.getDeclaredField("client");
            f.setAccessible(true);
            f.set(interactor, client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return interactor;
    }


    @Test
    void importQuiz_httpError_callsPresenterError() {
        Response response = buildResponse(500, "{}");
        StubOkHttpClient client = new StubOkHttpClient(new StubCall(response));

        CapturingPresenter presenter = new CapturingPresenter();
        CapturingRepository repository = new CapturingRepository();

        QuizImportInteractor interactor =
                createInteractorWithClient(client, presenter, repository);

        QuizImportInputData input = new QuizImportInputData(
                "HTTP Error Quiz", 5, "Any", "Any", "Any"
        );

        interactor.importQuiz(input);

        assertEquals("HTTP error: 500", presenter.errorMessage.get());
        assertNull(presenter.successQuiz.get());
        assertEquals(0, repository.saveCallCount);
    }


    @Test
    void importQuiz_apiNonZeroResponseCode_callsPresenterError() {
        String body = """
                {
                  "response_code": 1,
                  "results": []
                }
                """;
        Response response = buildResponse(200, body);
        StubOkHttpClient client = new StubOkHttpClient(new StubCall(response));

        CapturingPresenter presenter = new CapturingPresenter();
        CapturingRepository repository = new CapturingRepository();

        QuizImportInteractor interactor =
                createInteractorWithClient(client, presenter, repository);

        QuizImportInputData input = new QuizImportInputData(
                "NonZeroCode Quiz",
                10,
                "History",
                "Medium",
                "Multiple Choice"
        );

        interactor.importQuiz(input);

        assertEquals("API returned response_code=1", presenter.errorMessage.get());
        assertNull(presenter.successQuiz.get());
        assertEquals(0, repository.saveCallCount);
    }


    @Test
    void importQuiz_success_savesQuizAndPresentsSuccess_withHtmlDecoded() {
        String body = """
                {
                  "response_code": 0,
                  "results": [
                    {
                      "question": "2 &amp; 3 &#039;Q&#039; &ldquo;test&rdquo;",
                      "correct_answer": "Correct &quot;Ans&quot; &amp; more",
                      "incorrect_answers": [
                        "Wrong1 &ldquo;B&rdquo;",
                        "Wrong2 &#039;x&#039;"
                      ]
                    }
                  ]
                }
                """;
        Response response = buildResponse(200, body);
        StubOkHttpClient client = new StubOkHttpClient(new StubCall(response));

        CapturingPresenter presenter = new CapturingPresenter();
        CapturingRepository repository = new CapturingRepository();

        QuizImportInteractor interactor =
                createInteractorWithClient(client, presenter, repository);

        QuizImportInputData input = new QuizImportInputData(
                "Decoded Quiz",
                1,
                "Science",
                "hard",
                "True / False"
        );

        interactor.importQuiz(input);

        assertEquals(1, repository.saveCallCount);
        Quiz saved = repository.savedQuiz.get();
        assertNotNull(saved);

        assertSame(saved, presenter.successQuiz.get());
        assertNull(presenter.errorMessage.get());

        List<Question> questions = saved.getQuestions();
        assertEquals(1, questions.size());

        Question q = questions.getFirst();

        assertEquals("2 & 3 'Q' \"test\"", q.getQuestionText());
        assertEquals("Correct \"Ans\" & more", q.getCorrectAnswer());
        assertEquals("Wrong1 \"B\"", q.getIncorrectAnswers().getFirst());
        assertEquals("Wrong2 'x'", q.getIncorrectAnswers().get(1));
    }


    @Test
    void importQuiz_whenException_presentsImportFailedError() {

        StubCall failingCall = new StubCall(new RuntimeException("boom"));
        StubOkHttpClient client = new StubOkHttpClient(failingCall);

        CapturingPresenter presenter = new CapturingPresenter();
        CapturingRepository repository = new CapturingRepository();

        QuizImportInteractor interactor =
                createInteractorWithClient(client, presenter, repository);

        QuizImportInputData input = new QuizImportInputData(
                "Exception Quiz",
                3,
                "Any",
                "Easy",
                "short"
        );

        interactor.importQuiz(input);

        String msg = presenter.errorMessage.get();
        assertNotNull(msg);
        assertTrue(msg.startsWith("Import failed: "));
        assertTrue(msg.contains("boom"));

        assertNull(presenter.successQuiz.get());
        assertEquals(0, repository.saveCallCount);
    }
}
