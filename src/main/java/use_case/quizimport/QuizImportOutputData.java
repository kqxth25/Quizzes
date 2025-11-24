package use_case.quizimport;

import entity.Quiz;

public class QuizImportOutputData {
    public Quiz quiz;
    public boolean success;

    public QuizImportOutputData(Quiz quiz, boolean success) {
        this.quiz = quiz;
        this.success = success;
    }
}
