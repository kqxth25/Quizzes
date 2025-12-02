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
}
