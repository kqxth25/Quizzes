package interface_adapter.creator_login;

import interface_adapter.ViewManagerModel;
import use_case.creator_login.CreatorLoginOutputBoundary;

public class CreatorLoginPresenter implements CreatorLoginOutputBoundary {

    private final ViewManagerModel viewManager;

    public CreatorLoginPresenter(ViewManagerModel viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView() {
        viewManager.navigate("manage");
    }

    @Override
    public void prepareFailedView(String errorMessage) {
        System.out.println("Login failed: " + errorMessage);
    }
}
