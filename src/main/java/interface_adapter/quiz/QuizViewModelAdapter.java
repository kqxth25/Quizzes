package interface_adapter.quiz;

import use_case.quiz.QuizStateProvider;

/**
 * Adapter: wrap your existing QuizViewModel to provide QuizState to use-cases.
 */
public class QuizViewModelAdapter implements QuizStateProvider {

    private final QuizViewModel quizViewModel;

    public QuizViewModelAdapter(QuizViewModel quizViewModel) {
        this.quizViewModel = quizViewModel;
    }

    @Override
    public QuizState getQuizState() {
        return quizViewModel.getState();
    }
}
