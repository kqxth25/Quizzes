package interface_adapter.quizimport;

import use_case.quizimport.QuizImportInputBoundary;
import use_case.quizimport.QuizImportInputData;

public class QuizImportController {
    private final QuizImportInputBoundary interactor;

    public QuizImportController(QuizImportInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void importQuiz(String name, int amount, String categoryName, String difficulty, String type) {
        QuizImportInputData d = new QuizImportInputData(name, amount, categoryName, difficulty, type);
        interactor.importQuiz(d);
    }
}
