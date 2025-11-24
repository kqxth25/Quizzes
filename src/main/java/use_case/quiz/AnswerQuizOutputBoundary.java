package use_case.quiz;

public interface AnswerQuizOutputBoundary {
    void presentAnswer(AnswerQuizOutputData outputData);

    void presentNextQuestion(AnswerQuizOutputData outputData);

    void presentPreviousQuestion(AnswerQuizOutputData outputData);

    void presentNavigationWarning(String message);
}