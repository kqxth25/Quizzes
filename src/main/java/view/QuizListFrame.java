package view;

import javax.swing.*;
import java.awt.*;
import interface_adapter.quizimport.QuizImportController;

public class QuizListFrame extends JFrame {

    private JList<String> quizList;
    private DefaultListModel<String> listModel;

    public QuizListFrame() {
        setTitle("Quizzes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        listModel = new DefaultListModel<>();
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
            new QuizPreviewFrame(selected); // pass name, controller can be added later
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

    // Methods to update list from controller
    public void addQuiz(String name) {
        if (!listModel.contains(name)) listModel.addElement(name);
    }

    public void removeQuiz(String name) {
        listModel.removeElement(name);
    }
}
