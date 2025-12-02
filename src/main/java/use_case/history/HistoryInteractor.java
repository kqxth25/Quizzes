package use_case.history;

public class HistoryInteractor implements HistoryInputBoundary {

    private final HistoryOutputBoundary presenter;

    public HistoryInteractor(HistoryOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void prepareHistoryView() {
        presenter.switchToHistoryView();
    }
}
