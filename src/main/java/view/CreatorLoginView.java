package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.creator_login.CreatorLoginController;
import interface_adapter.creator_login.CreatorLoginViewModel;

import javax.swing.*;
import java.awt.*;

public class CreatorLoginView extends JPanel {

    private CreatorLoginController controller;
    private final ViewManagerModel viewManagerModel;
    private final CreatorLoginViewModel viewModel;
    private final String viewName = "creator login";

    private final JPasswordField passwordField = new JPasswordField(12);

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color SECONDARY_HOVER = new Color(148, 163, 184).brighter();

    private JButton loginBtn;
    private JButton backBtn;

    public CreatorLoginView(ViewManagerModel viewManagerModel,
                            CreatorLoginViewModel viewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewModel = viewModel;

        buildUI();
        setupViewModelListener();
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(CreatorLoginController controller) {
        this.controller = controller;
    }

    private void buildUI() {
        setBackground(BG_APP);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Creator Login");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_MAIN);

        JLabel subtitle = new JLabel("Enter the creator password to manage quizzes");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createVerticalStrut(4));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordPanel.add(passwordField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setOpaque(false);

        loginBtn = createPrimaryButton("Login");
        backBtn = createSecondaryButton("Back");

        buttons.add(loginBtn);
        buttons.add(backBtn);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(16));
        card.add(passwordPanel);
        card.add(Box.createVerticalStrut(20));
        card.add(buttons);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);

        loginBtn.addActionListener(e -> {
            if (controller != null) {
                controller.execute(new String(passwordField.getPassword()));
            } else {
                System.out.println("CreatorLoginController not set!");
            }
        });

        backBtn.addActionListener(e -> viewManagerModel.navigate("home"));
    }

    /**
     * Listen for success or failure from the presenter via the view model.
     */
    private void setupViewModelListener() {
        viewModel.addPropertyChangeListener(evt -> {
            // SUCCESS → navigate to manage screen
            if (viewModel.isLoginSuccess()) {
                viewManagerModel.navigate("manage");
                return;
            }

            // FAILURE → show the popup
            if (viewModel.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(
                        this,
                        viewModel.getErrorMessage(),
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    private static JButton baseButton(String text, Color bg, Color hoverBg, Color fg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill = getModel().isRollover() ? hoverBg : bg;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg.darker());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                g2.dispose();
            }
        };

        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        b.setForeground(fg);
        b.setPreferredSize(new Dimension(140, 38));
        b.setMinimumSize(new Dimension(140, 38));
        b.setMaximumSize(new Dimension(150, 38));
        return b;
    }

    private static JButton createPrimaryButton(String text) {
        return baseButton(text, PRIMARY, PRIMARY_HOVER, Color.WHITE);
    }

    private static JButton createSecondaryButton(String text) {
        return baseButton(text, SECONDARY, SECONDARY_HOVER, Color.WHITE);
    }
}
