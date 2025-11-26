package data_access;

import entity.Quiz;

import java.util.ArrayList;
import java.util.List;

public class InMemoryQuizRepository implements QuizRepository {
    private final List<Quiz> quizzes = new ArrayList<>();

    @Override
    public synchronized void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    @Override
    public synchronized Quiz getQuizByName(String name) {
        return quizzes.stream().filter(q -> q.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public synchronized List<Quiz> getAllQuizzes() {
        return new ArrayList<>(quizzes);
    }

    @Override
    public synchronized void deleteQuiz(String name) {
        quizzes.removeIf(q -> q.getName().equals(name));
    }
}
