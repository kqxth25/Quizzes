package view;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import interface_adapter.quizimport.QuizImportController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImportQuizFrame extends JFrame {

    public ImportQuizFrame(JFrame parent) {
        setTitle("Import Quiz");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Quiz Name:");
        JTextField nameField = new JTextField();

        JLabel numLabel = new JLabel("Number of Questions:");
        JTextField numField = new JTextField();

        ((AbstractDocument) numField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+")) super.insertString(fb, offset, string, attr);
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) super.replace(fb, offset, length, text, attrs);
            }
        });

        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Any", "Science", "History", "Film"});

        JLabel difficultyLabel = new JLabel("Difficulty:");
        JComboBox<String> difficultyBox = new JComboBox<>(new String[]{"Any", "Easy", "Medium", "Hard"});

        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Any", "Multiple Choice", "True/False"});

        JButton createBtn = new JButton("Create");

        gbc.gridx=0; gbc.gridy=0; add(nameLabel, gbc); gbc.gridx=1; add(nameField, gbc);
        gbc.gridx=0; gbc.gridy=1; add(numLabel, gbc); gbc.gridx=1; add(numField, gbc);
        gbc.gridx=0; gbc.gridy=2; add(categoryLabel, gbc); gbc.gridx=1; add(categoryBox, gbc);
        gbc.gridx=0; gbc.gridy=3; add(difficultyLabel, gbc); gbc.gridx=1; add(difficultyBox, gbc);
        gbc.gridx=0; gbc.gridy=4; add(typeLabel, gbc); gbc.gridx=1; add(typeBox, gbc);
        gbc.gridx=1; gbc.gridy=5; add(createBtn, gbc);

        createBtn.addActionListener(e -> {
            // Example call to controller (to be implemented)
            String name = nameField.getText();
            int amount = Integer.parseInt(numField.getText());
            String category = (String) categoryBox.getSelectedItem();
            String difficulty = (String) difficultyBox.getSelectedItem();
            String type = (String) typeBox.getSelectedItem();

            // ImportQuizController controller = ... inject
            // controller.importQuiz(name, amount, category, difficulty, type);

            JOptionPane.showMessageDialog(this, "Quiz created: " + name);
            dispose();
        });

        setVisible(true);
    }
}
