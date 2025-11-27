package use_case.quizimport;

import entity.Quiz;
import java.util.List;

public interface QuizRepository_import {
    void save(Quiz quiz);           // Save a new quiz
    List<Quiz> getAll();            // Get all quizzes
    Quiz getByName(String name);    // Get a quiz by name
    void delete(String name);         //delete a quiz by name
}
