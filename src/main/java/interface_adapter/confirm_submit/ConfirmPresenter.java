package interface_adapter.confirm_submit;

import use_case.confirm.ConfirmOutputBoundary;
import use_case.confirm.ConfirmResponseModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Presenter: translates response model to ConfirmState for the ConfirmViewModel.
 */
public class ConfirmPresenter implements ConfirmOutputBoundary {

    private final ConfirmViewModel viewModel;

    public ConfirmPresenter(ConfirmViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentConfirmation(ConfirmResponseModel response) {
        List<Integer> incomplete = response.getIncompleteQuestionIndices();
        boolean allDone = response.isAllCompleted();

        String message;
        String actionText;
        if (allDone) {
            message = "Good luck";
            actionText = "Confirm Submit";
        } else {
            // format like "Q1, Q3 not finished" (1-based numbering)
            String joined = incomplete.stream()
                    .map(i -> "Q" + (i + 1))
                    .collect(Collectors.joining(", "));
            message = joined + " not finished";
            actionText = "Still Submit";
        }

        ConfirmState state = new ConfirmState(incomplete, allDone, message, actionText);
        viewModel.setState(state);
    }
}
