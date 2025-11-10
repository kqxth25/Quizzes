package view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ResultView extends JFrame {

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


    public ResultView() {
        setTitle("Take Quiz");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));


        questions = new ArrayList<>();
        for (int i = 1; i <= totalQuestions; i++) {
            questions.add("Question " + i + ": xxxxxxX?");
        }


        options = new ArrayList<>();
        for (int i = 1; i <= totalQuestions; i++) {
            options.add(new String[]{
                    "Option A for Q" + i,
                    "Option B for Q" + i,
                    "Option C for Q" + i,
                    "Option D for Q" + i
            });
        }

        answers = new HashMap<>();


        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(questionLabel, BorderLayout.NORTH);


        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(4, 1));

        choiceBtns = new JRadioButton[4];
        choiceGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            choiceBtns[i] = new JRadioButton();
            choiceBtns[i].setFont(new Font("SansSerif", Font.PLAIN, 16));
            choiceGroup.add(choiceBtns[i]);
            optionPanel.add(choiceBtns[i]);

            final int idx = i;
            choiceBtns[i].addActionListener(e -> answers.put(current, idx));
        }

        add(optionPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new BorderLayout());

        warningLabel = new JLabel("", SwingConstants.CENTER);
        warningLabel.setForeground(Color.RED);
        bottomPanel.add(warningLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel();
        prevBtn = new JButton("Previous");
        actionBtn = new JButton("Next");
        btnPanel.add(prevBtn);
        btnPanel.add(actionBtn);

        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);


        prevBtn.addActionListener(e -> goPrevious());
        actionBtn.addActionListener(e -> handleAction());

        updateView();
        setVisible(true);
    }


    private void updateView() {
        questionLabel.setText(questions.get(current));

        String[] opts = options.get(current);
        for (int i = 0; i < 4; i++) {
            choiceBtns[i].setText(opts[i]);
        }

        choiceGroup.clearSelection();
        if (answers.containsKey(current)) {
            choiceBtns[answers.get(current)].setSelected(true);
        }

        warningLabel.setText("");
        prevBtn.setEnabled(current > 0);

        if (current == totalQuestions - 1) {
            actionBtn.setText("Submit");
        } else {
            actionBtn.setText("Next");
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

        for (int i = 0; i < totalQuestions; i++) {
            if (!answers.containsKey(i)) {
                unfinished.add(i);
            }
        }

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
        System.out.println(">>> goResult() called");

        int score = (int) (Math.random() * 100);

        boolean saved = mockSave(score);
        if (!saved) {
            JOptionPane.showMessageDialog(this,
                    "Error: Failed to save result.",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println(">>> Open result window");

        new ResultFrame(score);
        dispose();
    }


    private boolean mockSave(int score) {
        return Math.random() > 0.05;
    }


    class ResultFrame extends JFrame {
        public ResultFrame(int score) {
            setTitle("Result");
            setSize(400, 250);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10,10));

            JLabel scoreLabel =
                    new JLabel("You got " + score + "% of the quiz!", SwingConstants.CENTER);
            scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            add(scoreLabel, BorderLayout.CENTER);

            JPanel options = new JPanel(new FlowLayout());
            JButton detailBtn = new JButton("See Detail");
            JButton shareBtn = new JButton("Share Result");
            options.add(detailBtn);
            options.add(shareBtn);

            add(options, BorderLayout.SOUTH);

            detailBtn.addActionListener(e -> showDetails());
            shareBtn.addActionListener(e -> shareResult(score));

            setVisible(true);
        }

        private void showDetails() {
            JOptionPane.showMessageDialog(this,
                    "Details not implemented.");
        }

        private void shareResult(int score) {
            JOptionPane.showMessageDialog(this,
                    "Shared! Score = " + score + "%");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ResultView::new);
    }
}
