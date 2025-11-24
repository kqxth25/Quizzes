package interface_adapter.quiz;

import use_case.quiz.AnswerQuizOutputData;
import use_case.quiz.AnswerQuizOutputBoundary;

public class QuizPresenter implements AnswerQuizOutputBoundary {
    private final QuizViewModel viewModel;

    public QuizPresenter(QuizViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentAnswer(AnswerQuizOutputData outputData) {
        QuizState state = viewModel.getState();
        state.setAnswer(outputData.getQuestionIndex(), outputData.getSelectedOption());
        state.setCurrentQuestionIndex(outputData.getNextQuestionIndex());
        state.setQuestionText(outputData.getQuestionText());
        state.setOptions(outputData.getOptions());
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentNextQuestion(AnswerQuizOutputData outputData) {
        QuizState state = viewModel.getState();
        if (outputData.getSelectedOption() != -1) {
            state.setAnswer(outputData.getQuestionIndex(), outputData.getSelectedOption());
        }
        state.setCurrentQuestionIndex(outputData.getNextQuestionIndex());
        state.setQuestionText(outputData.getQuestionText());
        state.setOptions(outputData.getOptions());
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
        QuizState state = viewModel.getState();
        if (outputData.getSelectedOption() != -1) {
            state.setAnswer(outputData.getQuestionIndex(), outputData.getSelectedOption());
        }
        state.setCurrentQuestionIndex(outputData.getNextQuestionIndex());
        state.setQuestionText(outputData.getQuestionText());
        state.setOptions(outputData.getOptions());
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentNavigationWarning(String message) {
        System.out.println("Navigation Warning: " + message);
    }
}