package data_access;

import entity.Quiz;
import use_case.quizimport.QuizRepository_import;

import java.util.ArrayList;
import java.util.List;

public class InMemoryQuizRepository implements QuizRepository_import {
    private final List<Quiz> quizzes = new ArrayList<>();

    @Override
    public synchronized void save(Quiz quiz) {
        quizzes.add(quiz);
    }

    @Override
    public synchronized Quiz getByName(String name) {
        return quizzes.stream().filter(q -> q.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public synchronized List<Quiz> getAll() {
        return new ArrayList<>(quizzes);
    }

    @Override
    public synchronized void delete(String name) {
        quizzes.removeIf(q -> q.getName().equals(name));
    }
}
