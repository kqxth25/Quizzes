package data_access;

import use_case.selectquiz.ListQuizzesDataAccessInterface;
import use_case.selectquiz.QuizItemDto;

import java.util.ArrayList;
import java.util.List;

public class InMemoryQuizDataAccess implements ListQuizzesDataAccessInterface {

    private final List<QuizItemDto> quizzes = new ArrayList<>();

    //Mock data
    public InMemoryQuizDataAccess() {
        quizzes.add(new QuizItemDto("General Knowledge", "easy", "multiple"));
        quizzes.add(new QuizItemDto("Science", "medium", "boolean"));
        quizzes.add(new QuizItemDto("History", "hard", "multiple"));
    }

    @Override
    public List<QuizItemDto> list() {
        return new ArrayList<>(quizzes);
    }
}
