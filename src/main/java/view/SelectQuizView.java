package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizState;
import interface_adapter.quiz.QuizViewModel;
import interface_adapter.selectquiz.ListQuizzesController;
import interface_adapter.selectquiz.SelectQuizViewModel;
import interface_adapter.history.HistoryController;
import use_case.selectquiz.QuizItemDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

public class SelectQuizView extends JPanel implements ActionListener {

    private final String viewName = "select quiz";

    private final SelectQuizViewModel viewModel;
    private final ViewManagerModel nav;
    private final QuizViewModel quizViewModel;
    private final QuizController quizController;
    private final HistoryController historyController;

    private ListQuizzesController controller;

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color SECONDARY_HOVER = new Color(148, 163, 184).brighter();

    private final JPanel quizListPanel = new JPanel();
    private final JButton backButton;
    private final JButton historyButton;
    private final JLabel statusLabel = new JLabel("");

    public SelectQuizView(SelectQuizViewModel viewModel,
                          ViewManagerModel nav,
                          QuizViewModel quizViewModel,
                          QuizController quizController,
                          HistoryController historyController) {

        this.viewModel = viewModel;
        this.nav = nav;
        this.quizViewModel = quizViewModel;
        this.quizController = quizController;
        this.historyController = historyController;

        setBackground(BG_APP);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Ready for the Quiz?");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_MAIN);

        JLabel subtitle = new JLabel("Choose one below :P");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(18));

        quizListPanel.setLayout(new BoxLayout(quizListPanel, BoxLayout.Y_AXIS));
        quizListPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(quizListPanel);
        scrollPane.setPreferredSize(new Dimension(420, 260));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        card.add(scrollPane);
        card.add(Box.createVerticalStrut(12));

        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(TEXT_MUTED);
        card.add(statusLabel);
        card.add(Box.createVerticalStrut(16));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setOpaque(false);

        backButton = createSecondaryButton("Back to Home");
        historyButton = createSecondaryButton("View History");

        buttons.add(backButton);
        buttons.add(historyButton);

        card.add(buttons);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);

        backButton.addActionListener(this);
        historyButton.addActionListener(this);

        this.viewModel.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if ("state".equals(evt.getPropertyName())) {
                updateFromViewModel();
            }
        });

        this.nav.addPropertyChangeListener(evt -> {
            Object newVal = evt.getNewValue();
            if (newVal instanceof ViewManagerState) {
                ViewManagerState state = (ViewManagerState) newVal;
                String current = state.getActiveView();
                if (viewName.equals(current) && controller != null) {
                    controller.execute();
                }
            }
        });
    }

    public void setController(ListQuizzesController controller) {
        this.controller = controller;
        if (this.controller != null) {
            this.controller.execute();
        }
    }

    public void updateFromViewModel() {
        List<QuizItemDto> quizzes = viewModel.getQuizzes();
        quizListPanel.removeAll();

        if (quizzes == null || quizzes.isEmpty()) {
            JLabel empty = new JLabel("No quizzes available.");
            empty.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            empty.setForeground(TEXT_MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            quizListPanel.add(Box.createVerticalGlue());
            quizListPanel.add(empty);
            quizListPanel.add(Box.createVerticalGlue());
            statusLabel.setText("");
        } else {
            for (QuizItemDto q : quizzes) {
                final QuizItemDto item = q;

                JPanel row = new JPanel();
                row.setLayout(new BorderLayout());
                row.setOpaque(false);
                row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

                JLabel info = new JLabel(
                        item.getTitle() + "  ·  " +
                                item.getDifficulty() + "  ·  " +
                                item.getType()
                );
                info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                info.setForeground(TEXT_MAIN);

                JButton startBtn = createPrimaryButton("Start");
                startBtn.setPreferredSize(new Dimension(90, 32));
                startBtn.setMinimumSize(new Dimension(90, 32));
                startBtn.setMaximumSize(new Dimension(90, 32));
                startBtn.addActionListener(e -> onQuizSelected(item));

                row.add(info, BorderLayout.WEST);
                row.add(startBtn, BorderLayout.EAST);

                quizListPanel.add(row);
                quizListPanel.add(Box.createVerticalStrut(6));
            }
            statusLabel.setText("Select a quiz to start.");
        }

        quizListPanel.revalidate();
        quizListPanel.repaint();
    }

    private void onQuizSelected(QuizItemDto quiz) {
        String quizName = quiz.getTitle();

        quizController.loadQuiz(quizName);

        QuizState initialState = new QuizState(10);
        quizViewModel.setState(initialState);

        quizController.next(-1, -1);
        nav.navigate("quiz");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == backButton) {
            nav.navigate("home");
        } else if (src == historyButton) {
            historyController.onHistorySelected();
        }
    }

    public String getViewName() {
        return viewName;
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
        b.setMinimumSize(new Dimension(120, 34));
        b.setMaximumSize(new Dimension(160, 40));

        return b;
    }

    private static JButton createPrimaryButton(String text) {
        return baseButton(text, PRIMARY, PRIMARY_HOVER, Color.WHITE);
    }

    private static JButton createSecondaryButton(String text) {
        return baseButton(text, SECONDARY, SECONDARY_HOVER, Color.WHITE);
    }
}
