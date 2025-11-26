package view;

import entity.Question;
import entity.Quiz;

import javax.swing.*;
import java.awt.*;

public class QuizPreviewFrame extends JFrame {

    public QuizPreviewFrame(JFrame parent, Quiz quiz) {
        super(quiz.getName() + " - Preview");
        setSize(700, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(8,8));

        JPanel info = new JPanel(new GridLayout(2,3,6,6));
        info.add(new JLabel("Questions:")); info.add(new JLabel(String.valueOf(quiz.getAmount())));
        info.add(new JLabel("Category:")); info.add(new JLabel(quiz.getCategory()));
        info.add(new JLabel("Difficulty:")); info.add(new JLabel(quiz.getDifficulty()));
        add(info, BorderLayout.NORTH);

        DefaultListModel<String> qModel = new DefaultListModel<>();
        for (Question q : quiz.getQuestions()) qModel.addElement(q.getQuestionText());

        JList<String> qList = new JList<>(qModel);
        qList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(new JScrollPane(qList), BorderLayout.CENTER);

        setVisible(true);
    }
}
