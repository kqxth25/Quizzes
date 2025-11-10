package data_access;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the user data access object.
 * Supports both login and signup use cases.
 * This implementation does NOT persist data between runs.
 */
public class InMemoryUserDataAccessObject
        implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public boolean existsByName(String username) {
        return username != null && users.containsKey(username.trim());
    }

    @Override
    public void save(User user) {
        if (user == null || user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User or username cannot be null or empty.");
        }
        users.put(user.getName().trim(), user);
    }

    @Override
    public User get(String username) {
        if (username == null) {
            return null;
        }
        return users.get(username.trim());
    }
}
