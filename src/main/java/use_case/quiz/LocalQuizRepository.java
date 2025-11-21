package use_case.quiz;

public class LocalQuizRepository implements QuizRepository {
    private final String[][] questions;
    private final String[][] options;
    private final int[] correctAnswers;

    public LocalQuizRepository(String[][] questions, String[][] options, int[] correctAnswers) {
        this.questions = questions;
        this.options = options;
        this.correctAnswers = correctAnswers;
    }

    @Override
    public String[][] getQuestions() {
        return questions;
    }

    @Override
    public String[][] getOptions() {
        return options;
    }

    @Override
    public int[] getCorrectAnswers() {
        return correctAnswers;
    }
}
