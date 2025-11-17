package use_case.selectquiz;


public class ListQuizzesInputData {

    private String category;
    private String difficulty;
    private String type;

    public ListQuizzesInputData() {
        this.category = null;
        this.difficulty = null;
        this.type = null;
    }

    public ListQuizzesInputData(String category, String difficulty, String type) {
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ListQuizzesInputData{" +
                "category='" + category + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
