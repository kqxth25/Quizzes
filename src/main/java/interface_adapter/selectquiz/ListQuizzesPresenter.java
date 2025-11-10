package interface_adapter.selectquiz;

import use_case.selectquiz.ListQuizzesOutputBoundary;
import use_case.selectquiz.ListQuizzesOutputData;
import use_case.selectquiz.QuizItemDto;

import java.util.Collections;
import java.util.List;

public class ListQuizzesPresenter implements ListQuizzesOutputBoundary {

    private final SelectQuizViewModel viewModel;

    public ListQuizzesPresenter(SelectQuizViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ListQuizzesOutputData output) {
        SelectQuizState state = viewModel.getState();
        List<QuizItemDto> quizzes = output.getQuizzes();

        state.setQuizzes(quizzes);
        state.setError(null);

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void presentFailure(String errorMessage) {
        SelectQuizState state = viewModel.getState();
        state.setQuizzes(Collections.emptyList());
        state.setError(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }
}
