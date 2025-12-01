package use_case.result;

import use_case.quiz.QuizRepository_answer;

public class ResultInteractor implements ResultInputBoundary {

    private final QuizRepository_answer repository;
    private final ResultOutputBoundary presenter;

    public ResultInteractor(QuizRepository_answer repository, ResultOutputBoundary presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Override
    public void computeResult() {
        int[] saved = repository.getSavedAnswers();
        int[] correct = repository.getCorrectAnswers();

        if (saved == null || saved.length == 0) {
            // 没有已保存答案，分数为 0
            presenter.presentResult(new ResultResponseModel(0));
            return;
        }

        // 为保险起见，确保 correct 数组长度与 saved 至少有一个最小值范围
        int total = Math.min(saved.length, (correct == null ? 0 : correct.length));
        if (total == 0) {
            // 没有正确答案信息，按 0 分处理
            presenter.presentResult(new ResultResponseModel(0));
            return;
        }

        int correctCount = 0;
        for (int i = 0; i < total; i++) {
            if (saved[i] == correct[i]) {
                correctCount++;
            }
        }

        int score = (int) ((correctCount * 100.0) / total);

        presenter.presentResult(new ResultResponseModel(score));
    }
}
