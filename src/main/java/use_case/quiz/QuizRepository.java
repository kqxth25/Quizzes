package use_case.quiz;

public interface QuizRepository {
    String[][] getQuestions();
    String[][] getOptions();
}