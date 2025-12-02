package interface_adapter.result;

import use_case.result.ResultInputBoundary;

public class ResultController {

    private final ResultInputBoundary interactor;

    public ResultController(ResultInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void computeResult() {
        interactor.computeResult();
    }
}
