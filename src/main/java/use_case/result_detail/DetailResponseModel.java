package use_case.result_detail;

public class DetailResponseModel {
    public final String[] questions;     // question text
    public final String[][] options;     // each question's options (all)
    public final int[] correctIndex;     // correct option index for each question
    public final int[] savedAnswers;     // user's selected index (-1 if none)

    public DetailResponseModel(String[] questions, String[][] options, int[] correctIndex, int[] savedAnswers) {
        this.questions = questions;
        this.options = options;
        this.correctIndex = correctIndex;
        this.savedAnswers = savedAnswers;
    }
}
