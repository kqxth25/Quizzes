package interface_adapter.selectquiz;

import entity.Quiz;
import use_case.quizimport.QuizRepository_import;
import use_case.selectquiz.ListQuizzesDataAccessInterface;
import use_case.selectquiz.QuizItemDto;

import java.util.ArrayList;
import java.util.List;

public class QuizListDataAccessAdapter implements ListQuizzesDataAccessInterface {

    private final QuizRepository_import quizRepository;

    public QuizListDataAccessAdapter(QuizRepository_import quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public List<QuizItemDto> list() {
        List<Quiz> quizzes = quizRepository.getAll();
        List<QuizItemDto> result = new ArrayList<>();
        for (Quiz q : quizzes) {
            result.add(new QuizItemDto(
                    q.getName(),
                    q.getCategory(),
                    q.getDifficulty(),
                    q.getType()
            ));
        }
        return result;
    }
}
