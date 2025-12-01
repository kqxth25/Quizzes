package interface_adapter.confirm_submit;

import interface_adapter.ViewManagerModel;
import interface_adapter.result.ResultViewModel;
import interface_adapter.result.ResultState;
import use_case.confirm.ConfirmOutputBoundary;
import use_case.confirm.ConfirmResponseModel;

import java.util.List;
import java.util.stream.Collectors;

public class ConfirmPresenter implements ConfirmOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ConfirmViewModel viewModel;

    // 新增：注入 ResultViewModel，让 ConfirmPresenter 能直接更新 result 的 view model
    private final ResultViewModel resultViewModel;

    public ConfirmPresenter(ViewManagerModel viewManagerModel,
                            ConfirmViewModel viewModel,
                            ResultViewModel resultViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewModel = viewModel;
        this.resultViewModel = resultViewModel;
    }

    @Override
    public void presentConfirmation(ConfirmResponseModel response) {
        List<Integer> incomplete = response.getIncompleteQuestionIndices();

        if (incomplete != null && incomplete.size() == 1 && incomplete.contains(9)) {
            incomplete = java.util.List.of();
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
                    .collect(Collectors.joining(", "));
            message = joined + " not finished";
            actionText = "Still Submit";
        }

        ConfirmState state = new ConfirmState(incomplete, allDone, message, actionText);
        viewModel.setState(state);
    }

    // 确保 ConfirmOutputBoundary 中有这个方法签名（void showFinalScore(double)）
    @Override
    public void showFinalScore(double scorePct) {
        // 从 resultViewModel 取得当前 state，更新 score 后再 setState/firePropertyChanged
        ResultState s = resultViewModel.getState();
        if (s == null) {
            s = new ResultState();
        }
        s.setScore(scorePct);
        resultViewModel.setState(s);
        resultViewModel.firePropertyChanged();
    }

    @Override
    public void showResult() {
        viewManagerModel.navigate("resultView");
    }
}
