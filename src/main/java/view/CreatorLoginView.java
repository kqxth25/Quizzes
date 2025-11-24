package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.creator_login.CreatorLoginController;

import javax.swing.*;
import java.awt.*;

public class CreatorLoginView extends JPanel {

    private CreatorLoginController controller;
    private final ViewManagerModel viewManagerModel;
    private final String viewName = "creator login";

    private final JTextField passwordField = new JPasswordField(15);

    public CreatorLoginView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        buildUI();
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(CreatorLoginController controller) {
        this.controller = controller;
    }

    private void buildUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Creator Login");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridy = 2;
        add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        gbc.gridy = 3;
        add(loginBtn, gbc);

        JButton backBtn = new JButton("Go Back");
        gbc.gridy = 4;
        add(backBtn, gbc);

        loginBtn.addActionListener(e -> {
            if (controller != null) {
                controller.execute(passwordField.getText());
            } else {
                System.out.println("CreatorLoginController not set!");
            }
        });

        loginBtn.addActionListener(e -> {
            if (controller != null) {
                controller.execute(passwordField.getText());
            } else {
                System.out.println("CreatorLoginController not set!");
            }
        });

        backBtn.addActionListener(e -> {
            viewManagerModel.navigate("home");
        });

    }
}