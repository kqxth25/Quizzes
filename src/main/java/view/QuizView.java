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

    private final JLabel questionLabel = new JLabel("Question");
    private final JRadioButton[] options = new JRadioButton[4];
    private final ButtonGroup optionGroup = new ButtonGroup();
    private final JButton nextButton = new JButton("Next");

    public QuizView(QuizViewModel viewModel, Object viewManagerModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(questionLabel);
        add(Box.createVerticalStrut(20));


        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton("Option " + (i + 1));
            optionGroup.add(options[i]);
            add(options[i]);
        }

        add(Box.createVerticalStrut(20));
        add(nextButton);

        nextButton.addActionListener(this::onNext);
    }

    /* 控制器要从 Main 设置进来 */
    public void setController(QuizController controller) {
        this.controller = controller;
    }



    private void onNext(ActionEvent e) {
        if (controller == null) return;

        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i;
                break;
            }
        }

        QuizState state = viewModel.getState();
        controller.submitAnswer(state.getCurrentQuestionIndex(), selected);


        optionGroup.clearSelection();
    }
   @Override
    public void propertyChange(PropertyChangeEvent evt) {
        QuizState state = (QuizState) evt.getNewValue();

        // 设置题目
        questionLabel.setText(state.getQuestionText());

        // 设置选项
        String[] opts = state.getOptions();
        if (opts != null && opts.length >= 4) {
            for (int i = 0; i < 4; i++) {
                options[i].setText(opts[i]);
            }
        }

        revalidate();
        repaint();
    }
}
