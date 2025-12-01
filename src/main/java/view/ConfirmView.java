package view;

import interface_adapter.confirm_submit.ConfirmController;
import interface_adapter.confirm_submit.ConfirmState;
import interface_adapter.confirm_submit.ConfirmViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ConfirmView extends JPanel implements PropertyChangeListener {

    private final ConfirmViewModel viewModel;
    private ConfirmController controller;

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color DANGER = new Color(220, 38, 38);

    private final JLabel messageLabel = new JLabel();
    private final JButton actionButton = createPrimaryButton("Confirm Submit");
    private final JButton cancelButton = createSecondaryButton("Cancel");

    public ConfirmView(ConfirmViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setBackground(BG_APP);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setForeground(TEXT_MAIN);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(actionButton);

        card.add(messageLabel);
        card.add(Box.createVerticalStrut(18));
        card.add(buttonPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);

        actionButton.addActionListener(this::onAction);
        cancelButton.addActionListener(this::onCancel);
    }

    public void setController(ConfirmController controller) {
        this.controller = controller;
    }

    private void onAction(ActionEvent e) {
        if (controller == null) return;
        // Always call forceSubmit (interactor will decide). You could also pass whether allCompleted via viewmodel,
        // but simpler: for "Confirm Submit" we might still want to call same interactor in this simple design.
        controller.forceSubmit();
    }

    private void onCancel(ActionEvent e) {
        // Close the parent window (dialog). We search up Swing hierarchy and dispose ancestor JDialog.
        Window w = SwingUtilities.getWindowAncestor(this);
        if (w != null) {
            w.dispose();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ConfirmState state = (ConfirmState) evt.getNewValue();
        if (state == null) return;

        messageLabel.setText("<html><body style='width:400px'>" + state.getMessage() + "</body></html>");

        if (state.isAllCompleted()) {
            messageLabel.setForeground(TEXT_MAIN);
            actionButton.setText(state.getActionButtonText());
        } else {
            messageLabel.setForeground(DANGER);
            actionButton.setText(state.getActionButtonText());
        }

        revalidate();
        repaint();
    }

    private static JButton baseButton(String text, Color bg, Color hoverBg, Color fg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill = getModel().isRollover() ? hoverBg : bg;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg.darker());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };

        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        b.setForeground(fg);
        b.setPreferredSize(new Dimension(140, 36));
        return b;
    }

    private static JButton createPrimaryButton(String text) {
        return baseButton(text, PRIMARY, PRIMARY_HOVER, Color.WHITE);
    }

    private static JButton createSecondaryButton(String text) {
        Color secondary = new Color(148, 163, 184);
        return baseButton(text, secondary, secondary.brighter(), Color.WHITE);
    }
}
