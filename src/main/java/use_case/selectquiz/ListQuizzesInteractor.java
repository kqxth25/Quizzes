package use_case.selectquiz;

import java.util.List;


public class ListQuizzesInteractor implements ListQuizzesInputBoundary {

    private final ListQuizzesDataAccessInterface quizDataAccess;
    private final ListQuizzesOutputBoundary presenter;

    public ListQuizzesInteractor(ListQuizzesDataAccessInterface quizDataAccess,
                                 ListQuizzesOutputBoundary presenter) {
        this.quizDataAccess = quizDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        try {
            List<QuizItemDto> quizzes = quizDataAccess.list();
            ListQuizzesOutputData outputData = new ListQuizzesOutputData(quizzes);
            presenter.present(outputData);
        } catch (Exception e) {
            presenter.presentFailure("Failed to load quizzes.");
        }
    }
}
