package use_case;

import entity.Quiz;
import java.util.List;

public interface QuizRepository {
    void save(Quiz quiz);           // Save a new quiz
    List<Quiz> getAll();            // Get all quizzes
    Quiz getByName(String name);    // Get a quiz by name
}
