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
        String questionText = questions[nextIndex][0];
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

    @Override
    public void next(SubmitAnswerInputData inputData) {
        String[][] questions = repository.getQuestions();
        String[][] options = repository.getOptions();

        int currentIndex = inputData.getQuestionIndex();

        if (currentIndex >= questions.length - 1) {
            presenter.presentNavigationWarning("Already at the last question");
            return;
        }

        int nextIndex = currentIndex + 1;
        String questionText = questions[nextIndex][0];
        String[] opts = options[nextIndex];

        SubmitAnswerOutputData outputData = new SubmitAnswerOutputData(
                currentIndex,
                inputData.getSelectedOption(),
                nextIndex,
                questionText,
                opts
        );

        presenter.presentNextQuestion(outputData);
    }

    @Override
    public void previous(SubmitAnswerInputData inputData) {
        String[][] questions = repository.getQuestions();
        String[][] options = repository.getOptions();

        int currentIndex = inputData.getQuestionIndex();

        if (currentIndex <= 0) {
            presenter.presentNavigationWarning("Already at the first question");
            return;
        }

        int prevIndex = currentIndex - 1;
        String questionText = questions[prevIndex][0];
        String[] opts = options[prevIndex];

        SubmitAnswerOutputData outputData = new SubmitAnswerOutputData(
                currentIndex,
                inputData.getSelectedOption(),
                prevIndex,
                questionText,
                opts
        );

        presenter.presentPreviousQuestion(outputData);
    }
}
