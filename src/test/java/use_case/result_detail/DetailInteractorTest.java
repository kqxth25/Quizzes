package use_case.result_detail;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DetailInteractorTest {

    @Test
    void testLoadDetail_success() {
        FakeRepo repo = new FakeRepo();
        TestPresenter presenter = new TestPresenter();

        DetailInteractor interactor = new DetailInteractor(repo, presenter);

        interactor.loadDetail();

        assertTrue(presenter.called, "Presenter should be called");
        assertTrue(presenter.response != null);
        assertNotNull(presenter.response);
        assertEquals("Q1?", presenter.response.questions[0]);
        assertEquals((1), presenter.response.correctIndex[0]);
        assertEquals(2, presenter.response.savedAnswers[0]);
        assertArrayEquals(new String[]{"A", "B", "C"}, presenter.response.options[0]);
    }

    @Test
    void testLoadDetail_handlesNullRepoData() {
        FakeRepo repo = new FakeRepo();
        repo.returnNull = true;

        TestPresenter presenter = new TestPresenter();
        DetailInteractor interactor = new DetailInteractor(repo, presenter);

        interactor.loadDetail();

        assertTrue(presenter.called, "should be true");
        assertNotNull(presenter.response);
        assertEquals(0, presenter.response.questions.length);
        assertEquals(0, presenter.response.options.length);
    }

    @Test
    void testLoadDetail_nullInnerQuestionArray() {
        FakeRepo repo = new FakeRepo() {
            @Override
            public String[][] getQuestions() {
                return new String[][]{ null };
            }
        };

        TestPresenter presenter = new TestPresenter();
        DetailInteractor interactor = new DetailInteractor(repo, presenter);

        interactor.loadDetail();

        assertTrue(presenter.called);
        assertEquals(1, presenter.response.questions.length);
        assertEquals("", presenter.response.questions[0]);
    }

    @Test
    void testLoadDetail_nullInnerOptionsArray() {
        FakeRepo repo = new FakeRepo() {
            @Override
            public String[][] getOptions() {
                return new String[][]{ null };
            }
        };

        TestPresenter presenter = new TestPresenter();
        DetailInteractor interactor = new DetailInteractor(repo, presenter);

        interactor.loadDetail();

        assertTrue(presenter.called);
        assertEquals(1, presenter.response.options.length);
        assertEquals(0, presenter.response.options[0].length);
    }

    @Test
    void testLoadDetail_shortCorrectOrSavedArrays() {
        FakeRepo repo = new FakeRepo() {
            @Override
            public String[][] getQuestions() {
                return new String[][]{{"Q1?"}};
            }

            @Override
            public int[] getCorrectAnswers() {
                return new int[0];
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[0];
            }
        };

        TestPresenter presenter = new TestPresenter();
        DetailInteractor interactor = new DetailInteractor(repo, presenter);

        interactor.loadDetail();

        assertTrue(presenter.called);
        assertEquals(0, presenter.response.correctIndex[0]);
        assertEquals(-1, presenter.response.savedAnswers[0]);
    }

    @Test
    void testLoadDetail_optionsShorterThanQuestions() {
        FakeRepo repo = new FakeRepo() {
            @Override
            public String[][] getQuestions() {
                return new String[][]{
                        {"Q1?"},
                        {"Q2?"}
                };
            }

            @Override
            public String[][] getOptions() {
                return new String[][]{
                        {"A", "B"}
                };
            }

            @Override
            public int[] getCorrectAnswers() {
                return new int[]{1, 0};
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[]{0, -1};
            }
        };

        TestPresenter presenter = new TestPresenter();
        DetailInteractor interactor = new DetailInteractor(repo, presenter);

        interactor.loadDetail();

        assertTrue(presenter.called);
        assertEquals(2, presenter.response.options.length);

        assertArrayEquals(new String[]{"A", "B"}, presenter.response.options[0]);

        assertEquals(0, presenter.response.options[1].length);
    }
    @Test
    void testLoadDetail_emptyQuestionsArray() {
        FakeRepo repo = new FakeRepo() {
            @Override
            public String[][] getQuestions() {
                return new String[0][];
            }

            @Override
            public String[][] getOptions() {
                return new String[][]{};
            }

            @Override
            public int[] getCorrectAnswers() {
                return new int[]{};
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[]{};
            }
        };

        TestPresenter presenter = new TestPresenter();
        DetailInteractor interactor = new DetailInteractor(repo, presenter);

        interactor.loadDetail();

        assertTrue(presenter.called);
        assertEquals(0, presenter.response.questions.length);
        assertEquals(0, presenter.response.options.length);
        assertEquals(0, presenter.response.correctIndex.length);
        assertEquals(0, presenter.response.savedAnswers.length);
    }
    @Test
    void testLoadDetail_emptyQuestionRow() {
        FakeRepo repo = new FakeRepo() {
            @Override
            public String[][] getQuestions() {
                return new String[][]{
                        new String[0]
                };
            }

            @Override
            public String[][] getOptions() {
                return new String[][]{
                        new String[]{"A"}
                };
            }

            @Override
            public int[] getCorrectAnswers() {
                return new int[]{0};
            }

            @Override
            public int[] getSavedAnswers() {
                return new int[]{-1};
            }
        };

        TestPresenter presenter = new TestPresenter();
        DetailInteractor interactor = new DetailInteractor(repo, presenter);

        interactor.loadDetail();

        assertTrue(presenter.called);
        assertEquals("", presenter.response.questions[0]);
    }

}
