package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.result_detail.DetailViewModel;
import interface_adapter.result_detail.DetailState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DetailView extends JPanel implements PropertyChangeListener {

    private final DetailViewModel viewModel;
    private final ViewManagerModel viewManager;

    private final JPanel listPanel;

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);

    private static final Color GREEN = new Color(34, 139, 34);
    private static final Color RED = new Color(191, 63, 63);
    private static final Color CARD_BG = Color.WHITE;

    public DetailView(DetailViewModel vm, ViewManagerModel viewManager) {
        this.viewModel = vm;
        this.viewManager = viewManager;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(BG_APP);

        JLabel title = new JLabel("Detail Result", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(24, 0, 12, 0));
        add(title, BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_APP);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getVerticalScrollBar().setBackground(BG_APP);
        scroll.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setOpaque(false);
        JButton back = styledButton("Back");
        back.addActionListener(e -> viewManager.navigate("resultView"));
        bottom.add(back);

        add(bottom, BorderLayout.SOUTH);
    }

    private JButton styledButton(String text) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = getModel().isRollover() ? new Color(59, 130, 246).brighter() : new Color(59, 130, 246);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {}
        };

        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setForeground(Color.WHITE);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        return b;
    }

    private void rebuildFromState(DetailState s) {
        listPanel.removeAll();

        if (s == null || s.questions == null || s.questions.length == 0) {
            JLabel empty = new JLabel("No detail available", SwingConstants.CENTER);
            empty.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            empty.setForeground(TEXT_MUTED);
            empty.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
            listPanel.add(empty);
            revalidate();
            repaint();
            return;
        }

        for (int i = 0; i < s.questions.length; i++) {
            JPanel card = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    g2.setColor(CARD_BG);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                }
            };

            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setOpaque(false);
            card.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

            JLabel qLabel = new JLabel((i + 1) + ". " + s.questions[i]);
            qLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
            qLabel.setForeground(TEXT_MAIN);
            card.add(qLabel);
            card.add(Box.createVerticalStrut(10));

            String[] opts = s.options[i];
            int correct = s.correctIndex[i];
            int saved = s.savedAnswers[i];

            for (int j = 0; j < opts.length; j++) {
                JLabel opt = new JLabel(buildOptionText(j, opts[j], correct, saved));
                opt.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                if (j == correct && j == saved)
                    opt.setForeground(GREEN);
                else if (j == correct)
                    opt.setForeground(GREEN);
                else if (j == saved)
                    opt.setForeground(RED);
                else
                    opt.setForeground(TEXT_MAIN);

                card.add(opt);
                card.add(Box.createVerticalStrut(4));
            }

            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(12));
        }

        revalidate();
        repaint();
    }

    private String buildOptionText(int index, String text, int correct, int saved) {
        String base = (index + 1) + ") " + text;

        if (index == correct && index == saved)
            return base + "   (Correct • Your Answer)";
        if (index == correct)
            return base + "   (Correct)";
        if (index == saved)
            return base + "   (Your Answer)";
        return base;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DetailState state = (DetailState) evt.getNewValue();
        rebuildFromState(state);
    }

    private static class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(200, 200, 200);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(0, 0));
            btn.setMinimumSize(new Dimension(0, 0));
            btn.setMaximumSize(new Dimension(0, 0));
            return btn;
        }
    }
}
