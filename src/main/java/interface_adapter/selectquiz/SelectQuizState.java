package interface_adapter.selectquiz;

import use_case.selectquiz.QuizItemDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectQuizState {

    private List<QuizItemDto> quizzes;
    private String error;

    public SelectQuizState() {
        this.quizzes = new ArrayList<>();
        this.error = null;
    }

    public SelectQuizState(List<QuizItemDto> quizzes, String error) {
        if (quizzes == null) {
            this.quizzes = new ArrayList<>();
        } else {
            this.quizzes = new ArrayList<>(quizzes);
        }
        this.error = error;
    }

    public List<QuizItemDto> getQuizzes() {
        return Collections.unmodifiableList(this.quizzes);
    }

    public void setQuizzes(List<QuizItemDto> quizzes) {
        if (quizzes == null) {
            this.quizzes = new ArrayList<>();
        } else {
            this.quizzes = new ArrayList<>(quizzes);
        }
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SelectQuizState{" +
                "quizzesCount=" + (quizzes == null ? 0 : quizzes.size()) +
                ", error='" + error + '\'' +
                '}';
    }
}
