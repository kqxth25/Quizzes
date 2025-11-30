package use_case.user_login;

import entity.User;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
            return;
        }

        final User user = userDataAccessObject.get(username);
        if (user == null) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
            return;
        }

        if (!password.equals(user.getPassword())) {
            loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            return;
        }

        loginPresenter.prepareSuccessView(new LoginOutputData(user.getName()));
    }
}