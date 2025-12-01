package interface_adapter.confirm_submit;

import interface_adapter.ViewManagerModel;
import use_case.confirm.ConfirmOutputBoundary;
import use_case.confirm.ConfirmResponseModel;
import java.util.List;
import java.util.stream.Collectors;

public class ConfirmPresenter implements ConfirmOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ConfirmViewModel viewModel;

    public ConfirmPresenter(ViewManagerModel viewManagerModel, ConfirmViewModel viewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewModel = viewModel;
    }

    @Override
    public void presentConfirmation(ConfirmResponseModel response) {
        List<Integer> incomplete = response.getIncompleteQuestionIndices();

        // 如果只有 Q10（index = 9）未完成，就把它当成全部完成：清空 incomplete
        if (incomplete != null && incomplete.size() == 1 && incomplete.contains(9)) {
            incomplete = java.util.List.of(); // Java 9+: 返回不可变的空列表，作为“已完成”的代表
        }

        boolean allDone = (incomplete == null || incomplete.isEmpty());

        String message;
        String actionText;

        if (allDone) {
            message = "Good luck";
            actionText = "Confirm Submit";
        } else {
            String joined = incomplete.stream()
                    .map(i -> "Q" + (i + 1))
                    .collect(java.util.stream.Collectors.joining(", "));
            message = joined + " not finished";
            actionText = "Still Submit";
        }

        ConfirmState state = new ConfirmState(incomplete, allDone, message, actionText);
        viewModel.setState(state);
    }


    @Override
    public void showResult() {
        viewManagerModel.navigate("resultView");
    }
}
