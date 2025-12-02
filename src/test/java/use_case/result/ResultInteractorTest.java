package use_case.result;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultInteractorTest {

    @Test
    void testComputeResult_allCorrect() {
        FakeRepository repo = new FakeRepository(
                new int[]{1, 2, 3},
                new int[]{1, 2, 3}
        );
        TestPresenter presenter = new TestPresenter();

        FakeQuizViewModel quizVM = new FakeQuizViewModel();

        ResultInteractor interactor =
                new ResultInteractor(repo, presenter, quizVM);

        interactor.computeResult();

        assertTrue(presenter.called);
        assertNotNull(presenter.response);

        assertEquals(3, presenter.response.getScore());
        assertEquals(3, presenter.response.getTotal());
    }

    @Test
    void testComputeResult_someIncorrect() {
        FakeRepository repo = new FakeRepository(
                new int[]{1, 2, 3},
                new int[]{1, 0, 3}
        );
        TestPresenter presenter = new TestPresenter();
        FakeQuizViewModel quizVM = new FakeQuizViewModel();

        ResultInteractor interactor =
                new ResultInteractor(repo, presenter, quizVM);

        interactor.computeResult();

        assertTrue(presenter.called);
        assertEquals(2, presenter.response.getScore());
        assertEquals(3, presenter.response.getTotal());
    }

    @Test
    void testComputeResult_noneCorrect() {
        FakeRepository repo = new FakeRepository(
                new int[]{0, 0, 0},
                new int[]{1, 2, 3}
        );
        TestPresenter presenter = new TestPresenter();
        FakeQuizViewModel quizVM = new FakeQuizViewModel();

        ResultInteractor interactor =
                new ResultInteractor(repo, presenter, quizVM);

        interactor.computeResult();

        assertTrue(presenter.called);
        assertEquals(0, presenter.response.getScore());
        assertEquals(3, presenter.response.getTotal());
    }
}
