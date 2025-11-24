package view;

import javax.swing.*;
import java.awt.*;

public class ResultView extends JFrame {

    public ResultView(int score) {
        setTitle("Quiz Result");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel scoreLabel = new JLabel("You got " + score + "% of the quiz!", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(220, 220, 220));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(scoreLabel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBackground(new Color(245, 245, 245));

        JButton detailBtn = new JButton("See Detail");
        JButton shareBtn = new JButton("Share Result");

        styleButton(detailBtn, new Color(0, 102, 204));
        styleButton(shareBtn, new Color(0, 102, 204));

        btnPanel.add(detailBtn);
        btnPanel.add(shareBtn);
        add(btnPanel, BorderLayout.SOUTH);

        detailBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Details not implemented."));

        shareBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Shared! Score = " + score + "%"));

        setVisible(true);
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBackground(bgColor);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker().darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResultView(87));
    }
}


