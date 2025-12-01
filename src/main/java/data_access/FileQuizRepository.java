package data_access;

import entity.Quiz;
import use_case.quizimport.QuizRepository_import;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileQuizRepository implements QuizRepository_import {

    private final File dataFile;
    private final List<Quiz> quizzes = new ArrayList<>();

    public FileQuizRepository(String path) {
        this.dataFile = new File(path);

        try {
            if (!dataFile.exists()) {
                File parent = dataFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                dataFile.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create quiz data file: " + path, e);
        }

        if (dataFile.length() == 0) {
            saveToFile();
        } else {
            loadFromFile();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        quizzes.clear();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile))) {
            Object obj = in.readObject();
            if (obj instanceof List) {
                quizzes.addAll((List<Quiz>) obj);
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load quizzes from file", e);
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFile, false))) {
            out.writeObject(quizzes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save quizzes to file", e);
        }
    }

    @Override
    public synchronized void save(Quiz quiz) {
        quizzes.add(quiz);
        saveToFile();
    }

    @Override
    public synchronized Quiz getByName(String name) {
        return quizzes.stream()
                .filter(q -> q.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Quiz> getAll() {
        return new ArrayList<>(quizzes);
    }

    @Override
    public synchronized void delete(String name) {
        quizzes.removeIf(q -> q.getName().equals(name));
        saveToFile();
    }
}
