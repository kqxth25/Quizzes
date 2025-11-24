package use_case.quizrepository;

import entity.Quiz;
import java.util.List;

public interface QuizRepository {
    void save(Quiz quiz);
    void delete(String name);
    Quiz findByName(String name);
    List<Quiz> getAll();
    boolean exists(String name);
}