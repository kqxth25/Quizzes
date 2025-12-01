package interface_adapter.share_result;

public class ShareResultState {
    private String username;
    private String quizName;
    private double score;
    private int total;

    public ShareResultState() {}

    public ShareResultState(String username, String quizName, double score, int total) {
        this.username = username;
        this.quizName = quizName;
        this.score = score;
        this.total = total;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getQuizName() { return quizName; }
    public void setQuizName(String quizName) { this.quizName = quizName; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
}
