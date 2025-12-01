package view;

import interface_adapter.result.ResultViewModel;
import interface_adapter.result.ResultState;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ResultView extends JPanel implements PropertyChangeListener {

    private final ResultViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    private final JLabel scoreLabel = new JLabel("", SwingConstants.CENTER);

    public ResultView(ResultViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(16, 16));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(255, 255, 255));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scoreLabel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        bottom.setOpaque(false);

        JButton backBtn = new JButton("Back to Home");
        backBtn.addActionListener(e -> viewManagerModel.navigate("home"));
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        // show initial state if available
        updateFromState(viewModel.getState());
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
}
