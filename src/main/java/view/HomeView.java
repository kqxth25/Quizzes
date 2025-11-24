package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JPanel {

    private final String viewName = "home";
    private final ViewManagerModel viewManagerModel;

    private final JButton userButton;
    private final JButton creatorButton;

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(129, 140, 248);
    private static final Color SECONDARY_HOVER = new Color(165, 180, 252);

    public HomeView(final ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;

        setBackground(BG_APP);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(32, 40, 32, 40));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Welcome!", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(TEXT_MAIN);

        JLabel subtitle = new JLabel("Choose your role to continue", SwingConstants.CENTER);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(100, 116, 139));

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(24));

        userButton = createPrimaryButton("I am a user");
        creatorButton = createSecondaryButton("I am a creator");

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        userButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        creatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttons.add(userButton);
        buttons.add(Box.createVerticalStrut(12));
        buttons.add(creatorButton);

        card.add(buttons);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerModel.navigate("sign up");
            }
        });

        creatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerModel.navigate("creator login");
            }
        });
    }

    public String getViewName() {
        return viewName;
    }

    private static JButton baseButton(String text, Color bg, Color hoverBg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill = getModel().isRollover() ? hoverBg : bg;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg.darker());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };

        b.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));

        b.setForeground(Color.WHITE);
        b.setPreferredSize(new Dimension(200, 42));
        b.setMinimumSize(new Dimension(200, 42));
        b.setMaximumSize(new Dimension(200, 42));

        return b;
    }

    private static JButton createPrimaryButton(String text) {
        return baseButton(text, PRIMARY, PRIMARY_HOVER);
    }

    private static JButton createSecondaryButton(String text) {
        return baseButton(text, SECONDARY, SECONDARY_HOVER);
    }
}
