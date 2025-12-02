package use_case.result_detail;

import interface_adapter.result_detail.DetailOutputBoundary;

class TestPresenter implements DetailOutputBoundary {

    boolean called = false;
    use_case.result_detail.DetailResponseModel response;

    @Override
    public void presentDetail(use_case.result_detail.DetailResponseModel response) {
        this.called = true;
        this.response = response;
    }
}
