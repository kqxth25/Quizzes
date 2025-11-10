package view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class QuizView extends JFrame {

    private int current = 0;
    private final int totalQuestions = 10;

    private java.util.List<String> questions;
    private java.util.List<String[]> options;
    private Map<Integer, Integer> answers;

    private JLabel questionLabel;
    private JLabel warningLabel;

    private JRadioButton[] choiceBtns;
    private ButtonGroup choiceGroup;

    private JButton prevBtn;
    private JButton actionBtn;

    public QuizView() {
        setTitle("Quiz App");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20,20));
        getContentPane().setBackground(new Color(245, 245, 245));

        questions = new ArrayList<>();
        for (int i = 1; i <= totalQuestions; i++)
            questions.add("Question " + i + ": What is the answer?");

        options = new ArrayList<>();
        for (int i = 1; i <= totalQuestions; i++)
            options.add(new String[]{
                    "Option A", "Option B", "Option C", "Option D"
            });

        answers = new HashMap<>();

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        questionLabel.setOpaque(true);
        questionLabel.setBackground(new Color(220, 220, 220));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionPanel = new JPanel(new GridLayout(4,1,10,10));
        optionPanel.setBackground(new Color(245, 245, 245));
        optionPanel.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));
        choiceBtns = new JRadioButton[4];
        choiceGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            choiceBtns[i] = new JRadioButton();
            choiceBtns[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            choiceBtns[i].setBackground(Color.WHITE);
            choiceBtns[i].setFocusPainted(false);
            choiceBtns[i].setBorder(BorderFactory.createLineBorder(new Color(200,200,200),1,true));
            choiceBtns[i].setOpaque(true);
            choiceBtns[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    ((JRadioButton) evt.getSource()).setBackground(new Color(220, 240, 255));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    ((JRadioButton) evt.getSource()).setBackground(Color.WHITE);
                }
            });
            final int idx = i;
            choiceBtns[i].addActionListener(e -> answers.put(current, idx));
            choiceGroup.add(choiceBtns[i]);
            optionPanel.add(choiceBtns[i]);
        }
        add(optionPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));
        warningLabel = new JLabel("", SwingConstants.CENTER);
        warningLabel.setForeground(Color.RED);
        warningLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bottomPanel.add(warningLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new BorderLayout(20,0));
        btnPanel.setBackground(new Color(245,245,245));
        prevBtn = new JButton("◀ Previous");
        actionBtn = new JButton("Next ▶");

        styleButton(prevBtn, new Color(0,102,204));
        styleButton(actionBtn, new Color(0,102,204));

        btnPanel.add(prevBtn, BorderLayout.WEST);
        btnPanel.add(actionBtn, BorderLayout.EAST);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        prevBtn.addActionListener(e -> goPrevious());
        actionBtn.addActionListener(e -> handleAction());

        updateView();
        setVisible(true);
        toFront();
        requestFocus();
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8,15,8,15));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true); // 关键
        btn.setBackground(bgColor);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker().darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
    }


    private void updateView() {
        questionLabel.setText(questions.get(current));

        String[] opts = options.get(current);
        for (int i = 0; i < 4; i++)
            choiceBtns[i].setText(opts[i]);

        choiceGroup.clearSelection();
        if (answers.containsKey(current))
            choiceBtns[answers.get(current)].setSelected(true);

        warningLabel.setText("");
        prevBtn.setEnabled(current > 0);

        if (current == 0) {
            prevBtn.setVisible(false);
        } else {
            prevBtn.setVisible(true);
        }

        if (current == totalQuestions - 1) {
            actionBtn.setText("Submit");
            actionBtn.setBackground(new Color(0,102,204));
        } else {
            actionBtn.setText("Next ▶");
            actionBtn.setBackground(new Color(0,102,204));
        }
    }

    private void handleAction() {
        if (current == totalQuestions - 1) {
            if (actionBtn.getText().equals("Still Submit")) {
                goResult();
                return;
            }
            trySubmit();
        } else {
            goNext();
        }
    }

    private void goNext() {
        if (current < totalQuestions - 1) {
            current++;
            updateView();
        }
    }

    private void goPrevious() {
        if (current > 0) {
            current--;
            updateView();
        }
    }

    private void trySubmit() {
        java.util.List<Integer> unfinished = new ArrayList<>();
        for (int i = 0; i < totalQuestions; i++)
            if (!answers.containsKey(i)) unfinished.add(i);

        if (!unfinished.isEmpty()) {
            String msg = "Questions ";
            for (int i : unfinished) msg += (i + 1) + ", ";
            msg = msg.substring(0, msg.length() - 2) + " not finished";
            warningLabel.setText(msg);
            actionBtn.setText("Still Submit");
        } else {
            goResult();
        }
    }

    private void goResult() {
        int score = (int) (Math.random() * 100);
        boolean saved = Math.random() > 0.05;
        if (!saved) {
            JOptionPane.showMessageDialog(this, "Error: Failed to save result.","Save Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        new ResultFrame(score);
        dispose();
    }

    class ResultFrame extends JFrame {
        public ResultFrame(int score) {
            setTitle("Result");
            setSize(400, 250);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10,10));

            JLabel scoreLabel = new JLabel("You got " + score + "% of the quiz!", SwingConstants.CENTER);
            scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            add(scoreLabel, BorderLayout.CENTER);

            JPanel options = new JPanel(new FlowLayout());
            JButton detailBtn = new JButton("See Detail");
            JButton shareBtn = new JButton("Share Result");
            options.add(detailBtn);
            options.add(shareBtn);
            add(options, BorderLayout.SOUTH);

            detailBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,"Details not implemented."));
            shareBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,"Shared! Score = " + score + "%"));

            setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizView::new);
    }
}

