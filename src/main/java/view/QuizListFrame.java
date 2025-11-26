package view;

import data_access.QuizRepository;
import entity.Quiz;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizListFrame extends JFrame {

    private final QuizRepository repository;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> listUI;

    public QuizListFrame(QuizRepository repository) {
        super("Quiz Manager");
        this.repository = repository;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        listUI = new JList<>(listModel);
        add(new JScrollPane(listUI), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton importBtn = new JButton("Import");
        JButton previewBtn = new JButton("Preview");
        JButton deleteBtn = new JButton("Delete");

        controls.add(importBtn);
        controls.add(previewBtn);
        controls.add(deleteBtn);
        add(controls, BorderLayout.SOUTH);

        importBtn.addActionListener(e -> new ImportQuizFrame(this, repository, this));
        previewBtn.addActionListener(e -> previewSelected());
        deleteBtn.addActionListener(e -> deleteSelected());

        refreshQuizList();
        setVisible(true);
    }

    public void refreshQuizList() {
        listModel.clear();
        List<Quiz> quizzes = repository.getAllQuizzes();
        for (Quiz q : quizzes) listModel.addElement(q.getName());
    }

    private void previewSelected() {
        String selected = listUI.getSelectedValue();
        if (selected == null) { JOptionPane.showMessageDialog(this, "Select a quiz first."); return; }
        Quiz q = repository.getQuizByName(selected);
        if (q == null) { JOptionPane.showMessageDialog(this, "Quiz not found."); return; }
        new QuizPreviewFrame(this, q);
    }

    private void deleteSelected() {
        String selected = listUI.getSelectedValue();
        if (selected == null) { JOptionPane.showMessageDialog(this, "Select a quiz first."); return; }

        int ok = JOptionPane.showConfirmDialog(this, "Delete \""+selected+"\"?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            repository.deleteQuiz(selected);
            refreshQuizList();
        }
    }
}
