package interface_adapter.result_detail;

import use_case.result_detail.DetailInputBoundary;

public class DetailController {
    private final DetailInputBoundary interactor;

    public DetailController(DetailInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void showDetail() {
        interactor.loadDetail();
    }
}
