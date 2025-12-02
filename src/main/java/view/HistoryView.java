package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;


public class HistoryView extends JPanel {

    private final String viewName = "history-view";
    private final ViewManagerModel viewManagerModel;

    public HistoryView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel label = new JLabel("History Page (blank)", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(label, BorderLayout.CENTER);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Go to Select Quiz");

        backButton.addActionListener(e -> {
            viewManagerModel.navigate("select quiz");
        });

        topBar.add(backButton);
        add(topBar, BorderLayout.NORTH);
    }

    public String getViewName() {
        return viewName;
    }
}
