package use_case.quizimport;

import entity.Quiz;

public interface QuizImportOutputBoundary {
    void presentSuccess(Quiz quiz);
    void presentError(String errorMessage);
}
