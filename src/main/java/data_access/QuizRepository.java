package data_access;

import entity.Quiz;
import java.util.List;

public interface QuizRepository {
    void addQuiz(Quiz quiz);
    Quiz getQuizByName(String name);
    List<Quiz> getAllQuizzes();
    void deleteQuiz(String name);
}
