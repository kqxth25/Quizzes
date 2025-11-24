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

    private final JLabel progressLabel = new JLabel("Progress: 1/10");
    private final JLabel questionLabel = new JLabel("Question");
    private final JRadioButton[] options = new JRadioButton[4];
    private final ButtonGroup optionGroup = new ButtonGroup();
    private final JButton previousButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");

    public QuizView(QuizViewModel viewModel, Object viewManagerModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        progressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(progressLabel);
        add(Box.createVerticalStrut(10));

        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(questionLabel);
        add(Box.createVerticalStrut(20));

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton("Option " + (i + 1));
            optionGroup.add(options[i]);
            add(options[i]);
            add(Box.createVerticalStrut(5));
        }

        add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        add(buttonPanel);

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
            controller.submitAnswer(state.getCurrentQuestionIndex(), selected);
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
            JOptionPane.showMessageDialog(this,
                    "You are at the first question.",
                    "Navigation Warning",
                    JOptionPane.WARNING_MESSAGE);
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

        questionLabel.setText(state.getQuestionText());

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
        if (savedAnswer >= 0 && savedAnswer < opts.length) {
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
}