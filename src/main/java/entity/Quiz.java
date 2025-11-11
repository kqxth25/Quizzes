package entity;

import java.util.HashMap;
import java.util.List;

public class Quiz {

    private final int amount;
    private final String category;
    private final String difficulty;
    private final String type;
    private final List<HashMap<String, Object>> questions;

    public Quiz(int amount, String category, String difficulty, String type, List<HashMap<String, Object>> questions) {
        this.amount = amount;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;

        this.questions = questions;
    }
}
