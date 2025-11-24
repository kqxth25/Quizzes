package interface_adapter.quizimport;

import use_case.quizimport.*;
import view.ImportQuizFrame;

public class QuizImportPresenter implements QuizImportOutputBoundary {

    private final ImportQuizFrame view;

    public QuizImportPresenter(ImportQuizFrame view) {
        this.view = view;
    }

    @Override
    public void present(QuizImportOutputData outputData) {
        if (outputData.getError() != null) {
            view.showError(outputData.getError());
        } else {
            view.showSuccess(outputData.getQuiz());
        }
    }
}
