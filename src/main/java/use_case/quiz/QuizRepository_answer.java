package use_case.quiz;

public interface QuizRepository_answer {
    String[][] getQuestions();
    String[][] getOptions();
    int[] getCorrectAnswers();

    void saveAnswer(int index, int selectedOption);

    int[] getSavedAnswers();
    void loadQuiz(String quizName);
}
