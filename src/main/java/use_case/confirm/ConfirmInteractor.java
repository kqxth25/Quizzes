package use_case.confirm;

import interface_adapter.quiz.QuizState;
import use_case.quiz.QuizStateProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor that inspects the current QuizState (via provider) and creates a response for presenter.
 * Also handles 'force submit' action.
 */
public class ConfirmInteractor implements ConfirmInputBoundary {

    private final QuizStateProvider stateProvider;
    private final ConfirmOutputBoundary presenter;

    public ConfirmInteractor(QuizStateProvider stateProvider, ConfirmOutputBoundary presenter) {
        this.stateProvider = stateProvider;
        this.presenter = presenter;
    }

    @Override
    public void prepareConfirmation() {
        QuizState s = stateProvider.getQuizState();
        int total = s.getTotalQuestions();
        List<Integer> incomplete = new ArrayList<>();

        for (int i = 0; i < total; i++) {
            int ans = s.getAnswer(i);
            if (ans < 0) {
                incomplete.add(i);
            }
        }

        incomplete.remove(Integer.valueOf(9));

        boolean allCompleted = incomplete.isEmpty();
        ConfirmResponseModel response = new ConfirmResponseModel(incomplete, allCompleted);
        presenter.presentConfirmation(response);
    }



    @Override
    public void forceSubmit() {

        QuizState s = stateProvider.getQuizState();
        int total = s.getTotalQuestions();

        int correct = 0;

        int[] correctAnswers = s.getCorrectAnswers();

        for (int i = 0; i < total; i++) {
            if (s.getAnswer(i) == correctAnswers[i]) {
                correct++;
            }
        }

        double scorePct = (double) correct / total * 100.0;

        presenter.showFinalScore(scorePct);

        presenter.showResult();
    }


}
