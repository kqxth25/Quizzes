package interface_adapter.result;

import use_case.result.ResultOutputBoundary;
import use_case.result.ResultResponseModel;

public class ResultPresenter implements ResultOutputBoundary {

    private final ResultViewModel viewModel;
    private final Runnable showResultViewCallback;

    public ResultPresenter(ResultViewModel viewModel, Runnable showResultViewCallback) {
        this.viewModel = viewModel;
        this.showResultViewCallback = showResultViewCallback;
    }

    @Override
    public void presentResult(ResultResponseModel response) {
        ResultState state = new ResultState();
        state.setScore(response.getScore());
        viewModel.setState(state);

        showResultViewCallback.run();  // navigate to result view
    }
}
