package interface_adapter.result;

import use_case.result.ResultOutputBoundary;
import use_case.result.ResultResponseModel;

public class ResultPresenter implements ResultOutputBoundary {

    private final ResultViewModel viewModel;

    public ResultPresenter(ResultViewModel vm) {
        this.viewModel = vm;
    }

    @Override
    public void presentResult(ResultResponseModel response) {
        viewModel.setScore(response.getScore());
        viewModel.setTotal(response.getTotal());
        viewModel.firePropertyChanged();
    }
}
