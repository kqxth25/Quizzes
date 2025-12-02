package interface_adapter.history;

import interface_adapter.ViewManagerModel;
import use_case.history.HistoryOutputBoundary;

public class HistoryPresenter implements HistoryOutputBoundary {

    private final ViewManagerModel viewManagerModel;

    public HistoryPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void switchToHistoryView() {
        viewManagerModel.navigate("history-view");
    }
}
