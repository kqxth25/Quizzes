package use_case.confirm;

import interface_adapter.quiz.QuizState;
import use_case.quiz.QuizStateProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor that inspects the current QuizState (via provider) and creates a response for presenter.
 * Also handles 'force submit' action (here we simply forward a 'force submit' event to presenter by sending an empty list and allCompleted=false;
 * in a real system you would call the submit use-case).
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
            int ans = s.getAnswer(i); // your QuizState has getAnswer(index)
            if (ans < 0) {
                incomplete.add(i);
            }
        }

        // ⬇⬇⬇ 在这里忽略 Q10（index = 9）未完成 ⬇⬇⬇
        incomplete.remove(Integer.valueOf(9));

        boolean allCompleted = incomplete.isEmpty();
        ConfirmResponseModel response = new ConfirmResponseModel(incomplete, allCompleted);
        presenter.presentConfirmation(response);
    }


    @Override
    public void forceSubmit() {
        // In a full system this would trigger the actual submission use case.
        // For our confirm flow, we can reuse presenter to update UI to a "forcing" state.
        // We'll present a response with an empty list but mark allCompleted=false to indicate forced submission.
        ConfirmResponseModel response = new ConfirmResponseModel(new ArrayList<>(), false);
        presenter.presentConfirmation(response);

        // TODO: call the actual submit interactor if you have one (e.g. AnswerQuizInputBoundary.submitFinal())
    }
}
