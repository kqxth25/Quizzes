package use_case.confirm;

import interface_adapter.confirm_submit.ConfirmState;

/**
 * Output boundary used by interactor to present prepared confirm state to presenter.
 */
public interface ConfirmOutputBoundary {
    void presentConfirmation(ConfirmResponseModel response);

    void showResult();
}
