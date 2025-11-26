package interface_adapter.quizimport;

import entity.Quiz;
import use_case.quizimport.QuizImportOutputBoundary;
import view.ImportQuizFrame;

public class QuizImportPresenter implements QuizImportOutputBoundary {

    private final ImportQuizFrame view;

    public QuizImportPresenter(ImportQuizFrame view) {
        this.view = view;
    }

    @Override
    public void presentSuccess(Quiz quiz) {
        view.showSuccess(quiz);
    }

    @Override
    public void presentError(String errorMessage) {
        view.showError(errorMessage);
    }
}
