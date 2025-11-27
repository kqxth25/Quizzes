package use_case.quiz;

public interface QuizRepository_answer {
    String[][] getQuestions();
    String[][] getOptions();
    int[] getCorrectAnswers();

}