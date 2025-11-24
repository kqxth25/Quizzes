package use_case.creator_login;

public class CreatorLoginInteractor implements CreatorLoginInputBoundary {

    private final CreatorLoginOutputBoundary presenter;

    public CreatorLoginInteractor(CreatorLoginOutputBoundary presenter){
        this.presenter = presenter;
    }

    @Override
    public void execute(CreatorLoginInputData inputData){
        if ("kfc".equals(inputData.getPassword())){
            presenter.prepareSuccessView();
        } else {
            presenter.prepareFailedView("Incorrect password");
        }
    }
}
