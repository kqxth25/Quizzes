package view;

import javax.swing.*;
import java.awt.*;

public class DeleteConfirmDialog extends JDialog {
    public DeleteConfirmDialog(JFrame parent, String quizName, DefaultListModel<String> model) {
        super(parent, "Confirm Delete", true);
        setSize(300,150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10,10));

        JLabel confirmLabel = new JLabel("Are you sure you want to delete " + quizName + "?", SwingConstants.CENTER);
        add(confirmLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton yesBtn = new JButton("Yes");
        JButton noBtn = new JButton("No");
        buttonPanel.add(yesBtn);
        buttonPanel.add(noBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        yesBtn.addActionListener(e -> {
            model.removeElement(quizName);
            dispose();
        });
        noBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
