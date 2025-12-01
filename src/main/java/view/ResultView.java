package view;

import interface_adapter.result.ResultController;
import interface_adapter.result.ResultViewModel;
import interface_adapter.result.ResultState;

import interface_adapter.ViewManagerModel;
import interface_adapter.result_detail.DetailController;
import interface_adapter.share_result.ShareResultController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ResultView extends JPanel implements PropertyChangeListener {

    private final ResultViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private DetailController detailController;
    private interface_adapter.share_result.ShareResultController shareController;

    private final JLabel scoreLabel = new JLabel("", SwingConstants.CENTER);
    private final ShareResultController shareResultcontroller;

    // New buttons
    private JButton shareBtn;
    private JButton detailBtn;
    private JButton backBtn;

    // Colors copied from SignupView
    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color SECONDARY_HOVER = new Color(148, 163, 184).brighter();

    public ResultView(ResultViewModel viewModel, ViewManagerModel viewManagerModel, ShareResultController shareResultcontroller) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.shareResultcontroller = shareResultcontroller;

        this.viewModel.addPropertyChangeListener(this);

        setBackground(BG_APP);
        setLayout(new GridBagLayout());

        // ===== Card Panel =====
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(32, 40, 32, 40));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        scoreLabel.setForeground(TEXT_MAIN);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setOpaque(false);

        card.add(scoreLabel);
        card.add(Box.createVerticalStrut(20));

        // ===== Buttons Panel =====
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttons.setOpaque(false);

        shareBtn = createPrimaryButton("Share Result");
        detailBtn = createSecondaryButton("View Detail");
        backBtn = createSecondaryButton("Back to Home");

        shareBtn.addActionListener(e -> {
            shareResultcontroller.loadShareData();
            viewManagerModel.navigate("share result");
        });

        backBtn.addActionListener(e -> viewManagerModel.navigate("home"));

        buttons.add(shareBtn);
        buttons.add(detailBtn);
        buttons.add(backBtn);

        card.add(buttons);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);

        updateFromState(viewModel.getState());
    }

    public void setShareController(interface_adapter.share_result.ShareResultController controller) {
        this.shareController = controller;
    }

    public void setDetailController(DetailController controller) {
        this.detailController = controller;

        // remove previous listeners (optional), then add new one
        // make sure detailBtn was created in constructor before this setter is called
        if (detailBtn != null) {
            // clear existing listeners to avoid duplicate actions
            for (java.awt.event.ActionListener al : detailBtn.getActionListeners()) {
                detailBtn.removeActionListener(al);
            }

            detailBtn.addActionListener(e -> {
                if (this.detailController != null) {
                    // ask controller to prepare/present detail data
                    this.detailController.showDetail();
                }
                // navigate to detail view (AppBuilder has already added detailView to cardPanel)
                this.viewManagerModel.navigate("detailView");
            });
        } else {
            throw new UnsupportedOperationException("detailBtn must be initialized before setDetailController is called");
        }
    }
    private void updateFromState(ResultState state) {
        if (state == null) {
            scoreLabel.setText("No result");
        } else {
            scoreLabel.setText("You got " + state.getScore() + "%");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ResultState s = (ResultState) evt.getNewValue();
        if (s != null) {
            updateFromState(s);
            revalidate();
            repaint();
        }
    }

    // ===== UI Components borrowed from SignupView =====

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
        b.setPreferredSize(new Dimension(150, 40));
        b.setMaximumSize(new Dimension(180, 40));
        return b;
    }

    private static JButton createPrimaryButton(String text) {
        return baseButton(text, PRIMARY, PRIMARY_HOVER, Color.WHITE);
    }

    private static JButton createSecondaryButton(String text) {
        return baseButton(text, SECONDARY, SECONDARY_HOVER, Color.WHITE);
    }
}
