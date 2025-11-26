package use_case.quizimport;

public class QuizImportInputData {
    private final String name;
    private final int amount;
    private final String categoryName; // human name from dropdown (e.g. "Science: Computers" or "Any")
    private final String difficulty;   // "any","easy","medium","hard"
    private final String type;         // "any","boolean","multiple"

    public QuizImportInputData(String name, int amount, String categoryName, String difficulty, String type) {
        this.name = name;
        this.amount = amount;
        this.categoryName = categoryName;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getName() { return name; }
    public int getAmount() { return amount; }
    public String getCategoryName() { return categoryName; }
    public String getDifficulty() { return difficulty; }
    public String getType() { return type; }
}
