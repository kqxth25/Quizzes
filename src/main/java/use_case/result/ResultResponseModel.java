package use_case.result;

public class ResultResponseModel {
    private final int score;
    private final int total;

    public ResultResponseModel(int score, int total) {
        this.score = score;
        this.total = total;
    }

    public int getScore() {
        return score;
    }

    public int getTotal() {
        return total;
    }
}
