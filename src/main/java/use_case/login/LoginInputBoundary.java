package use_case.login;

public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);
}