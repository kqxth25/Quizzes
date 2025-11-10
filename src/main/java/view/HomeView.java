package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeView extends JPanel {

    private final String viewName = "home";
    private final ViewManagerModel viewManagerModel;

    private final JButton userButton;
    private final JButton creatorButton;

    public HomeView(final ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;

        //background color
        setBackground(new Color(192, 192, 192));
        setLayout(new GridBagLayout());

        final JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));

        final JLabel title = new JLabel("Welcome!", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(47, 67, 112));


        userButton = createButton(
                "I am a user",
                new Color(60, 117, 213),
                Color.WHITE,
                new Color(112, 188, 41));

        creatorButton = createButton(
                "I am a creator",
                new Color(136, 86, 234),
                Color.WHITE,
                new Color(236, 197, 76));


        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 0));
        buttons.setOpaque(false);
        buttons.add(userButton);
        buttons.add(creatorButton);


        column.add(title);
        column.add(Box.createVerticalStrut(28));
        column.add(buttons);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(column, gbc);

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


    private static JButton createButton(String text, Color bg, Color fg, Color hoverBg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getModel().isRollover() ? hoverBg : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                super.paintComponent(g2);
                g2.dispose();
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

        b.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        b.setForeground(fg);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));

        b.setPreferredSize(new Dimension(160, 50));
        b.setMinimumSize(new Dimension(160, 50));
        b.setMaximumSize(new Dimension(160, 50));

        return b;
    }
}
