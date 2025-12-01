package interface_adapter.quiz;

import use_case.quiz.AnswerQuizInputBoundary;
import use_case.quiz.AnswerQuizInputData;
import use_case.quiz.AnswerQuizInteractor;

public class QuizController {
    private final AnswerQuizInputBoundary submitAnswerUseCase;
    // 新增：confirm controller
    private interface_adapter.confirm_submit.ConfirmController confirmController;

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

    // ---------- 新增部分 ----------
    public void setConfirmController(interface_adapter.confirm_submit.ConfirmController confirmController) {
        this.confirmController = confirmController;
    }

    /**
     * Called when user presses "Submit" on last question.
     * We first save the final answer, then show the confirm dialog.
     */
    public void prepareConfirmation(int questionIndex, int selectedOption) {
        submitAnswerUseCase.saveAnswer(
                new AnswerQuizInputData(questionIndex, selectedOption)
        );

        if (confirmController != null) {
            confirmController.prepareConfirmation();
        }
    }

}
