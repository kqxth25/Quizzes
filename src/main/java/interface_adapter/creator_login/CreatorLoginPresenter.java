package interface_adapter.creator_login;

import use_case.creator_login.CreatorLoginOutputBoundary;

public class CreatorLoginPresenter implements CreatorLoginOutputBoundary {

    private final CreatorLoginViewModel viewModel;

    public CreatorLoginPresenter(CreatorLoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        viewModel.setLoginSuccess(true);
        viewModel.setErrorMessage(null);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailedView(String errorMessage) {
        viewModel.setLoginSuccess(false);
        viewModel.setErrorMessage(errorMessage);
        viewModel.firePropertyChanged();
    }
}