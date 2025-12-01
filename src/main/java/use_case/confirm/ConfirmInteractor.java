package use_case.confirm;

import interface_adapter.quiz.QuizState;
import use_case.quiz.QuizStateProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor that inspects the current QuizState (via provider) and creates a response for presenter.
 * Also handles 'force submit' action.
 */
public class ConfirmInteractor implements ConfirmInputBoundary {

    private final QuizStateProvider stateProvider;
    private final ConfirmOutputBoundary presenter;

    public ConfirmInteractor(QuizStateProvider stateProvider, ConfirmOutputBoundary presenter) {
        this.stateProvider = stateProvider;
        this.presenter = presenter;
    }

    @Override
    public void prepareConfirmation() {
        QuizState s = stateProvider.getQuizState();
        int total = s.getTotalQuestions();
        List<Integer> incomplete = new ArrayList<>();

        // 正常统计所有未完成题目
        for (int i = 0; i < total; i++) {
            int ans = s.getAnswer(i);
            if (ans < 0) {
                incomplete.add(i);
            }
        }

        // 规则：不显示第10题（index = 9）
        incomplete.remove(Integer.valueOf(9));

        boolean allCompleted = incomplete.isEmpty();
        ConfirmResponseModel response = new ConfirmResponseModel(incomplete, allCompleted);
        presenter.presentConfirmation(response);
    }



    @Override
    public void forceSubmit() {
        // 1. 告诉 Presenter 用户强制提交了（这步会把 dialog 按钮更新为 "Confirm Submit"）
        ConfirmResponseModel response = new ConfirmResponseModel(new ArrayList<>(), true);
        presenter.presentConfirmation(response);

        // 2. 然后正式跳转到 result
        presenter.showResult();
    }

}
