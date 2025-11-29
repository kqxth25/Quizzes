package use_case.user_signup;

import java.util.Objects;

public class SignupOutputData {
    private final String username;

    public SignupOutputData(String username) {
        this.username = Objects.requireNonNull(username, "username");
    }

    public String getUsername() {
        return username;
    }
}
