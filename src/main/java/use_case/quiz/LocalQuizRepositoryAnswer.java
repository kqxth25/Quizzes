package use_case.quiz;

import java.util.Arrays;

public class LocalQuizRepositoryAnswer implements QuizRepository_answer {
    private final String[][] questions;
    private final String[][] options;
    private final int[] correctAnswers;

    private final int[] savedAnswers;

    public LocalQuizRepositoryAnswer(String[][] questions, String[][] options, int[] correctAnswers) {
        this.questions = questions;
        this.options = options;
        this.correctAnswers = correctAnswers;

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

    @Override
    public void saveAnswer(int index, int selectedOption) {
        savedAnswers[index] = selectedOption;
    }

    @Override
    public int[] getSavedAnswers() {
        return savedAnswers;
    }
    @Override
    public void loadQuiz(String quizName) {
    }

}
