package use_case.quiz;

import java.util.Arrays;

public class LocalQuizRepositoryAnswer implements QuizRepository_answer {
    private final String[][] questions;
    private final String[][] options;
    private final int[] correctAnswers;

    // ★ 新增：存储用户答案
    private final int[] savedAnswers;

    public LocalQuizRepositoryAnswer(String[][] questions, String[][] options, int[] correctAnswers) {
        this.questions = questions;
        this.options = options;
        this.correctAnswers = correctAnswers;

        // ★ 初始化用户答案数组
        this.savedAnswers = new int[questions.length];
        Arrays.fill(this.savedAnswers, -1);
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

    // ★ 实现 saveAnswer
    @Override
    public void saveAnswer(int index, int selectedOption) {
        savedAnswers[index] = selectedOption;
    }

    // ★ 实现 getSavedAnswers
    @Override
    public int[] getSavedAnswers() {
        return savedAnswers;
    }
    @Override
    public void loadQuiz(String quizName) {
        // 本地 quiz 已通过 constructor 加载，这里无需任何操作
    }

}
