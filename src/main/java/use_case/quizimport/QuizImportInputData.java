package use_case.quizimport;

public class QuizImportInputData {
    private final String name;
    private final int amount;
    private final String category;
    private final String difficulty;
    private final String type;

    public QuizImportInputData(String name, int amount, String category, String difficulty, String type) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getName() { return name; }
    public int getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
    public String getType() { return type; }
}