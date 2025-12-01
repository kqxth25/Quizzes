package interface_adapter.share_result;

import interface_adapter.login.LoginViewModel;
import interface_adapter.result.ResultViewModel;
import interface_adapter.selectquiz.SelectQuizViewModel;

public class ShareResultController {

    private final ShareResultViewModel shareResultViewModel;
    private final LoginViewModel loginViewModel;
    private final SelectQuizViewModel selectQuizViewModel;
    private final ResultViewModel resultViewModel;

    public ShareResultController(
            ShareResultViewModel vm,
            LoginViewModel loginVM,
            SelectQuizViewModel selectVM,
            ResultViewModel resultVM
    ) {
        this.shareResultViewModel = vm;
        this.loginViewModel = loginVM;
        this.selectQuizViewModel = selectVM;
        this.resultViewModel = resultVM;
    }

    public void loadShareData() {

        String username = loginViewModel.getState().getUsername();

        String quizName = "";
        if (!selectQuizViewModel.getQuizzes().isEmpty()) {
            quizName = selectQuizViewModel.getQuizzes().get(0).getTitle();
        }

        double score = resultViewModel.getState().getScore();
        int total = resultViewModel.getState().getTotal();

        ShareResultState s = new ShareResultState(username, quizName, score, total);
        shareResultViewModel.setState(s);
    }
}
