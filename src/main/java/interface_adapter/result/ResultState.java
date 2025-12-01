package interface_adapter.result;

public class ResultState {
    // 改成 double 可以保存带小数的百分比
    private double score;
    private int total; // 如果你还要展示总题数

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
