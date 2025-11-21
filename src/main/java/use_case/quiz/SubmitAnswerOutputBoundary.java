package use_case.quiz;

public interface SubmitAnswerOutputBoundary {
    void presentAnswer(SubmitAnswerOutputData outputData);

    void presentNextQuestion(SubmitAnswerOutputData outputData);

    void presentPreviousQuestion(SubmitAnswerOutputData outputData);

    void presentNavigationWarning(String message);
}