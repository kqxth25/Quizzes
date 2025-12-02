package interface_adapter.quiz;

import use_case.quiz.QuizStateProvider;
import use_case.quiz.QuizRepository_answer;

/**
 * Adapter: provide a QuizState to use-cases by merging the view-model state
 * with the repository's savedAnswers so callers (e.g. ConfirmInteractor)
 * see the repository-updated answers.
 */
public class QuizStateProviderImpl implements QuizStateProvider {

    private final QuizViewModel viewModel;
    private final QuizRepository_answer repository;

    public QuizStateProviderImpl(QuizViewModel viewModel, QuizRepository_answer repository) {
        this.viewModel = viewModel;
        this.repository = repository;
    }

    @Override
    public QuizState getQuizState() {
        QuizState state = viewModel.getState();

        int[] saved = repository.getSavedAnswers();
        if (saved != null) {
            int total = Math.min(saved.length, state.getTotalQuestions());
            for (int i = 0; i < total; i++) {
                if (saved[i] >= 0) {
                    state.setAnswer(i, saved[i]);
                }
            }
        }

        return state;
    }

}
