package use_case.quiz;

public class AnswerQuizInteractor implements AnswerQuizInputBoundary {
    private final AnswerQuizOutputBoundary presenter;
    private final QuizRepository_answer repository;

    public AnswerQuizInteractor(AnswerQuizOutputBoundary presenter, QuizRepository_answer repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    @Override
    public void saveAnswer(AnswerQuizInputData inputData) {
        repository.saveAnswer(
                inputData.getQuestionIndex(),
                inputData.getSelectedOption()
        );
    }

    @Override
    public void submitAnswer(AnswerQuizInputData inputData) {

        repository.saveAnswer(
                inputData.getQuestionIndex(),
                inputData.getSelectedOption()
        );

        String[][] questions = repository.getQuestions();
        String[][] options = repository.getOptions();

        int currentIndex = inputData.getQuestionIndex();
        int nextIndex = Math.min(currentIndex + 1, questions.length - 1);

        String questionText = questions[nextIndex][0];
        String[] opts = options[nextIndex];

        AnswerQuizOutputData outputData = new AnswerQuizOutputData(
                currentIndex,
                inputData.getSelectedOption(),
                nextIndex,
                questionText,
                opts
        );

        presenter.presentAnswer(outputData);
    }

    @Override
    public void next(AnswerQuizInputData inputData) {

        repository.saveAnswer(
                inputData.getQuestionIndex(),
                inputData.getSelectedOption()
        );

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

        AnswerQuizOutputData outputData = new AnswerQuizOutputData(
                currentIndex,
                inputData.getSelectedOption(),
                nextIndex,
                questionText,
                opts
        );

        presenter.presentNextQuestion(outputData);
    }

    @Override
    public void previous(AnswerQuizInputData inputData) {

        repository.saveAnswer(
                inputData.getQuestionIndex(),
                inputData.getSelectedOption()
        );

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

        AnswerQuizOutputData outputData = new AnswerQuizOutputData(
                currentIndex,
                inputData.getSelectedOption(),
                prevIndex,
                questionText,
                opts
        );

        presenter.presentPreviousQuestion(outputData);
    }

    public void loadQuiz(String quizName) {
        if (repository instanceof ImportedQuizRepositoryAdapter) {
            ((ImportedQuizRepositoryAdapter) repository).loadQuiz(quizName);
        }
    }
}
