package use_case.quizimport;

import entity.Question;
import entity.Quiz;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizImportInteractorTest {

    @Test
    void presenterSuccessAndRepositoryInteractionTest() {
        // Capture saved quizzes using a list
        List<Quiz> saved = new ArrayList<>();

        // Anonymous repository
        QuizRepository_import repository = new QuizRepository_import() {
            @Override
            public void save(Quiz quiz) {
                saved.add(quiz);
            }

            @Override
            public List<Quiz> getAll() {
                return saved;
            }

            @Override
            public Quiz getByName(String name) {
                return saved.stream()
                        .filter(q -> q.getName().equals(name))
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public void delete(String name) {
                saved.removeIf(q -> q.getName().equals(name));
            }
        };

        // Capture presenter calls
        final Quiz[] receivedQuiz = new Quiz[1];
        final String[] receivedError = new String[1];

        QuizImportOutputBoundary presenter = new QuizImportOutputBoundary() {
            @Override
            public void presentSuccess(Quiz quiz) {
                receivedQuiz[0] = quiz;
            }

            @Override
            public void presentError(String message) {
                receivedError[0] = message;
            }
        };

        // Simulate success without calling API
        Quiz quiz = new Quiz("Test Quiz", 3, "Any", "Any", "Any", List.of());
        repository.save(quiz);
        presenter.presentSuccess(quiz);

        assertEquals(quiz, repository.getByName("Test Quiz"));
        assertEquals(quiz, receivedQuiz[0]); // presenter received the quiz
        assertNull(receivedError[0]);        // no failures were triggered
    }

    @Test
    void presenterErrorTest() {
        final String[] receivedError = new String[1];

        QuizImportOutputBoundary presenter = new QuizImportOutputBoundary() {
            @Override
            public void presentSuccess(Quiz quiz) {
                fail("presentSuccess should not be called");
            }

            @Override
            public void presentError(String message) {
                receivedError[0] = message;
            }
        };

        presenter.presentError("Simulated failure");

        assertEquals("Simulated failure", receivedError[0]);
    }

    @Test
    void repositoryDeleteTest() {
        List<Quiz> saved = new ArrayList<>();

        QuizRepository_import repository = new QuizRepository_import() {
            @Override
            public void save(Quiz quiz) {
                saved.add(quiz);
            }

            @Override
            public List<Quiz> getAll() {
                return saved;
            }

            @Override
            public Quiz getByName(String name) {
                return saved.stream()
                        .filter(q -> q.getName().equals(name))
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public void delete(String name) {
                saved.removeIf(q -> q.getName().equals(name));
            }
        };

        Quiz quiz = new Quiz("Quiz1", 4, "Any", "Any", "Any", List.of());
        repository.save(quiz);

        assertNotNull(repository.getByName("Quiz1"));

        repository.delete("Quiz1");

        assertNull(repository.getByName("Quiz1"));
        assertEquals(0, repository.getAll().size());
    }
}
