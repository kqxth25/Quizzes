package data_access;

import entity.User;
import entity.UserFactory;
import use_case.user_login.LoginUserDataAccessInterface;
import use_case.user_signup.SignupUserDataAccessInterface;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File-based implementation of the user data access object.
 * Stores users in a local CSV file: username,password
 * This implementation PERSISTS data between runs.
 */
public class FileUserDataAccessObject
        implements LoginUserDataAccessInterface, SignupUserDataAccessInterface {

    private static final String HEADER = "username,password";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final UserFactory userFactory;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) {
        this.csvFile = new File(csvPath);
        this.userFactory = userFactory;

        headers.put("username", 0);
        headers.put("password", 1);

        try {
            if (!csvFile.exists()) {
                File parent = csvFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                csvFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create user CSV file: " + csvPath, e);
        }

        if (csvFile.length() == 0) {
            writeToFile();
        } else {
            loadFromFile();
        }
    }

    private void loadFromFile() {
        users.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                writeToFile();
                return;
            }

            if (!headerLine.equals(HEADER)) {
                throw new RuntimeException(String.format(
                        "User CSV header should be:%n%s%nbut was:%n%s",
                        HEADER, headerLine));
            }

            String row;
            while ((row = reader.readLine()) != null) {
                if (row.isBlank()) continue;

                String[] cols = row.split(",", -1);
                String username = cols[headers.get("username")].trim();
                String password = cols[headers.get("password")];

                if (username.isEmpty()) continue;

                User user = userFactory.create(username, password);
                users.put(username, user);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read users from CSV file: " + csvFile.getPath(), e);
        }
    }

    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, false))) {
            writer.write(HEADER);
            writer.newLine();

            for (User user : users.values()) {
                String line = String.format("%s,%s",
                        user.getName().trim(),
                        user.getPassword() == null ? "" : user.getPassword());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write users to CSV file: " + csvFile.getPath(), e);
        }
    }

    @Override
    public boolean existsByName(String username) {
        if (username == null) return false;
        return users.containsKey(username.trim());
    }

    @Override
    public void save(User user) {
        if (user == null || user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User or username cannot be null or empty.");
        }
        String trimmedName = user.getName().trim();
        users.put(trimmedName, user);
        writeToFile();
    }

    @Override
    public User get(String username) {
        if (username == null) return null;
        return users.get(username.trim());
    }
}
