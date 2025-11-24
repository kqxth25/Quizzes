package use_case.quizimport;

import entity.Quiz;

public interface QuizApiDataAccess {
    Quiz fetchQuiz(String name, int amount, String category, String difficulty, String type);
}
