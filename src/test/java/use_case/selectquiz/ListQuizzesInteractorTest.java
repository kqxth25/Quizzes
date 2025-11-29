package use_case.selectquiz;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ListQuizzesInteractorTest {

    @Test
    void successTest() {
        ListQuizzesDataAccessInterface successDataAccess = new ListQuizzesDataAccessInterface() {
            @Override
            public List<QuizItemDto> list() {
                return List.of();}
        };

        ListQuizzesOutputBoundary successPresenter = new ListQuizzesOutputBoundary() {
            @Override
            public void present(ListQuizzesOutputData outputData) {
                assertNotNull(outputData);
                assertNotNull(outputData.getQuizzes());
            }

            @Override
            public void presentFailure(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };
        ListQuizzesInputBoundary interactor =
                new ListQuizzesInteractor(successDataAccess, successPresenter);
        interactor.execute();
    }

    @Test
    void failureTest() {
        ListQuizzesDataAccessInterface failingDataAccess = new ListQuizzesDataAccessInterface() {
            @Override
            public List<QuizItemDto> list() {
                throw new RuntimeException("Simulated failure");
            }
        };

        ListQuizzesOutputBoundary failurePresenter = new ListQuizzesOutputBoundary() {
            @Override
            public void present(ListQuizzesOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentFailure(String errorMessage) {
                assertEquals("Failed to load quizzes.", errorMessage);
            }
        };
        ListQuizzesInputBoundary interactor =
                new ListQuizzesInteractor(failingDataAccess, failurePresenter);
        interactor.execute();
    }
}
