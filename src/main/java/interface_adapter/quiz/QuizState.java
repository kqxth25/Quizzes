package interface_adapter.quiz;

import java.util.Arrays;

public class QuizState {

    private int currentQuestionIndex;
    private String questionText;
    private String[] options;
    private int[] selectedAnswers;
    private final int totalQuestions;

    public QuizState(int totalQuestions) {
        this.totalQuestions = totalQuestions;
        this.selectedAnswers = new int[totalQuestions];
        Arrays.fill(this.selectedAnswers, -1);
        this.currentQuestionIndex = 0;
        this.questionText = "";
        this.options = new String[0];
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setAnswer(int questionIndex, int selectedOption) {
        if (questionIndex >= 0 && questionIndex < totalQuestions) {
            selectedAnswers[questionIndex] = selectedOption;
        }
    }

    public int getAnswer(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < totalQuestions) {
            return selectedAnswers[questionIndex];
        }
        return -1;
    }

    public boolean hasNext() {
        return currentQuestionIndex < totalQuestions - 1;
    }

    public boolean hasPrevious() {
        return currentQuestionIndex > 0;
    }
}