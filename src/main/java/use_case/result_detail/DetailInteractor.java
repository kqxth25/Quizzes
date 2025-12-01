package use_case.result_detail;

import use_case.quiz.QuizRepository_answer;
import interface_adapter.result_detail.DetailOutputBoundary;

import java.util.ArrayList;
import java.util.List;

/**
 * 从 QuizRepository_answer 取题、选项、正确答案和保存的用户答案，
 * 并交给 presenter（输出边界）处理成展示数据。
 */
public class DetailInteractor implements DetailInputBoundary {

    private final QuizRepository_answer repo;
    private final DetailOutputBoundary presenter;

    public DetailInteractor(QuizRepository_answer repo, DetailOutputBoundary presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }

    @Override
    public void loadDetail() {
        // repository 应该在 quiz 被加载时已经准备好数据
        String[][] questions2d = repo.getQuestions();
        String[][] options = repo.getOptions();
        int[] correct = repo.getCorrectAnswers();
        int[] saved = repo.getSavedAnswers();

        // defensive fallback
        if (questions2d == null) questions2d = new String[0][];
        if (options == null) options = new String[0][];
        if (correct == null) correct = new int[0];
        if (saved == null) saved = new int[0];

        int n = questions2d.length;
        // build 1D questions text array (questions2d likely has [i][0] format)
        String[] questions = new String[n];
        for (int i = 0; i < n; i++) {
            if (questions2d[i] != null && questions2d[i].length > 0) {
                questions[i] = questions2d[i][0];
            } else {
                questions[i] = "";
            }
        }

        // options already String[][] where options[i] is options of question i
        // correct[] and saved[] lengths might differ, normalize
        int[] correctIdx = new int[n];
        int[] savedIdx = new int[n];
        for (int i = 0; i < n; i++) {
            correctIdx[i] = (i < correct.length) ? correct[i] : 0;
            savedIdx[i] = (i < saved.length) ? saved[i] : -1;
        }

        // Ensure options array matches n (defensive)
        String[][] finalOptions = new String[n][];
        for (int i = 0; i < n; i++) {
            finalOptions[i] = (i < options.length && options[i] != null) ? options[i] : new String[0];
        }

        DetailResponseModel response = new DetailResponseModel(questions, finalOptions, correctIdx, savedIdx);
        presenter.presentDetail(response);
    }
}
