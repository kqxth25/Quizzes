package interface_adapter.quiz;

import use_case.quiz.AnswerQuizInputBoundary;
import use_case.quiz.AnswerQuizInputData;
import use_case.quiz.AnswerQuizInteractor;

public class QuizController {
    private final AnswerQuizInputBoundary submitAnswerUseCase;

    public QuizController(AnswerQuizInputBoundary submitAnswerUseCase) {
        this.submitAnswerUseCase = submitAnswerUseCase;
    }

    public void loadQuiz(String quizName) {
        if (submitAnswerUseCase instanceof AnswerQuizInteractor) {
            ((AnswerQuizInteractor) submitAnswerUseCase).loadQuiz(quizName);
        }
    }

    public void submitAnswer(int questionIndex, int selectedOption) {
        AnswerQuizInputData inputData = new AnswerQuizInputData(questionIndex, selectedOption);
        submitAnswerUseCase.submitAnswer(inputData);
    }

    public void next(int currentIndex, int selectedOption) {
        AnswerQuizInputData inputData = new AnswerQuizInputData(currentIndex, selectedOption);
        submitAnswerUseCase.next(inputData);
    }

    public void previous(int currentIndex, int selectedOption) {
        AnswerQuizInputData inputData = new AnswerQuizInputData(currentIndex, selectedOption);
        submitAnswerUseCase.previous(inputData);
    }
}