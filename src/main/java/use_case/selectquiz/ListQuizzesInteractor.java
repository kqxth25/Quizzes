package use_case.selectquiz;

import java.util.ArrayList;
import java.util.List;

public class ListQuizzesInteractor implements ListQuizzesInputBoundary {

    private final ListQuizzesDataAccessInterface quizDataAccess;
    private final ListQuizzesOutputBoundary presenter;

    public ListQuizzesInteractor(ListQuizzesDataAccessInterface quizDataAccess,
                                 ListQuizzesOutputBoundary presenter) {
        this.quizDataAccess = quizDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ListQuizzesInputData inputData) {
        try {
            List<QuizItemDto> all = quizDataAccess.list();

            String category = inputData.getCategory();
            String difficulty = inputData.getDifficulty();
            String type = inputData.getType();

            List<QuizItemDto> filtered = new ArrayList<>();
            for (QuizItemDto item : all) {

                if (category != null && !"Any".equalsIgnoreCase(category)) {
                    String c = item.getCategory();
                    if (c == null || !category.equalsIgnoreCase(c)) {
                        continue;
                    }
                }

                if (difficulty != null && !"Any".equalsIgnoreCase(difficulty)) {
                    String d = item.getDifficulty();
                    if (d == null || !difficulty.equalsIgnoreCase(d)) {
                        continue;
                    }
                }

                if (type != null && !"Any".equalsIgnoreCase(type)) {
                    String t = item.getType();
                    if (t == null || !type.equalsIgnoreCase(t)) {
                        continue;
                    }
                }

                filtered.add(item);
            }

            presenter.present(new ListQuizzesOutputData(filtered));
        } catch (Exception e) {
            presenter.presentFailure("Failed to load quizzes.");
        }
    }
}
