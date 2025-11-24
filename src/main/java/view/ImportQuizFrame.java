package view;

import entity.Quiz;
import interface_adapter.quizimport.QuizImportPresenter;
import use_case.quizimport.QuizImportInteractor;
import use_case.quizimport.QuizImportInputData;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class ImportQuizFrame extends JFrame {

    private final QuizImportInteractor interactor;

    private JTextField nameField;
    private JTextField amountField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> difficultyBox;
    private JComboBox<String> typeBox;
    private JButton createBtn;

    public ImportQuizFrame(JFrame parent) {
        setTitle("Import Quiz");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize presenter and interactor
        QuizImportPresenter presenter = new QuizImportPresenter(this);
        interactor = new QuizImportInteractor(presenter);

        // Quiz Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Quiz Name:"), gbc);
        nameField = new JTextField("Quiz 1");
        gbc.gridx = 1;
        add(nameField, gbc);

        // Number of Questions
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Number of Questions:"), gbc);
        amountField = new JTextField("10");
        ((AbstractDocument) amountField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d+")) super.insertString(fb, offset, string, attr);
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("\\d*")) super.replace(fb, offset, length, text, attrs);
            }
        });
        gbc.gridx = 1;
        add(amountField, gbc);

        // Category
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Category:"), gbc);
        categoryBox = new JComboBox<>(new String[]{"Any", "Science", "History", "Film", "Music", "Video Games"});
        gbc.gridx = 1;
        add(categoryBox, gbc);

        // Difficulty
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Difficulty:"), gbc);
        difficultyBox = new JComboBox<>(new String[]{"Any", "Easy", "Medium", "Hard"});
        gbc.gridx = 1;
        add(difficultyBox, gbc);

        // Type
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Type:"), gbc);
        typeBox = new JComboBox<>(new String[]{"Any", "Multiple Choice", "True/False"});
        gbc.gridx = 1;
        add(typeBox, gbc);

        // Create / Import Button
        createBtn = new JButton("Import");
        gbc.gridx = 1; gbc.gridy = 5;
        add(createBtn, gbc);

        createBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int amount = Integer.parseInt(amountField.getText());
                String category = categoryBox.getSelectedItem().toString();
                String difficulty = difficultyBox.getSelectedItem().toString();
                String type = typeBox.getSelectedItem().toString();

                // Wrap inputs in Clean Architecture input data object
                QuizImportInputData inputData = new QuizImportInputData(name, amount, category, difficulty, type);

                // Call interactor
                interactor.importQuiz(inputData);

            } catch (NumberFormatException ex) {
                showError("Number of questions must be a valid number.");
            }
        });

        setVisible(true);
    }

    // Called by the presenter after a successful import
    public void showSuccess(Quiz quiz) {
        JOptionPane.showMessageDialog(this, "Quiz imported successfully: " + quiz.getName());
        dispose();
    }

    // Called by the presenter if an error occurs
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, "Error: " + message);
    }
}
