package interface_adapter.creator_login;

import interface_adapter.ViewManagerModel;
import use_case.creator_login.CreatorLoginOutputBoundary;

public class CreatorLoginPresenter implements CreatorLoginOutputBoundary {

    private final CreatorLoginViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public CreatorLoginPresenter(ViewManagerModel viewManagerModel,
                                 CreatorLoginViewModel viewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView() {
        viewModel.setLoginSuccess(true);
        viewModel.setErrorMessage(null);
        viewModel.firePropertyChanged();
        viewManagerModel.navigate("manage quiz");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailedView(String errorMessage) {
        viewModel.setLoginSuccess(false);
        viewModel.setErrorMessage(errorMessage);
        viewModel.firePropertyChanged();
    }
}