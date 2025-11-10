package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.selectquiz.SelectQuizViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

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

        viewManagerModel.setCurrentUser(out.getUsername());

        loginViewModel.setState(new LoginState());
        loginViewModel.firePropertyChange();

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
