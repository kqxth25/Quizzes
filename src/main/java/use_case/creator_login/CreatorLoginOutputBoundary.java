package use_case.creator_login;

public interface CreatorLoginOutputBoundary {
    void prepareSuccessView();
    void prepareFailedView(String errorMessage);
}
