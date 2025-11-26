package use_case.quizimport;

public class QuizImportOutputData {
    private final String quizName;

    public QuizImportOutputData(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizName() { return quizName; }
}