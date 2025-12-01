package view;

import interface_adapter.share_result.ShareResultState;
import interface_adapter.share_result.ShareResultViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ShareResultView extends JPanel implements PropertyChangeListener {

    private final ShareResultViewModel viewModel;

    private final JLabel usernameLabel = new JLabel();
    private final JLabel quizLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();
    private final JLabel hintLabel = new JLabel(
            "please screenshot this page and send to your instructor");

    // ---- Colors (Copied style from LoginView) ----
    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);

    public ShareResultView(ShareResultViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setBackground(BG_APP);
        setLayout(new GridBagLayout()); // center card like LoginView

        // === Card container ===
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // subtle shadow
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 30));
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(32, 48, 32, 48));

        // === Title ===
        JLabel title = new JLabel("Share Result");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_MAIN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Your quiz performance summary");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === User info labels style ===
        Font infoFont = new Font("Segoe UI", Font.PLAIN, 17);
        usernameLabel.setFont(infoFont);
        quizLabel.setFont(infoFont);
        scoreLabel.setFont(infoFont);

        usernameLabel.setForeground(TEXT_MAIN);
        quizLabel.setForeground(TEXT_MAIN);
        scoreLabel.setForeground(TEXT_MAIN);

        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === Add components into card ===
        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(20));

        card.add(usernameLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(quizLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(scoreLabel);
        card.add(Box.createVerticalStrut(20));

        // === Bottom hint ===
        hintLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        hintLabel.setForeground(TEXT_MUTED);
        hintLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel hintPanel = new JPanel(new BorderLayout());
        hintPanel.setOpaque(false);
        hintPanel.add(hintLabel, BorderLayout.EAST);

        card.add(hintPanel);

        // center card like LoginView
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ShareResultState s = viewModel.getState();
        usernameLabel.setText("Username: " + s.getUsername());
        quizLabel.setText("Quiz Name: " + s.getQuizName());
        scoreLabel.setText("Final Score: " + (int) s.getScore() + " / 100");
    }
}
