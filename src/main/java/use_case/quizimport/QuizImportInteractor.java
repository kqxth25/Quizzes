package use_case.quizimport;

import entity.Quiz;
import data_access.QuizApiDataAccessObject;

public class QuizImportInteractor implements QuizImportInputBoundary {

    private final QuizImportOutputBoundary presenter;
    private final QuizApiDataAccessObject dao;

    public QuizImportInteractor(QuizImportOutputBoundary presenter) {
        this.presenter = presenter;
        this.dao = new QuizApiDataAccessObject();
    }

    @Override
    public void importQuiz(QuizImportInputData inputData) {
        try {
            Quiz quiz = dao.fetchQuiz(
                    inputData.getName(),
                    inputData.getAmount(),
                    inputData.getCategory(),
                    inputData.getDifficulty(),
                    inputData.getType()
            );
            presenter.present(new QuizImportOutputData(quiz, null));
        } catch (Exception e) {
            presenter.present(new QuizImportOutputData(null, e.getMessage()));
        }
    }
}
