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

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color SECONDARY_HOVER = new Color(148, 163, 184).brighter();

    private int hoverIndex = -1;

    public ManageQuizView(ViewManagerModel viewManagerModel,
                          QuizRepository_import repository) {
        this.viewManagerModel = viewManagerModel;
        this.repository = repository;

        setBackground(BG_APP);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        card.setLayout(new BorderLayout(16, 16));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel title = new JLabel("Manage Quizzes");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_MAIN);
        top.add(title, BorderLayout.WEST);

        JButton backHome = createSecondaryButton("Back to Home");
        backHome.addActionListener(e -> {
            viewManagerModel.navigate("home");
            viewManagerModel.firePropertyChange();
        });
        top.add(backHome, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);

        listUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listUI.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listUI.setCellRenderer(new QuizListCellRenderer());
        listUI.setFixedCellHeight(50);
        listUI.setBorder(BorderFactory.createEmptyBorder());

        JScrollPane scrollPane = new JScrollPane(listUI);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        card.add(scrollPane, BorderLayout.CENTER);

        setupListHover();

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        controls.setOpaque(false);
        JButton importBtn = createPrimaryButton("Import");
        JButton previewBtn = createSecondaryButton("Preview");
        JButton deleteBtn = createSecondaryButton("Delete");

        controls.add(importBtn);
        controls.add(previewBtn);
        controls.add(deleteBtn);

        card.add(controls, BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);

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
            Window w = SwingUtilities.getWindowAncestor(this);
            JFrame parent = w instanceof JFrame ? (JFrame) w : null;
            new NotSelectDialog(parent, "Select a quiz first.");
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
            Window w = SwingUtilities.getWindowAncestor(this);
            JFrame parent = w instanceof JFrame ? (JFrame) w : null;
            new NotSelectDialog(parent, "Select a quiz first.");
            return;
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        JFrame parent = window instanceof JFrame ? (JFrame) window : null;

        new DeleteConfirmDialog(
                parent,
                selected,
                repository,
                this::refreshQuizList
        );
    }

    private static JButton baseButton(String text, Color bg, Color hoverBg, Color fg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = getModel().isRollover() ? hoverBg : bg;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg.darker());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                g2.dispose();
            }
        };

        b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        b.setForeground(fg);
        b.setPreferredSize(new Dimension(140, 38));
        b.setMinimumSize(new Dimension(140, 38));
        b.setMaximumSize(new Dimension(150, 38));

        return b;
    }

    private static JButton createPrimaryButton(String text) {
        return baseButton(text, PRIMARY, PRIMARY_HOVER, Color.WHITE);
    }

    private static JButton createSecondaryButton(String text) {
        return baseButton(text, SECONDARY, SECONDARY_HOVER, Color.WHITE);
    }

    private void setupListHover() {
        listUI.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                int idx = listUI.locationToIndex(e.getPoint());
                if (idx != hoverIndex) {
                    hoverIndex = idx;
                    listUI.repaint();
                }
            }
        });
        listUI.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hoverIndex = -1;
                listUI.repaint();
            }
        });
    }

    private class QuizListCellRenderer extends JPanel implements ListCellRenderer<String> {
        private final JLabel label = new JLabel();

        public QuizListCellRenderer() {
            setLayout(new BorderLayout());
            setOpaque(true);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            add(label, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list,
                                                      String value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            label.setText(value);

            if (isSelected) {
                setBackground(PRIMARY);
                label.setForeground(Color.WHITE);
            } else if (index == hoverIndex) {
                setBackground(PRIMARY_HOVER.brighter());
                label.setForeground(TEXT_MAIN);
            } else {
                setBackground(Color.WHITE);
                label.setForeground(TEXT_MAIN);
            }

            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

            return this;
        }
    }
}
