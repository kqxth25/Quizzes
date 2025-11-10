package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ManageView extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizListFrame());
    }
}

/** ------------------ MAIN QUIZ LIST ------------------ **/
class QuizListFrame extends JFrame {
    private JList<String> quizList;
    private DefaultListModel<String> listModel;

    public QuizListFrame() {
        setTitle("Quizzes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        listModel = new DefaultListModel<>();
        listModel.addElement("Quiz 1");
        listModel.addElement("Quiz 2");
        quizList = new JList<>(listModel);
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(quizList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton importBtn = new JButton("Import");
        JButton previewBtn = new JButton("Preview");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(importBtn);
        buttonPanel.add(previewBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        importBtn.addActionListener(e -> new ImportQuizFrame(this));
        previewBtn.addActionListener(e -> previewQuiz());
        deleteBtn.addActionListener(e -> deleteQuiz());

        setVisible(true);
    }

    private void previewQuiz() {
        String selected = quizList.getSelectedValue();
        if (selected != null)
            new QuizPreviewFrame(selected);
        else
            JOptionPane.showMessageDialog(this, "Select a quiz to preview.");
    }

    private void deleteQuiz() {
        String selected = quizList.getSelectedValue();
        if (selected != null)
            new DeleteConfirmDialog(this, selected, listModel);
        else
            JOptionPane.showMessageDialog(this, "Select a quiz to delete.");
    }
}

/** ------------------ IMPORT QUIZ FORM ------------------ **/
class ImportQuizFrame extends JFrame {
    public ImportQuizFrame(JFrame parent) {
        setTitle("Import Quiz");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Quiz Name:");
        JTextField nameField = new JTextField("Quiz 3");

        JLabel numLabel = new JLabel("Number of Questions:");
        JTextField numField = new JTextField("10");

        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Any", "Science", "History", "Film"});

        JLabel difficultyLabel = new JLabel("Difficulty:");
        JComboBox<String> difficultyBox = new JComboBox<>(new String[]{"Any", "Easy", "Medium", "Hard"});

        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Any", "Multiple Choice", "True/False"});

        JButton createBtn = new JButton("Create");

        gbc.gridx = 0; gbc.gridy = 0; add(nameLabel, gbc);
        gbc.gridx = 1; add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(numLabel, gbc);
        gbc.gridx = 1; add(numField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(categoryLabel, gbc);
        gbc.gridx = 1; add(categoryBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(difficultyLabel, gbc);
        gbc.gridx = 1; add(difficultyBox, gbc);
        gbc.gridx = 0; gbc.gridy = 4; add(typeLabel, gbc);
        gbc.gridx = 1; add(typeBox, gbc);
        gbc.gridx = 1; gbc.gridy = 5; add(createBtn, gbc);

        createBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Quiz created: " + nameField.getText());
            dispose();
        });

        setVisible(true);
    }
}

/** ------------------ QUIZ PREVIEW ------------------ **/
class QuizPreviewFrame extends JFrame {
    public QuizPreviewFrame(String quizName) {
        setTitle(quizName + " Preview");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        // Quiz info panel at the top
        JPanel infoPanel = new JPanel(new GridLayout(4,2,5,5));
        infoPanel.add(new JLabel("Questions:"));
        infoPanel.add(new JLabel("10"));
        infoPanel.add(new JLabel("Category:"));
        infoPanel.add(new JLabel("Entertainment: Film"));
        infoPanel.add(new JLabel("Difficulty:"));
        infoPanel.add(new JLabel("Easy"));
        infoPanel.add(new JLabel("Type:"));
        infoPanel.add(new JLabel("True/False"));
        add(infoPanel, BorderLayout.NORTH);

        // Questions list using DefaultListModel
        DefaultListModel<String> questionModel = new DefaultListModel<>();
        questionModel.addElement("George Lucas directed the entire Star Wars trilogy.");
        questionModel.addElement("Shaquille O'Neal appeared in the 1997 film *Steel*.");
        questionModel.addElement("The Godfather was released in 1990.");
        questionModel.addElement("Titanic won 11 Academy Awards.");
        questionModel.addElement("The Matrix was directed by James Cameron.");
        questionModel.addElement("Jurassic Park was released in 1993.");
        questionModel.addElement("Avatar is the highest-grossing film ever.");
        questionModel.addElement("Robert Downey Jr. played Iron Man.");
        questionModel.addElement("Frozen was a Pixar movie.");
        questionModel.addElement("The Lion King was produced by Disney.");

        JList<String> questionList = new JList<>(questionModel);
        questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionList.setVisibleRowCount(8);
        questionList.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(questionList);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}


/** ------------------ DELETE CONFIRMATION ------------------ **/
class DeleteConfirmDialog extends JDialog {
    public DeleteConfirmDialog(JFrame parent, String quizName, DefaultListModel<String> model) {
        super(parent, "Confirm Delete", true);
        setSize(300,150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10,10));

        JLabel confirmLabel = new JLabel("Are you sure you want to delete " + quizName + "?", SwingConstants.CENTER);
        add(confirmLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton yesBtn = new JButton("Yes");
        JButton noBtn = new JButton("No");
        buttonPanel.add(yesBtn);
        buttonPanel.add(noBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        yesBtn.addActionListener(e -> {
            model.removeElement(quizName);
            dispose();
        });
        noBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
