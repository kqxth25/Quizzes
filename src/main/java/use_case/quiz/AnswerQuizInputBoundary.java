package use_case.quiz;

public interface AnswerQuizInputBoundary {
    void saveAnswer(AnswerQuizInputData inputData);
    void submitAnswer(AnswerQuizInputData inputData);

    void next(AnswerQuizInputData inputData);

    void previous(AnswerQuizInputData inputData);
}