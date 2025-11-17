package use_case.selectquiz;

import java.util.List;


public class ListQuizzesOutputData {

    private final List<QuizItemDto> quizzes;

    public ListQuizzesOutputData(List<QuizItemDto> quizzes) {
        this.quizzes = quizzes;
    }

    public List<QuizItemDto> getQuizzes() {
        return quizzes;
    }
}
