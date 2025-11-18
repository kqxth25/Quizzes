package use_case.quiz;

import interface_adapter.quiz.QuizPresenter;

public class SubmitAnswerInteractor implements SubmitAnswerInputBoundary {
    private final SubmitAnswerOutputBoundary presenter;
    private final QuizRepository repository;

    public SubmitAnswerInteractor(SubmitAnswerOutputBoundary presenter, QuizRepository repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    @Override
    public void submitAnswer(SubmitAnswerInputData inputData) {
        String[][] questions = repository.getQuestions();
        String[][] options = repository.getOptions();

        int currentIndex = inputData.getQuestionIndex();
        int nextIndex = Math.min(currentIndex + 1, questions.length - 1);
        String questionText = questions[nextIndex][0]; // 假设题目文本在数组的第一列
        String[] opts = options[nextIndex];

        SubmitAnswerOutputData outputData = new SubmitAnswerOutputData(
                currentIndex,
                inputData.getSelectedOption(),
                nextIndex,
                questionText,
                opts
        );

        presenter.presentAnswer(outputData);
    }
}
