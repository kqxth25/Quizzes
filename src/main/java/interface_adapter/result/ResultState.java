package interface_adapter.result;

public class ResultState {
    private double score;
    private int total;

    public ResultState() {
        this.score = 0.0;
        this.total = 0;
    }

    public ResultState(double score, int total) {
        this.score = score;
        this.total = total;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
