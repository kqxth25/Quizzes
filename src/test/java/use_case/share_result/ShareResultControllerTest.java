package use_case.share_result;

import interface_adapter.share_result.ShareResultController;
import interface_adapter.share_result.ShareResultState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.result.ResultState;
import interface_adapter.result.ResultViewModel;
import interface_adapter.selectquiz.SelectQuizViewModel;
import interface_adapter.share_result.ShareResultViewModel;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ShareResultControllerTest {

    /** Fake QuizItemDto for selectQuiz VM */
    private static class FakeQuizItemDto extends use_case.selectquiz.QuizItemDto {
        public FakeQuizItemDto(String title) {
            super(title, "cat", "diff", "type");
        }
    }

    @Test
    void testLoadShareData() {

        LoginViewModel loginVM = new LoginViewModel();
        loginVM.getState().setUsername("alice");

        SelectQuizViewModel selectVM = new SelectQuizViewModel();
        selectVM.setQuizzes(Collections.singletonList(
                new FakeQuizItemDto("Math Quiz")
        ));

        ResultViewModel resultVM = new ResultViewModel(new ResultState());
        resultVM.setScore(85);
        resultVM.setTotal(10);

        ShareResultViewModel shareVM = new ShareResultViewModel();

        ShareResultController controller =
                new ShareResultController(shareVM, loginVM, selectVM, resultVM);

        controller.loadShareData();

        ShareResultState state = shareVM.getState();

        assertEquals("alice", state.getUsername());
        assertEquals("Math Quiz", state.getQuizName());
        assertEquals(85, state.getScore());
        assertEquals(10, state.getTotal());
    }
}
