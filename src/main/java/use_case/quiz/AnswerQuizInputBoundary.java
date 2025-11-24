package use_case.quiz;

public interface AnswerQuizInputBoundary {
    void submitAnswer(AnswerQuizInputData inputData);

    void next(AnswerQuizInputData inputData);

    void previous(AnswerQuizInputData inputData);
}