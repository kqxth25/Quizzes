package use_case.creator_login;

public class CreatorLoginInputData {

    private final String password;

    public CreatorLoginInputData(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
