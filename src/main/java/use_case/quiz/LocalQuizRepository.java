package use_case.quiz;

public class LocalQuizRepository implements QuizRepository {
    private final String[][] questions;
    private final String[][] options;

    public LocalQuizRepository(String[][] questions, String[][] options) {
        this.questions = questions;
        this.options = options;
    }

    @Override
    public String[][] getQuestions() {
        return questions;
    }

    @Override
    public String[][] getOptions() {
        return options;
    }
}
