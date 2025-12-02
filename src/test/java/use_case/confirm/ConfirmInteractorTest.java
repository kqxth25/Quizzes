package use_case.confirm;

import interface_adapter.quiz.QuizState;
import use_case.quiz.QuizStateProvider;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests for ConfirmInteractor:
 *  - prepareConfirmation() should detect incomplete questions except question 9
 *  - forceSubmit() should compute score and call presenter methods
 */
class ConfirmSubmitTest {

    /**
     * Fake Presenter to capture output from ConfirmInteractor
     */
    private static class FakePresenter implements ConfirmOutputBoundary {
        ConfirmResponseModel receivedResponse;
        Double receivedScore;
        boolean showResultCalled = false;

        @Override
        public void presentConfirmation(ConfirmResponseModel response) {
            this.receivedResponse = response;
        }

        @Override
        public void showFinalScore(double scorePct) {
            this.receivedScore = scorePct;
        }

        @Override
        public void showResult() {
            this.showResultCalled = true;
        }
    }

    /**
     * Fake QuizStateProvider providing a static QuizState
     */
    private static class FakeQuizStateProvider implements QuizStateProvider {

        private final QuizState state;

        FakeQuizStateProvider(QuizState state) {
            this.state = state;
        }

        @Override
        public QuizState getQuizState() {
            return state;
        }
    }

    @Test
    void testPrepareConfirmationDetectsIncompleteQuestions() {

        QuizState state = new QuizState(12);

        state.setAnswer(0, 1);
        state.setAnswer(1, -1);
        state.setAnswer(2, 2);
        state.setAnswer(9, -1);

        FakePresenter presenter = new FakePresenter();
        FakeQuizStateProvider provider = new FakeQuizStateProvider(state);

        ConfirmInteractor interactor = new ConfirmInteractor(provider, presenter);

        interactor.prepareConfirmation();

        assertNotNull(presenter.receivedResponse);

        List<Integer> incomplete = presenter.receivedResponse.getIncompleteQuestionIndices();

        assertFalse(incomplete.contains(9));

        assertTrue(incomplete.contains(1));

        assertEquals(9, incomplete.size());

        boolean allCompleted = presenter.receivedResponse.isAllCompleted();
        assertFalse(allCompleted);

    }

    @Test
    void testForceSubmitComputesScoreCorrectly() {

        QuizState state = new QuizState(5);

        state.setCorrectAnswers(new int[]{1, 2, 3, 0, 2});

        state.setAnswer(0, 1);
        state.setAnswer(1, 2);
        state.setAnswer(2, 3);
        state.setAnswer(3, 1);
        state.setAnswer(4, 0);

        FakePresenter presenter = new FakePresenter();
        FakeQuizStateProvider provider = new FakeQuizStateProvider(state);

        ConfirmInteractor interactor = new ConfirmInteractor(provider, presenter);

        interactor.forceSubmit();

        assertNotNull(presenter.receivedScore);

        assertEquals(60.0, presenter.receivedScore, 0.0001);

        assertTrue(presenter.showResultCalled);
    }
}
