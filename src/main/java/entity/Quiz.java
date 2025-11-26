package entity;

import java.util.List;

public class Quiz {
    private final String name;
    private final int amount;
    private final String category;    // human-readable category name (from API)
    private final String difficulty;  // "easy", "medium", "hard" or "any"
    private final String type;        // "boolean", "multiple" or "any"
    private final List<Question> questions;

    public Quiz(String name, int amount, String category, String difficulty, String type, List<Question> questions) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
        this.questions = questions;
    }

    public String getName() { return name; }
    public int getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
    public String getType() { return type; }
    public List<Question> getQuestions() { return questions; }
}
