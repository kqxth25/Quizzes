package use_case.quiz;

public class SubmitAnswerOutputData {
    private final int questionIndex;
    private final int selectedOption;
    private final int nextQuestionIndex;
    private final String questionText;
    private final String[] options;

    public SubmitAnswerOutputData(int questionIndex, int selectedOption, int nextQuestionIndex,
                                  String questionText, String[] options) {
        this.questionIndex = questionIndex;
        this.selectedOption = selectedOption;
        this.nextQuestionIndex = nextQuestionIndex;
        this.questionText = questionText;
        this.options = options;
    }

    public int getQuestionIndex() { return questionIndex; }
    public int getSelectedOption() { return selectedOption; }
    public int getNextQuestionIndex() { return nextQuestionIndex; }
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
}