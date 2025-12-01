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

        // === 1. 获取用户答案 ===
        QuizState s = stateProvider.getQuizState();
        int total = s.getTotalQuestions();

        int correct = 0;

        // === 2. 获取数据库中的正确答案 ===
        // 你必须在 QuizState 中提供 correct answers
        int[] correctAnswers = s.getCorrectAnswers();  // ★ 需要你确保 QuizState 有这个

        // === 3. 对比用户答案与正确答案 ===
        for (int i = 0; i < total; i++) {
            if (s.getAnswer(i) == correctAnswers[i]) {
                correct++;
            }
        }

        // === 4. 计算得分百分比 ===
        double scorePct = (double) correct / total * 100.0;

        // === 5. 把分数传给 presenter（让 ResultViewModel 更新） ===
        presenter.showFinalScore(scorePct);

        // === 6. 跳转到 result view ===
        presenter.showResult();
    }


}
