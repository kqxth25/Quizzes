package view;

import javax.swing.*;
import java.awt.*;

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

        JPanel buttonPanel = new JPanel();
        JButton importBtn = new JButton("Import");
        JButton previewBtn = new JButton("Preview");
        JButton deleteBtn = new JButton("Delete");
        buttonPanel.add(importBtn);
        buttonPanel.add(previewBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        importBtn.addActionListener(e -> new ImportQuizFrame(this));
        setVisible(true);
    }
}
