package use_case.result;

class TestPresenter implements ResultOutputBoundary {

    boolean called = false;
    ResultResponseModel response = null;

    @Override
    public void presentResult(ResultResponseModel response) {
        this.called = true;
        this.response = response;
    }
}
