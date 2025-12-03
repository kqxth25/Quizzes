package use_case.quiz;
import entity.Question;
import entity.Quiz;
import org.junit.jupiter.api.Test;
import use_case.quiz.*;
import use_case.quizimport.QuizRepository_import;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnswerQuizInteractorTest {

    private static final String[][] TEST_QUESTIONS = {
            {"Question 1"},
            {"Question 2"},
            {"Question 3"}
    };

    private static final String[][] TEST_OPTIONS = {
            {"A1", "B1", "C1", "D1"},
            {"A2", "B2", "C2", "D2"},
            {"A3", "B3", "C3", "D3"}
    };
    private static final int[] TEST_CORRECT_ANSWERS = {0, 1, 2};

    @Test
    void submitAnswer_FirstQuestion_Success() {
        QuizRepository_answer repository = new QuizRepository_answer() {
            @Override
            public String[][] getQuestions() {
                return TEST_QUESTIONS;
            }

            @Override
            public String[][] getOptions() {
                return TEST_OPTIONS;
            }

            @Override
            public int[] getCorrectAnswers() {
                return TEST_CORRECT_ANSWERS;
            }

            @Override
            public void saveAnswer(int index, int selectedOption) {
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[0];
            }

            @Override
            public void loadQuiz(String quizName) {
            }
        };

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
                assertNotNull(outputData);
                assertEquals(0, outputData.getQuestionIndex());
                assertEquals(1, outputData.getSelectedOption());
                assertEquals(1, outputData.getNextQuestionIndex());
                assertEquals("Question 2", outputData.getQuestionText());
                assertArrayEquals(new String[]{"A2", "B2", "C2", "D2"}, outputData.getOptions());
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
                fail("presentNextQuestion should not be called");
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
                fail("presentPreviousQuestion should not be called");
            }

            @Override
            public void presentNavigationWarning(String message) {
                fail("presentNavigationWarning should not be called");
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, repository);
        AnswerQuizInputData inputData = new AnswerQuizInputData(0, 1);
        interactor.submitAnswer(inputData);
    }

    @Test
    void submitAnswer_LastQuestion_DoesNotExceedBounds() {
        QuizRepository_answer repository = new QuizRepository_answer() {
            @Override
            public String[][] getQuestions() {
                return TEST_QUESTIONS;
            }

            @Override
            public String[][] getOptions() {
                return TEST_OPTIONS;
            }

            @Override
            public int[] getCorrectAnswers() {
                return TEST_CORRECT_ANSWERS;
            }

            @Override
            public void saveAnswer(int index, int selectedOption) {
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[0];
            }

            @Override
            public void loadQuiz(String quizName) {
            }
        };

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
                assertNotNull(outputData);
                assertEquals(2, outputData.getQuestionIndex());
                assertEquals(3, outputData.getSelectedOption());
                assertEquals(2, outputData.getNextQuestionIndex());
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
                fail("presentNextQuestion should not be called");
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
                fail("presentPreviousQuestion should not be called");
            }

            @Override
            public void presentNavigationWarning(String message) {
                fail("presentNavigationWarning should not be called");
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, repository);
        AnswerQuizInputData inputData = new AnswerQuizInputData(2, 3);
        interactor.submitAnswer(inputData);
    }


    @Test
    void next_FromFirstToSecond_Success() {
        QuizRepository_answer repository = new QuizRepository_answer() {
            @Override
            public String[][] getQuestions() {
                return TEST_QUESTIONS;
            }

            @Override
            public String[][] getOptions() {
                return TEST_OPTIONS;
            }

            @Override
            public int[] getCorrectAnswers() {
                return TEST_CORRECT_ANSWERS;
            }

            @Override
            public void saveAnswer(int index, int selectedOption) {
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[0];
            }

            @Override
            public void loadQuiz(String quizName) {
            }
        };

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
                fail("presentAnswer should not be called");
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
                assertNotNull(outputData);
                assertEquals(0, outputData.getQuestionIndex());
                assertEquals(1, outputData.getSelectedOption());
                assertEquals(1, outputData.getNextQuestionIndex());
                assertEquals("Question 2", outputData.getQuestionText());
                assertArrayEquals(new String[]{"A2", "B2", "C2", "D2"}, outputData.getOptions());
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
                fail("presentPreviousQuestion should not be called");
            }

            @Override
            public void presentNavigationWarning(String message) {
                fail("presentNavigationWarning should not be called");
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, repository);
        AnswerQuizInputData inputData = new AnswerQuizInputData(0, 1);
        interactor.next(inputData);
    }

    @Test
    void next_AlreadyAtLastQuestion_ShowsWarning() {
        QuizRepository_answer repository = new QuizRepository_answer() {
            @Override
            public String[][] getQuestions() {
                return TEST_QUESTIONS;
            }

            @Override
            public String[][] getOptions() {
                return TEST_OPTIONS;
            }

            @Override
            public int[] getCorrectAnswers() {
                return TEST_CORRECT_ANSWERS;
            }

            @Override
            public void saveAnswer(int index, int selectedOption) {
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[0];
            }

            @Override
            public void loadQuiz(String quizName) {
            }
        };

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
                fail("presentAnswer should not be called");
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
                fail("presentNextQuestion should not be called");
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
                fail("presentPreviousQuestion should not be called");
            }

            @Override
            public void presentNavigationWarning(String message) {
                assertEquals("Already at the last question", message);
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, repository);
        AnswerQuizInputData inputData = new AnswerQuizInputData(2, 0);
        interactor.next(inputData);
    }


    @Test
    void previous_FromSecondToFirst_Success() {
        QuizRepository_answer repository = new QuizRepository_answer() {
            @Override
            public String[][] getQuestions() {
                return TEST_QUESTIONS;
            }

            @Override
            public String[][] getOptions() {
                return TEST_OPTIONS;
            }

            @Override
            public int[] getCorrectAnswers() {
                return TEST_CORRECT_ANSWERS;
            }

            @Override
            public void saveAnswer(int index, int selectedOption) {
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[0];
            }

            @Override
            public void loadQuiz(String quizName) {
            }
        };

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
                fail("presentAnswer should not be called");
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
                fail("presentNextQuestion should not be called");
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
                assertNotNull(outputData);
                assertEquals(1, outputData.getQuestionIndex());
                assertEquals(2, outputData.getSelectedOption());
                assertEquals(0, outputData.getNextQuestionIndex());
                assertEquals("Question 1", outputData.getQuestionText());
                assertArrayEquals(new String[]{"A1", "B1", "C1", "D1"}, outputData.getOptions());
            }

            @Override
            public void presentNavigationWarning(String message) {
                fail("presentNavigationWarning should not be called");
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, repository);
        AnswerQuizInputData inputData = new AnswerQuizInputData(1, 2);
        interactor.previous(inputData);
    }

    @Test
    void previous_AlreadyAtFirstQuestion_ShowsWarning() {
        QuizRepository_answer repository = new QuizRepository_answer() {
            @Override
            public String[][] getQuestions() {
                return TEST_QUESTIONS;
            }

            @Override
            public String[][] getOptions() {
                return TEST_OPTIONS;
            }

            @Override
            public int[] getCorrectAnswers() {
                return TEST_CORRECT_ANSWERS;
            }

            @Override
            public void saveAnswer(int index, int selectedOption) {
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[0];
            }

            @Override
            public void loadQuiz(String quizName) {
            }
        };

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
                fail("presentAnswer should not be called");
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
                fail("presentNextQuestion should not be called");
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
                fail("presentPreviousQuestion should not be called");
            }

            @Override
            public void presentNavigationWarning(String message) {
                assertEquals("Already at the first question", message);
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, repository);
        AnswerQuizInputData inputData = new AnswerQuizInputData(0, 0);
        interactor.previous(inputData);
    }


    @Test
    void loadQuiz_WithImportedAdapter_CallsLoadQuiz() {
        QuizRepository_answer adapter = new ImportedQuizRepositoryAdapter(null) {
            private boolean loadQuizCalled = false;

            @Override
            public void loadQuiz(String quizName) {
                loadQuizCalled = true;
                assertEquals("Test Quiz", quizName);
            }

            @Override
            public String[][] getQuestions() {
                assertTrue(loadQuizCalled, "loadQuiz should be called before getQuestions");
                return TEST_QUESTIONS;
            }

            @Override
            public String[][] getOptions() {
                return TEST_OPTIONS;
            }

            @Override
            public int[] getCorrectAnswers() {
                return TEST_CORRECT_ANSWERS;
            }
        };

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentNavigationWarning(String message) {
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, adapter);
        interactor.loadQuiz("Test Quiz");
    }

    @Test
    void loadQuiz_WithNonAdapterRepository_DoesNotThrowException() {
        QuizRepository_answer repository = new QuizRepository_answer() {
            @Override
            public String[][] getQuestions() {
                return TEST_QUESTIONS;
            }

            @Override
            public String[][] getOptions() {
                return TEST_OPTIONS;
            }

            @Override
            public int[] getCorrectAnswers() {
                return TEST_CORRECT_ANSWERS;
            }

            @Override
            public void saveAnswer(int index, int selectedOption) {
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[0];
            }

            @Override
            public void loadQuiz(String quizName) {
            }
        };

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentNavigationWarning(String message) {
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, repository);

        assertDoesNotThrow(() -> interactor.loadQuiz("Test Quiz"));
    }


    @Test
    void testAdapterWithNullQuiz() {
        QuizRepository_import mockRepo = createMockImportRepository(null);
        ImportedQuizRepositoryAdapter adapter = new ImportedQuizRepositoryAdapter(mockRepo);

        adapter.loadQuiz("Nonexistent");

        assertEquals(0, adapter.getQuestionCount());
        assertEquals(0, adapter.getQuestions().length);
        assertEquals(0, adapter.getOptions().length);
        assertEquals(0, adapter.getCorrectAnswers().length);
    }

    @Test
    void testAdapterLoadsQuiz() {
        Question q1 = new Question("What is Java?", "A language",
                Arrays.asList("A drink", "A country"));
        Question q2 = new Question("What is Python?", "A snake",
                Arrays.asList("A language", "A tool"));

        Quiz quiz = new Quiz("Programming Quiz", 2, "Tech", "easy", "multiple",
                Arrays.asList(q1, q2));
        QuizRepository_import mockRepo = createMockImportRepository(quiz);
        ImportedQuizRepositoryAdapter adapter = new ImportedQuizRepositoryAdapter(mockRepo);

        adapter.loadQuiz("Programming Quiz");

        assertEquals(2, adapter.getQuestionCount());
        assertEquals("What is Java?", adapter.getQuestions()[0][0]);
        assertEquals("What is Python?", adapter.getQuestions()[1][0]);
        assertEquals(3, adapter.getOptions()[0].length);
        assertEquals("A language", adapter.getOptions()[0][0]);
        assertEquals(0, adapter.getCorrectAnswers()[0]);
    }

    @Test
    void testAdapterWithEmptyQuiz() {
        Quiz emptyQuiz = new Quiz("Empty", 0, "Test", "easy", "multiple", Arrays.asList());
        QuizRepository_import mockRepo = createMockImportRepository(emptyQuiz);
        ImportedQuizRepositoryAdapter adapter = new ImportedQuizRepositoryAdapter(mockRepo);

        adapter.loadQuiz("Empty");

        assertEquals(0, adapter.getQuestionCount());
    }


    @Test
    void testLocalRepositoryStoresData() {
        String[][] questions = {{"Q1"}, {"Q2"}};
        String[][] options = {{"A", "B", "C"}, {"D", "E", "F"}};
        int[] correctAnswers = {0, 1};

        LocalQuizRepositoryAnswer repo = new LocalQuizRepositoryAnswer(questions, options, correctAnswers);

        assertArrayEquals(questions, repo.getQuestions());
        assertArrayEquals(options, repo.getOptions());
        assertArrayEquals(correctAnswers, repo.getCorrectAnswers());
    }

    @Test
    void testLocalRepositoryWithEmptyData() {
        String[][] empty = new String[0][0];
        int[] emptyAnswers = new int[0];

        LocalQuizRepositoryAnswer repo = new LocalQuizRepositoryAnswer(empty, empty, emptyAnswers);

        assertEquals(0, repo.getQuestions().length);
        assertEquals(0, repo.getOptions().length);
        assertEquals(0, repo.getCorrectAnswers().length);
    }

    @Test
    void testLocalRepositorySaveAndGetAnswers() {
        String[][] questions = {{"Q1"}, {"Q2"}, {"Q3"}};
        String[][] options = {{"A", "B"}, {"C", "D"}, {"E", "F"}};
        int[] correctAnswers = {0, 1, 0};

        LocalQuizRepositoryAnswer repo = new LocalQuizRepositoryAnswer(questions, options, correctAnswers);

        // Initially all answers should be -1
        int[] savedAnswers = repo.getSavedAnswers();
        assertEquals(3, savedAnswers.length);
        assertEquals(-1, savedAnswers[0]);
        assertEquals(-1, savedAnswers[1]);
        assertEquals(-1, savedAnswers[2]);

        // Save some answers
        repo.saveAnswer(0, 1);
        repo.saveAnswer(2, 0);

        // Verify saved answers
        savedAnswers = repo.getSavedAnswers();
        assertEquals(1, savedAnswers[0]);
        assertEquals(-1, savedAnswers[1]);
        assertEquals(0, savedAnswers[2]);
    }

    @Test
    void testLocalRepositoryLoadQuiz() {
        String[][] questions = {{"Q1"}};
        String[][] options = {{"A", "B"}};
        int[] correctAnswers = {0};

        LocalQuizRepositoryAnswer repo = new LocalQuizRepositoryAnswer(questions, options, correctAnswers);

        // loadQuiz does nothing for LocalQuizRepositoryAnswer, just ensure no exception
        assertDoesNotThrow(() -> repo.loadQuiz("Test"));
    }

    @Test
    void testAdapterGetSavedAnswers() {
        QuizRepository_import mockRepo = createMockImportRepository(null);
        ImportedQuizRepositoryAdapter adapter = new ImportedQuizRepositoryAdapter(mockRepo);

        int[] savedAnswers = adapter.getSavedAnswers();
        assertEquals(0, savedAnswers.length);
    }

    @Test
    void testAdapterSaveAnswer() {
        QuizRepository_import mockRepo = createMockImportRepository(null);
        ImportedQuizRepositoryAdapter adapter = new ImportedQuizRepositoryAdapter(mockRepo);

        // saveAnswer does nothing for ImportedQuizRepositoryAdapter, just ensure no exception
        assertDoesNotThrow(() -> adapter.saveAnswer(0, 1));
    }

    @Test
    void testSaveAnswer() {
        String[][] questions = {{"Q1"}, {"Q2"}};
        String[][] options = {{"A", "B"}, {"C", "D"}};
        int[] correctAnswers = {0, 1};

        LocalQuizRepositoryAnswer repository = new LocalQuizRepositoryAnswer(questions, options, correctAnswers);

        AnswerQuizOutputBoundary presenter = new AnswerQuizOutputBoundary() {
            @Override
            public void presentAnswer(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentNextQuestion(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentPreviousQuestion(AnswerQuizOutputData outputData) {
            }

            @Override
            public void presentNavigationWarning(String message) {
            }
        };

        AnswerQuizInteractor interactor = new AnswerQuizInteractor(presenter, repository);
        AnswerQuizInputData inputData = new AnswerQuizInputData(1, 2);
        interactor.saveAnswer(inputData);

        // Verify answer was saved
        int[] savedAnswers = repository.getSavedAnswers();
        assertEquals(2, savedAnswers[1]);
    }

    private QuizRepository_import createMockImportRepository(Quiz quiz) {
        return new QuizRepository_import() {
            @Override
            public void save(Quiz quiz) {}

            @Override
            public List<Quiz> getAll() {
                return quiz != null ? Arrays.asList(quiz) : Arrays.asList();
            }

            @Override
            public Quiz getByName(String name) {
                return quiz;
            }

            @Override
            public void delete(String name) {}
        };
    }
}