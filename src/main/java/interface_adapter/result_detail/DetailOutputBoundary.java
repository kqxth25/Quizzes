package interface_adapter.result_detail;

import use_case.result_detail.DetailResponseModel;

public interface DetailOutputBoundary {
    void presentDetail(DetailResponseModel response);
}
