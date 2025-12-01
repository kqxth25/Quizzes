package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.selectquiz.SelectQuizViewModel;
import use_case.user_login.LoginOutputBoundary;
import use_case.user_login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final SelectQuizViewModel selectQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          SelectQuizViewModel selectQuizViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.selectQuizViewModel = selectQuizViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData out) {

        // 1. 存用户信息到 ViewManagerModel（你本来有）
        viewManagerModel.setCurrentUser(out.getUsername());

        // 2. 更新 LoginViewModel 的 state，写入 username
        LoginState s = new LoginState();
        s.setUsername(out.getUsername());
        loginViewModel.setState(s);

        loginViewModel.firePropertyChange();

        // 3. 跳转
        viewManagerModel.navigate(selectQuizViewModel.getViewName());
    }


    @Override
    public void prepareFailView(String error) {
        LoginState s = loginViewModel.getState();
        s.setLoginError(error);
        loginViewModel.setState(s);
        loginViewModel.firePropertyChange();
    }
}
