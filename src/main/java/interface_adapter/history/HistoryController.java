package interface_adapter.history;

import use_case.history.HistoryInputBoundary;

public class HistoryController {
    private final HistoryInputBoundary interactor;

    public HistoryController(HistoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void onHistorySelected() {
        interactor.prepareHistoryView();
    }
}
