package use_case.quiz;

public class AnswerQuizInputData {
    private final int questionIndex;
    private final int selectedOption;

    public AnswerQuizInputData(int questionIndex, int selectedOption) {
        this.questionIndex = questionIndex;
        this.selectedOption = selectedOption;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public int getSelectedOption() {
        return selectedOption;
    }
}
