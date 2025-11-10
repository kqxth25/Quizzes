package use_case.selectquiz;

import java.util.List;

public interface ListQuizzesDataAccessInterface {

    /**
     * Returns all available quizzes.
     */
    List<QuizItemDto> list();
}
