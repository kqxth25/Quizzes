package view;

import javax.swing.*;
import java.awt.*;
import entity.Quiz;

public class QuizPreviewFrame extends JFrame {
    public QuizPreviewFrame(Quiz quiz) {
        setTitle(quiz.getName() + " Preview");
        setSize(500,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        DefaultListModel<String> questionModel = new DefaultListModel<>();
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            questionModel.addElement((i+1) + ". " + quiz.getQuestions().get(i).getQuestionText());
        }

        JList<String> questionList = new JList<>(questionModel);
        add(new JScrollPane(questionList), BorderLayout.CENTER);
        setVisible(true);
    }
}
