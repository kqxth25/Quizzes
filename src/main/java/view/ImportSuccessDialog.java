package view;


import javax.swing.*;
import java.awt.*;

public class ImportSuccessDialog extends JDialog {

    public ImportSuccessDialog(JFrame parent, String message) {
        super(parent, "Success", true);

        setSize(320, 160);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Success");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(30, 41, 59));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg = new JLabel(message, SwingConstants.CENTER);
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msg.setForeground(new Color(55, 65, 81));
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(10));
        card.add(msg);
        card.add(Box.createVerticalStrut(20));

        JButton okBtn = new JButton("OK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(59, 130, 246));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                super.paintComponent(g);
            }
        };
        okBtn.setForeground(Color.WHITE);
        okBtn.setFocusPainted(false);
        okBtn.setContentAreaFilled(false);
        okBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        okBtn.addActionListener(e -> dispose());

        card.add(okBtn);

        add(card, BorderLayout.CENTER);

        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        setVisible(true);
    }
}