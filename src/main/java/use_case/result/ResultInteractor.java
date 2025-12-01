package use_case.result;

import interface_adapter.quiz.QuizState;
import interface_adapter.quiz.QuizViewModel;
import use_case.quiz.QuizRepository_answer;

public class ResultInteractor implements ResultInputBoundary {

    private final QuizRepository_answer repository;
    private final ResultOutputBoundary presenter;
    private final QuizState quizState;

    public ResultInteractor(QuizRepository_answer repository,
                            ResultOutputBoundary presenter,
                            QuizViewModel quizViewModel) {
        this.repository = repository;
        this.presenter = presenter;
        this.quizState = quizViewModel.getState();
    }

    @Override
    public void computeResult() {

        int[] user = repository.getSavedAnswers();
        int[] correct = repository.getCorrectAnswers();

        int score = 0;
        int total = correct.length;

        for (int i = 0; i < total; i++) {
            if (user[i] == correct[i]) {
                score++;
            }
        }

        ResultResponseModel response = new ResultResponseModel(score, total);
        presenter.presentResult(response);
    }
}
