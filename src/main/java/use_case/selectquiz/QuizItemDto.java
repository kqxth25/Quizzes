package use_case.selectquiz;

public class QuizItemDto {
    private final String title;
    private final String category;
    private final String difficulty;
    private final String type;

    public QuizItemDto(String title,
                       String category,
                       String difficulty,
                       String type) {
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
    public String getType() { return type; }

    @Override
    public String toString() {
        return "QuizItemDto{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
