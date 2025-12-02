package interface_adapter.selectquiz;

import use_case.selectquiz.ListQuizzesInputBoundary;
import use_case.selectquiz.ListQuizzesInputData;

public class ListQuizzesController {

    private final ListQuizzesInputBoundary interactor;

    public ListQuizzesController(ListQuizzesInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String category, String difficulty, String type) {
        ListQuizzesInputData input = new ListQuizzesInputData(category, difficulty, type);
        interactor.execute(input);
    }
}
