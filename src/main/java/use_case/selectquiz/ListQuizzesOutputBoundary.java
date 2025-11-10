package use_case.selectquiz;

/**
 * Output boundary for the "List Quizzes" use case.
 * Defines how the interactor communicates results to the presenter.
 */
public interface ListQuizzesOutputBoundary {

    /**
     * Called when quizzes are successfully retrieved.
     * @param outputData contains the list of quizzes
     */
    void present(ListQuizzesOutputData outputData);

    /**
     * Called when retrieving quizzes fails.
     * @param errorMessage the error message
     */
    void presentFailure(String errorMessage);
}
