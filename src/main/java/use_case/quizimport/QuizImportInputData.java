package use_case.quizimport;

public class QuizImportInputData {
    public String name;
    public int amount;
    public String category;
    public String difficulty;
    public String type;

    public QuizImportInputData(String name, int amount, String category, String difficulty, String type) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
    }
}

