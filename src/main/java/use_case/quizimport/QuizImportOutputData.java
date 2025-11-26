package use_case.quizimport;

import entity.Quiz;

public class QuizImportOutputData {
    private final Quiz quiz;
    private final String error;

    public QuizImportOutputData(Quiz quiz, String error) {
        this.quiz = quiz;
        this.error = error;
    }

    public Quiz getQuiz() { return quiz; }
    public String getError() { return error; }
}
