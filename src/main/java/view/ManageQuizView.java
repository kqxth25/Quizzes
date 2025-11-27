package view;

import entity.Quiz;
import interface_adapter.ViewManagerModel;
import use_case.quizimport.QuizRepository_import;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageQuizView extends JPanel {

    private final String viewName = "manage quiz";

    private final ViewManagerModel viewManagerModel;
    private final QuizRepository_import repository;

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> listUI = new JList<>(listModel);

    public ManageQuizView(ViewManagerModel viewManagerModel,
                          QuizRepository_import repository) {
        this.viewManagerModel = viewManagerModel;
        this.repository = repository;

        setLayout(new BorderLayout(8, 8));

        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Manage Quizzes");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        top.add(title, BorderLayout.WEST);

        JButton backHome = new JButton("Back to Home");
        backHome.addActionListener(e -> {
            viewManagerModel.navigate("home");
            viewManagerModel.firePropertyChange();
        });
        top.add(backHome, BorderLayout.EAST);
        top.add(backHome, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        listUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listUI), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton importBtn = new JButton("Import");
        JButton previewBtn = new JButton("Preview");
        JButton deleteBtn = new JButton("Delete");

        controls.add(importBtn);
        controls.add(previewBtn);
        controls.add(deleteBtn);
        add(controls, BorderLayout.SOUTH);

        importBtn.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            JFrame parent = window instanceof JFrame ? (JFrame) window : null;

            new ImportQuizFrame(parent, repository, this::refreshQuizList);
        });

        previewBtn.addActionListener(e -> previewSelected());

        deleteBtn.addActionListener(e -> deleteSelected());

        refreshQuizList();
    }

    public String getViewName() {
        return viewName;
    }

    public void refreshQuizList() {
        listModel.clear();
        List<Quiz> quizzes = repository.getAll();
        for (Quiz q : quizzes) {
            listModel.addElement(q.getName());
        }
    }

    private void previewSelected() {
        String selected = listUI.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a quiz first.");
            return;
        }
        Quiz q = repository.getByName(selected);
        if (q == null) {
            JOptionPane.showMessageDialog(this, "Quiz not found.");
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        JFrame parent = window instanceof JFrame ? (JFrame) window : null;
        new QuizPreviewFrame(parent, q);
    }

    private void deleteSelected() {
        String selected = listUI.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a quiz first.");
            return;
        }

        int ok = JOptionPane.showConfirmDialog(
                this,
                "Delete \"" + selected + "\"?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );
        if (ok == JOptionPane.YES_OPTION) {
            repository.delete(selected);
            refreshQuizList();
        }
    }
}
