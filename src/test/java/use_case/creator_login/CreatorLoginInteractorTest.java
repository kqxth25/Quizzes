package use_case.creator_login;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreatorLoginInteractorTest {

    @Test
    void testLoginSuccess_whenPasswordIsKfc() {
        // Fake presenter to capture output
        TestPresenter presenter = new TestPresenter();
        CreatorLoginInteractor interactor = new CreatorLoginInteractor(presenter);

        CreatorLoginInputData input = new CreatorLoginInputData("kfc");
        interactor.execute(input);

        assertTrue(presenter.successCalled, "Success view should be called");
        assertFalse(presenter.failCalled, "Fail view should NOT be called");
    }

    @Test
    void testLoginFailure_whenPasswordIsWrong() {
        TestPresenter presenter = new TestPresenter();
        CreatorLoginInteractor interactor = new CreatorLoginInteractor(presenter);

        CreatorLoginInputData input = new CreatorLoginInputData("wrongpass");
        interactor.execute(input);

        assertFalse(presenter.successCalled, "Success view should NOT be called");
        assertTrue(presenter.failCalled, "Fail view should be called");
        assertEquals("Incorrect password", presenter.errorMessage);
    }

    @Test
    void testLoginFailure_whenPasswordIsEmpty() {
        TestPresenter presenter = new TestPresenter();
        CreatorLoginInteractor interactor = new CreatorLoginInteractor(presenter);

        CreatorLoginInputData input = new CreatorLoginInputData("");
        interactor.execute(input);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertEquals("Incorrect password", presenter.errorMessage);
    }
}
