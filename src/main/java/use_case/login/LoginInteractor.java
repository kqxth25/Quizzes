package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizState;
import interface_adapter.quiz.QuizViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class QuizView extends JPanel implements PropertyChangeListener {

    private final String viewName = "quiz";
    private final QuizViewModel quizViewModel;
    private QuizController quizController;

    private final JLabel questionNumberLabel = new JLabel();
    private final JLabel questionLabel = new JLabel();
    private final JRadioButton[] options = new JRadioButton[4];
    private final ButtonGroup group = new ButtonGroup();
    private final JButton submitButton = new JButton("Submit");

    public QuizView(QuizViewModel quizViewModel, ViewManagerModel viewManagerModel) {
        this.quizViewModel = quizViewModel;
        this.quizViewModel.addPropertyChangeListener(this);

        setLayout(new GridBagLayout()); // 全局居中
        setBackground(new Color(245, 246, 250));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210,210,210), 1),
                BorderFactory.createEmptyBorder(20, 25, 25, 25)
        ));
        card.setPreferredSize(new Dimension(500, 400));

        // ------- TOP: question number -------
        questionNumberLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        questionNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionNumberLabel.setForeground(new Color(90,90,90));
        card.add(questionNumberLabel);

        // ------- Question text -------
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        card.add(questionLabel);

        // ------- Options -------
        for (int i = 0; i < 4; i++) {
            options[i] = makeOption();
            group.add(options[i]);
            card.add(options[i]);
        }

        // ------- Submit button -------
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        submitButton.setBackground(new Color(52, 152, 219));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.add(Box.createVerticalStrut(20));
        card.add(submitButton);

        add(card); // add to center of GridBagLayout

        submitButton.addActionListener(e -> {
            int selected = -1;
            for (int i = 0; i < 4; i++) {
                if (options[i].isSelected()) {
                    selected = i;
                    break;
                }
            }
            QuizState state = quizViewModel.getState();
            quizController.submitAnswer(state.getCurrentQuestionIndex(), selected);
        });
    }

    private JRadioButton makeOption() {
        JRadioButton btn = new JRadioButton();
        btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(50, 50, 50));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // hover 效果
        btn.addChangeListener(e -> {
            if (btn.isRolloverEnabled() && btn.getModel().isRollover()) {
                btn.setBackground(new Color(245, 245, 245));
            } else if (!btn.isSelected()) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        QuizState state = quizViewModel.getState();
        if (state == null) return;

        questionNumberLabel.setText(
                "Question " + (state.getCurrentQuestionIndex() + 1) +
                        " / " + state.getTotalQuestions()
        );
        questionLabel.setText("<html><body style='text-align:center'>" + state.getQuestionText() + "</body></html>");

        String[] ops = state.getOptions();
        group.clearSelection();

        for (int i = 0; i < 4; i++) {
            if (ops != null && i < ops.length) {
                options[i].setText(ops[i]);
                options[i].setVisible(true);
            } else {
                options[i].setVisible(false);
            }
        }

        int[] selected = state.getSelectedAnswers();
        if (selected != null && selected[state.getCurrentQuestionIndex()] != -1) {
            options[selected[state.getCurrentQuestionIndex()]].setSelected(true);
        }
    }

    public void setController(QuizController quizController) {
        this.quizController = quizController;
    }

    public String getViewName() {
        return this.viewName;
    }
}
