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

    // ----------------------------------------------------------
    // TEST 1: prepareConfirmation()
    // ----------------------------------------------------------
    @Test
    void testPrepareConfirmationDetectsIncompleteQuestions() {

        QuizState state = new QuizState(12);

        // mark some answered / unanswered
        state.setAnswer(0, 1);
        state.setAnswer(1, -1); // incomplete
        state.setAnswer(2, 2);
        state.setAnswer(9, -1); // but this should be removed by interactor

        FakePresenter presenter = new FakePresenter();
        FakeQuizStateProvider provider = new FakeQuizStateProvider(state);

        ConfirmInteractor interactor = new ConfirmInteractor(provider, presenter);

        interactor.prepareConfirmation();

        assertNotNull(presenter.receivedResponse);

        List<Integer> incomplete = presenter.receivedResponse.getIncompleteQuestionIndices();

        // question 9 should be removed by the interactor
        assertFalse(incomplete.contains(9));

        // question 1 should remain since it's incomplete
        assertTrue(incomplete.contains(1));

        // total incomplete should be exactly 1
        assertEquals(9, incomplete.size());
    }

    // ----------------------------------------------------------
    // TEST 2: forceSubmit()
    // ----------------------------------------------------------
    @Test
    void testForceSubmitComputesScoreCorrectly() {

        QuizState state = new QuizState(5);

        // correct answers
        state.setCorrectAnswers(new int[]{1, 2, 3, 0, 2});

        // student answers
        state.setAnswer(0, 1); // correct
        state.setAnswer(1, 2); // correct
        state.setAnswer(2, 3); // correct
        state.setAnswer(3, 1); // wrong
        state.setAnswer(4, 0); // wrong

        FakePresenter presenter = new FakePresenter();
        FakeQuizStateProvider provider = new FakeQuizStateProvider(state);

        ConfirmInteractor interactor = new ConfirmInteractor(provider, presenter);

        interactor.forceSubmit();

        assertNotNull(presenter.receivedScore);

        // 3 correct out of 5 → 60%
        assertEquals(60.0, presenter.receivedScore, 0.0001);

        assertTrue(presenter.showResultCalled);
    }
}
