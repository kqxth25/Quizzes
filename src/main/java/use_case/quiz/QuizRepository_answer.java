package use_case.quiz;

public interface QuizRepository_answer {
    String[][] getQuestions();
    String[][] getOptions();
    int[] getCorrectAnswers();

    // ★ 新增：存用户答案
    void saveAnswer(int index, int selectedOption);

    // ★ 新增：取用户已经回答的所有题目
    int[] getSavedAnswers();
    void loadQuiz(String quizName);
}
