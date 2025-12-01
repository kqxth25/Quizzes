package use_case.result;

public class ResultResponseModel {
    private final int score;

    public ResultResponseModel(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
