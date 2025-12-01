package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;

public class HistoryView extends JPanel {

    private final String viewName = "history-view";

    public HistoryView(ViewManagerModel nav) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Just a placeholder label for now
        JLabel label = new JLabel("History Page (blank)", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(label, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }
}
