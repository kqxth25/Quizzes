package use_case.result;

import interface_adapter.quiz.QuizViewModel;
import interface_adapter.quiz.QuizState;

public class FakeQuizViewModel extends QuizViewModel {

    public FakeQuizViewModel() {
        super(new QuizState(10));
    }
}
