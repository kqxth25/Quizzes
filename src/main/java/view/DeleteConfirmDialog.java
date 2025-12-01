package view;

import use_case.quizimport.QuizRepository_import;

import javax.swing.*;
import java.awt.*;

public class DeleteConfirmDialog extends JDialog {

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color SECONDARY_HOVER = SECONDARY.brighter();

    public DeleteConfirmDialog(JFrame parent, String quizName, QuizRepository_import repository, Runnable onDelete) {
        super(parent, "Confirm Delete", true);

        setSize(380, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(BG_APP);

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 30));
                g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 18, 18);

                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout(20, 20));

        JPanel inner = new JPanel(new BorderLayout(20, 20));
        inner.setBackground(CARD_BG);
        inner.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        card.add(inner);

        JLabel title = new JLabel("Delete Quiz", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_MAIN);

        JLabel confirmLabel = new JLabel(
                "Are you sure you want to delete \"" + quizName + "\"?",
                SwingConstants.CENTER
        );
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmLabel.setForeground(TEXT_MUTED);

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(confirmLabel);

        inner.add(textPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);

        JButton yesBtn = createPrimaryButton("Delete");
        JButton noBtn = createSecondaryButton("Cancel");

        buttonPanel.add(noBtn);
        buttonPanel.add(yesBtn);

        inner.add(buttonPanel, BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(card, gbc);

        yesBtn.addActionListener(e -> {
            repository.delete(quizName);
            if (onDelete != null) {
                onDelete.run();
            }
            dispose();
        });

        noBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    private JButton baseButton(String text, Color bg, Color hover, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill = getModel().isRollover() ? hover : bg;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) { }
        };

        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        return btn;
    }

    private JButton createPrimaryButton(String text) {
        return baseButton(text, PRIMARY, PRIMARY_HOVER, Color.WHITE);
    }

    private JButton createSecondaryButton(String text) {
        return baseButton(text, SECONDARY, SECONDARY_HOVER, Color.WHITE);
    }
}