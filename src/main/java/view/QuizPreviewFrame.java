package view;

import javax.swing.*;
import java.awt.*;

public class QuizPreviewFrame extends JFrame {

    public QuizPreviewFrame(String quizName) {
        setTitle(quizName + " Preview");
        setSize(500,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        JPanel infoPanel = new JPanel(new GridLayout(4,2,5,5));
        infoPanel.add(new JLabel("Questions:")); infoPanel.add(new JLabel("10"));
        infoPanel.add(new JLabel("Category:")); infoPanel.add(new JLabel("Entertainment: Film"));
        infoPanel.add(new JLabel("Difficulty:")); infoPanel.add(new JLabel("Easy"));
        infoPanel.add(new JLabel("Type:")); infoPanel.add(new JLabel("Multiple Choice"));
        add(infoPanel, BorderLayout.NORTH);

        DefaultListModel<String> questionModel = new DefaultListModel<>();
        // Questions will be set by controller
        questionModel.addElement("Sample question 1");
        questionModel.addElement("Sample question 2");

        JList<String> questionList = new JList<>(questionModel);
        questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionList.setVisibleRowCount(8);
        questionList.setFont(new Font("SansSerif", Font.PLAIN, 14));

        add(new JScrollPane(questionList), BorderLayout.CENTER);
        setVisible(true);
    }
}