package interface_adapter.creator_login;

import use_case.creator_login.*;

public class CreatorLoginController {

    private final CreatorLoginInputBoundary interactor;

    public CreatorLoginController(CreatorLoginInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String password){
        interactor.execute(new CreatorLoginInputData(password));
    }
}
