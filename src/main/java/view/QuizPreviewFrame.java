package view;

import entity.Question;
import entity.Quiz;

import javax.swing.*;
import java.awt.*;

public class QuizPreviewFrame extends JFrame {

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color PRIMARY = new Color(59, 130, 246);

    public QuizPreviewFrame(JFrame parent, Quiz quiz) {
        super(quiz.getName() + " - Preview");

        setSize(760, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(BG_APP);

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // soft shadow
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 25));
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                g2.dispose();
            }
        };

        card.setLayout(new BorderLayout(20, 20));
        card.setOpaque(false);

        JPanel inner = new JPanel(new BorderLayout(20, 20));
        inner.setBackground(CARD_BG);
        inner.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        card.add(inner);

        JLabel title = new JLabel("Quiz Preview", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_MAIN);
        inner.add(title, BorderLayout.NORTH);

        JPanel info = new JPanel(new GridLayout(3, 2, 12, 12));
        info.setOpaque(false);

        info.add(buildInfoLabel("Questions:"));
        info.add(buildInfoValue(String.valueOf(quiz.getAmount())));

        info.add(buildInfoLabel("Category:"));
        info.add(buildInfoValue(quiz.getCategory()));

        info.add(buildInfoLabel("Difficulty:"));
        info.add(buildInfoValue(quiz.getDifficulty()));

        inner.add(info, BorderLayout.WEST);

        DefaultListModel<String> qModel = new DefaultListModel<>();
        for (Question q : quiz.getQuestions()) {
            qModel.addElement("•  " + q.getQuestionText());
        }

        JList<String> qList = new JList<>(qModel);
        qList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        qList.setForeground(TEXT_MAIN);
        qList.setFixedCellHeight(28);

        JScrollPane scroll = new JScrollPane(qList);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235), 1));
        inner.add(scroll, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        add(card, gbc);

        setVisible(true);
    }

    private JLabel buildInfoLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 15));
        l.setForeground(TEXT_MUTED);
        return l;
    }

    private JLabel buildInfoValue(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        l.setForeground(TEXT_MAIN);
        return l;
    }
}