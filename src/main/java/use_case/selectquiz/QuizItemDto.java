package use_case.selectquiz;

public class QuizItemDto {

    private final String title;

    private final String difficulty;

    private final String type;

    /**
     * @param title       the quiz title or category
     * @param difficulty  the difficulty level
     * @param type        the quiz type (multiple or boolean)
     */
    public QuizItemDto(String title, String difficulty, String type) {
        this.title = title;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "QuizItemDto{" +
                "title='" + title + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
