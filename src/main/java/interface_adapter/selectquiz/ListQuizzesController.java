package interface_adapter.selectquiz;

import use_case.selectquiz.ListQuizzesInputBoundary;

public class ListQuizzesController {

    private final ListQuizzesInputBoundary interactor;

    public ListQuizzesController(ListQuizzesInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}
