package use_case.quiz;

import org.junit.jupiter.api.Test;

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
}