package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizState;
import interface_adapter.quiz.QuizViewModel;
import interface_adapter.selectquiz.ListQuizzesController;
import interface_adapter.selectquiz.SelectQuizViewModel;
import use_case.selectquiz.QuizItemDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;


public class SelectQuizView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "select quiz";

    private final SelectQuizViewModel viewModel;
    private final ViewManagerModel nav;
    private final QuizViewModel quizViewModel;
    private final QuizController quizController;

    private ListQuizzesController controller;

    private final JPanel quizListPanel = new JPanel();
    private final JButton backButton = new JButton("Back to Home");
    private final JButton historyButton = new JButton("View History");
    private final JLabel statusLabel = new JLabel("");

    public SelectQuizView(SelectQuizViewModel viewModel,
                          ViewManagerModel nav,
                          QuizViewModel quizViewModel,
                          QuizController quizController) {
        this.viewModel = viewModel;
        this.nav = nav;
        this.quizViewModel = quizViewModel;
        this.quizController = quizController;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));

        quizListPanel.setLayout(new BoxLayout(quizListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(quizListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(backButton);
        bottomPanel.add(historyButton);

        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.add(bottomPanel, BorderLayout.CENTER);
        southContainer.add(statusLabel, BorderLayout.SOUTH);
        add(southContainer, BorderLayout.SOUTH);

        backButton.addActionListener(this);
        historyButton.addActionListener(this);

        updateFromViewModel();
    }

    public void setController(ListQuizzesController controller) {
        this.controller = controller;
        if (this.controller != null) {
            this.controller.execute();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            updateFromViewModel();
        }
    }

    public void updateFromViewModel() {
        List<QuizItemDto> quizzes = viewModel.getQuizzes();
        quizListPanel.removeAll();

        if (quizzes == null || quizzes.isEmpty()) {
            JLabel empty = new JLabel("No quizzes available.");
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            quizListPanel.add(Box.createVerticalStrut(20));
            quizListPanel.add(empty);
            statusLabel.setText("");
        } else {
            for (QuizItemDto q : quizzes) {
                final QuizItemDto item = q;

                JButton quizButton = new JButton(item.getTitle() + " (" + item.getDifficulty() + ")");
                quizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                quizButton.addActionListener(e -> onQuizSelected(item));

                quizListPanel.add(Box.createVerticalStrut(8));
                quizListPanel.add(quizButton);
            }
            statusLabel.setText("Select a quiz to start.");
        }

        quizListPanel.revalidate();
        quizListPanel.repaint();
    }


    private void onQuizSelected(QuizItemDto quiz) {
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
            nav.navigate("history");
        }
    }

    public String getViewName() {
        return this.viewName;
    }
}
