package view;

import data_access.InMemoryQuizRepository;
import data_access.QuizRepository;

import javax.swing.SwingUtilities;

public class ManageView {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizRepository repo = new InMemoryQuizRepository();
            new QuizListFrame(repo);
        });
    }
}
