package interface_adapter.selectquiz;

import interface_adapter.ViewModel;
import use_case.selectquiz.QuizItemDto;

import java.util.List;

public class SelectQuizViewModel extends ViewModel<SelectQuizState> {

    public SelectQuizViewModel() {
        super("select quiz");
        this.setState(new SelectQuizState());
    }

    public List<QuizItemDto> getQuizzes() {
        return this.getState().getQuizzes();
    }

    public void setQuizzes(List<QuizItemDto> quizzes) {
        SelectQuizState s = this.getState();
        s.setQuizzes(quizzes);
        this.setState(s);
    }

    public String getError() {
        return this.getState().getError();
    }

    public void setError(String error) {
        SelectQuizState s = this.getState();
        s.setError(error);
        this.setState(s);
    }
}
