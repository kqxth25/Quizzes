package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JPanel {

    private final String viewName = "home";
    private final ViewManagerModel viewManagerModel;

    private final JButton userButton;
    private final JButton creatorButton;

    public HomeView(final ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;

        final JLabel title = new JLabel("Homepage");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        userButton = new JButton("I am user");
        creatorButton = new JButton("I am creator");

        final JPanel buttons = new JPanel();
        buttons.add(userButton);
        buttons.add(creatorButton);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerModel.navigate("sign up");
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(12));
        this.add(title);
        this.add(Box.createVerticalStrut(12));
        this.add(buttons);
    }

    public String getViewName() {
        return viewName;
    }
}
