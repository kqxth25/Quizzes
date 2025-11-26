package entity;

import java.util.List;

public class Quiz {
    private final String name;
    private final int amount;
    private final String category;
    private final String difficulty;
    private final String type;
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
    public List<Question> getQuestions() { return questions; }
}
