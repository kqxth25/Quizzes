package use_case.creator_login;

import use_case.creator_login.CreatorLoginOutputBoundary;

class TestPresenter implements CreatorLoginOutputBoundary {

    boolean successCalled = false;
    boolean failCalled = false;
    String errorMessage = null;

    @Override
    public void prepareSuccessView() {
        successCalled = true;
    }

    @Override
    public void prepareFailedView(String errorMessage) {
        failCalled = true;
        this.errorMessage = errorMessage;
    }
}
