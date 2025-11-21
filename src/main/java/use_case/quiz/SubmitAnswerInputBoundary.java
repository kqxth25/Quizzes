package use_case.quiz;

public interface SubmitAnswerInputBoundary {
    void submitAnswer(SubmitAnswerInputData inputData);

    void next(SubmitAnswerInputData inputData);

    void previous(SubmitAnswerInputData inputData);
}