package interface_adapter.quiz;

import use_case.quiz.SubmitAnswerOutputData;
import use_case.quiz.SubmitAnswerOutputBoundary;

public class QuizPresenter implements SubmitAnswerOutputBoundary {
    private final QuizViewModel viewModel;

    public QuizPresenter(QuizViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentAnswer(SubmitAnswerOutputData outputData) {
        QuizState state = viewModel.getState();
        state.setAnswer(outputData.getQuestionIndex(), outputData.getSelectedOption());
        state.setCurrentQuestionIndex(outputData.getNextQuestionIndex());
        state.setQuestionText(outputData.getQuestionText());
        state.setOptions(outputData.getOptions());
        viewModel.setState(state);
    }
}
