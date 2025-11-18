package interface_adapter.quiz;

import use_case.quiz.SubmitAnswerInputBoundary;
import use_case.quiz.SubmitAnswerInputData;

public class QuizController {
    private final SubmitAnswerInputBoundary submitAnswerUseCase;

    public QuizController(SubmitAnswerInputBoundary submitAnswerUseCase) {
        this.submitAnswerUseCase = submitAnswerUseCase;
    }

    public void submitAnswer(int questionIndex, int selectedOption) {
        SubmitAnswerInputData inputData = new SubmitAnswerInputData(questionIndex, selectedOption);
        submitAnswerUseCase.submitAnswer(inputData);
    }
}
