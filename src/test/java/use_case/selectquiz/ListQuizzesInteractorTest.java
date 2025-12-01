package use_case.selectquiz;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ListQuizzesInteractorTest {

    @Test
    void successNoFilter_returnsAllQuizzes() {
        ListQuizzesDataAccessInterface dataAccess = () -> List.of(
                new QuizItemDto("Q1", "History", "easy", "multiple"),
                new QuizItemDto("Q2", "Science", "medium", "boolean"),
                new QuizItemDto("Q3", "History", "hard", "multiple")
        );

        AtomicReference<ListQuizzesOutputData> captured = new AtomicReference<>();
        AtomicBoolean failureCalled = new AtomicBoolean(false);

        ListQuizzesOutputBoundary presenter = new ListQuizzesOutputBoundary() {
            @Override
            public void present(ListQuizzesOutputData outputData) {
                captured.set(outputData);
            }

            @Override
            public void presentFailure(String errorMessage) {
                failureCalled.set(true);
            }
        };

        ListQuizzesInputBoundary interactor =
                new ListQuizzesInteractor(dataAccess, presenter);

        ListQuizzesInputData input =
                new ListQuizzesInputData("Any", "Any", "Any");

        interactor.execute(input);

        assertFalse(failureCalled.get(), "Failure presenter should not be called");
        assertNotNull(captured.get(), "OutputData should not be null");
        assertNotNull(captured.get().getQuizzes(), "Quiz list should not be null");
        assertEquals(3, captured.get().getQuizzes().size(),
                "All quizzes should be returned when filters are Any");
    }

    @Test
    void successWithFilters_returnsOnlyMatchingQuizzes() {
        ListQuizzesDataAccessInterface dataAccess = () -> List.of(
                new QuizItemDto("Q1", "History", "easy", "multiple"),
                new QuizItemDto("Q2", "Science", "medium", "boolean"),
                new QuizItemDto("Q3", "History", "hard", "multiple")
        );

        AtomicReference<ListQuizzesOutputData> captured = new AtomicReference<>();
        AtomicBoolean failureCalled = new AtomicBoolean(false);

        ListQuizzesOutputBoundary presenter = new ListQuizzesOutputBoundary() {
            @Override
            public void present(ListQuizzesOutputData outputData) {
                captured.set(outputData);
            }

            @Override
            public void presentFailure(String errorMessage) {
                failureCalled.set(true);
            }
        };

        ListQuizzesInputBoundary interactor =
                new ListQuizzesInteractor(dataAccess, presenter);

        ListQuizzesInputData input =
                new ListQuizzesInputData("History", "easy", "multiple");

        interactor.execute(input);

        assertFalse(failureCalled.get(), "Failure presenter should not be called");
        assertNotNull(captured.get());
        List<QuizItemDto> result = captured.get().getQuizzes();
        assertEquals(1, result.size(), "Only one quiz should match the filters");
        assertEquals("Q1", result.getFirst().getTitle());
        assertEquals("History", result.getFirst().getCategory());
        assertEquals("easy", result.getFirst().getDifficulty());
        assertEquals("multiple", result.getFirst().getType());
    }

    @Test
    void failurePath_callsPresenterFailure() {
        ListQuizzesDataAccessInterface failingDataAccess =
                () -> { throw new RuntimeException("Simulated failure"); };

        AtomicBoolean successCalled = new AtomicBoolean(false);
        AtomicReference<String> errorCaptured = new AtomicReference<>();

        ListQuizzesOutputBoundary presenter = new ListQuizzesOutputBoundary() {
            @Override
            public void present(ListQuizzesOutputData outputData) {
                successCalled.set(true);
            }

            @Override
            public void presentFailure(String errorMessage) {
                errorCaptured.set(errorMessage);
            }
        };

        ListQuizzesInputBoundary interactor =
                new ListQuizzesInteractor(failingDataAccess, presenter);

        ListQuizzesInputData input =
                new ListQuizzesInputData("Any", "Any", "Any");

        interactor.execute(input);

        assertFalse(successCalled.get(), "Success presenter should not be called");
        assertEquals("Failed to load quizzes.", errorCaptured.get());
    }
}
