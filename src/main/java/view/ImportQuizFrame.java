package view;

import data_access.QuizRepository;
import interface_adapter.quizimport.QuizImportController;
import interface_adapter.quizimport.QuizImportPresenter;
import use_case.quizimport.QuizImportInteractor;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.*;
import interface_adapter.quizimport.CategoryMapper;

public class ImportQuizFrame extends JFrame {
    private final QuizImportController controller;
    private final QuizListFrame parentListFrame;

    public ImportQuizFrame(JFrame parent, QuizRepository repository, QuizListFrame parentListFrame) {
        super("Import Quiz");
        this.parentListFrame = parentListFrame;

        setSize(420, 320);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        // wire Clean Architecture pieces for this view
        QuizImportPresenter presenter = new QuizImportPresenter(this);
        QuizImportInteractor interactor = new QuizImportInteractor(presenter, repository);
        this.controller = new QuizImportController(interactor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Quiz Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField("Quiz " + System.currentTimeMillis()%1000, 18);
        add(nameField, gbc);

        // amount
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Number of Questions:"), gbc);
        gbc.gridx = 1;
        JTextField amountField = new JTextField("10", 6);
        // restrict to digits
        ((AbstractDocument) amountField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) super.insertString(fb, offset, string, attr);
            }
            @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) super.replace(fb, offset, length, text, attrs);
            }
        });
        add(amountField, gbc);

        // category
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> categoryBox = new JComboBox<>(CategoryMapper.getCategoryNames());
        add(categoryBox, gbc);

        // difficulty
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Difficulty:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> difficultyBox = new JComboBox<>(new String[] {"Any","Easy","Medium","Hard"});
        add(difficultyBox, gbc);

        // type
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> typeBox = new JComboBox<>(new String[] {"Any","Multiple Choice","Boolean"});
        add(typeBox, gbc);

        // import button
        gbc.gridx = 1; gbc.gridy = 5;
        JButton importBtn = new JButton("Import");
        add(importBtn, gbc);

        importBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Please enter a name."); return; }

                int amount = Integer.parseInt(amountField.getText().trim());
                if (amount <= 0) { JOptionPane.showMessageDialog(this, "Amount must be > 0"); return; }

                String category = (String) categoryBox.getSelectedItem(); // human name, maybe "Any"
                String difficulty = ((String) difficultyBox.getSelectedItem()).toLowerCase(); // "any","easy",...
                String type = ((String) typeBox.getSelectedItem()).toLowerCase(); // "any","multiple choice","boolean"

                // normalize type to API-friendly tokens
                String typeForInteractor = "any";
                if (type.startsWith("multi")) typeForInteractor = "multiple";
                else if (type.startsWith("bool") || type.contains("true")) typeForInteractor = "boolean";

                controller.importQuiz(name, amount, category, difficulty, typeForInteractor);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Number of questions must be a number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    // called by presenter on success
    public void showSuccess(entity.Quiz quiz) {
        JOptionPane.showMessageDialog(this, "Imported: " + quiz.getName());
        // refresh parent list view
        if (parentListFrame != null) parentListFrame.refreshQuizList();
        dispose();
    }

    // called by presenter on error
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, "Import error: " + message);
    }
}
