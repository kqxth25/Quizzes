package view;

import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizState;
import interface_adapter.quiz.QuizViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class QuizView extends JPanel implements PropertyChangeListener {

    private final QuizViewModel viewModel;
    private QuizController controller;

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color SECONDARY_HOVER = new Color(148, 163, 184).brighter();

    private final JLabel progressLabel = new JLabel("Progress: 1/10");
    private final JLabel questionLabel = new JLabel("Question");
    private final JRadioButton[] options = new JRadioButton[4];
    private final ButtonGroup optionGroup = new ButtonGroup();

    private final JButton previousButton = createSecondaryButton("Previous");
    private final JButton nextButton = createPrimaryButton("Next");

    public QuizView(QuizViewModel viewModel, Object viewManagerModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setBackground(BG_APP);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        progressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        progressLabel.setForeground(TEXT_MUTED);

        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        questionLabel.setForeground(TEXT_MAIN);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setOpaque(false);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton("Option " + (i + 1));
            options[i].setOpaque(false);
            options[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            options[i].setForeground(TEXT_MAIN);
            optionGroup.add(options[i]);

            optionsPanel.add(options[i]);
            optionsPanel.add(Box.createVerticalStrut(4));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        card.add(progressLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(questionLabel);
        card.add(Box.createVerticalStrut(14));
        card.add(optionsPanel);
        card.add(Box.createVerticalStrut(18));
        card.add(buttonPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);

        nextButton.addActionListener(this::onNext);
        previousButton.addActionListener(this::onPrevious);
        previousButton.setEnabled(false);
    }

    public void setController(QuizController controller) {
        this.controller = controller;
    }

    private void onNext(ActionEvent e) {
        if (controller == null) return;

        QuizState state = viewModel.getState();
        int selected = getSelectedOption();

        if ("Submit".equals(nextButton.getText())) {
            controller.prepareConfirmation(state.getCurrentQuestionIndex(), selected);
        } else {
            controller.next(state.getCurrentQuestionIndex(), selected);
        }
    }

    private int getSelectedOption() {
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    private void onPrevious(ActionEvent e) {
        if (controller == null) return;

        QuizState state = viewModel.getState();

        if (!state.hasPrevious()) {
            JOptionPane.showMessageDialog(
                    this,
                    "You are at the first question.",
                    "Navigation Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int selected = getSelectedOption();
        controller.previous(state.getCurrentQuestionIndex(), selected);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        QuizState state = (QuizState) evt.getNewValue();

        int current = state.getCurrentQuestionIndex() + 1;
        int total = state.getTotalQuestions();
        progressLabel.setText("Progress: " + current + "/" + total);

        String qText = state.getQuestionText();
        if (qText != null) {
            questionLabel.setText("<html><body style='width:600px'>" + qText + "</body></html>");
        } else {
            questionLabel.setText("");
        }

        optionGroup.clearSelection();

        String[] opts = state.getOptions();
        if (opts != null) {
            for (int i = 0; i < options.length; i++) {
                if (i < opts.length) {
                    options[i].setText(opts[i]);
                    options[i].setVisible(true);
                } else {
                    options[i].setVisible(false);
                }
            }
        }

        int savedAnswer = state.getAnswer(state.getCurrentQuestionIndex());
        if (opts != null && savedAnswer >= 0 && savedAnswer < opts.length) {
            options[savedAnswer].setSelected(true);
        }

        previousButton.setEnabled(state.hasPrevious());
        if (state.hasNext()) {
            nextButton.setText("Next");
        } else {
            nextButton.setText("Submit");
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