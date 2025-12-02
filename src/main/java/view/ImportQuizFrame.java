package view;

import interface_adapter.quizimport.CategoryMapper;
import interface_adapter.quizimport.QuizImportController;
import interface_adapter.quizimport.QuizImportPresenter;
import use_case.quizimport.QuizImportInteractor;
import use_case.quizimport.QuizRepository_import;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class ImportQuizFrame extends JFrame {

    private final QuizImportController controller;
    private final Runnable onImported;

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color SECONDARY_HOVER = new Color(148, 163, 184).brighter();

    private static final Color FIELD_BG = new Color(241, 245, 249);
    private static final int FIELD_HEIGHT = 40;

    public ImportQuizFrame(JFrame parent, QuizRepository_import repository, Runnable onImported) {
        super("Import Quiz");
        this.onImported = onImported;

        setSize(480, 460);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(BG_APP);

        QuizImportPresenter presenter = new QuizImportPresenter(this);
        QuizImportInteractor interactor = new QuizImportInteractor(presenter, repository);
        this.controller = new QuizImportController(interactor);

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Import Quiz");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_MAIN);

        JLabel subtitle = new JLabel("Fill the fields to import a quiz");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(18));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        class FieldWrapper extends JPanel {
            FieldWrapper(JComponent inner) {
                setLayout(new BorderLayout());
                setBackground(FIELD_BG);
                setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                setPreferredSize(new Dimension(220, FIELD_HEIGHT));
                setMaximumSize(new Dimension(220, FIELD_HEIGHT));
                setMinimumSize(new Dimension(220, FIELD_HEIGHT));
                inner.setOpaque(false);
                add(inner, BorderLayout.CENTER);
            }
        }

        class FlatComboUI extends BasicComboBoxUI {
            @Override
            protected JButton createArrowButton() {
                JButton arrow = new JButton("\u25BC");
                arrow.setBorder(null);
                arrow.setFocusable(false);
                arrow.setOpaque(false);
                arrow.setContentAreaFilled(false);
                arrow.setForeground(new Color(71, 85, 105));
                arrow.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                return arrow;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            }
        }

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Quiz Name:");
        nameLabel.setFont(labelFont);
        nameLabel.setForeground(TEXT_MAIN);
        form.add(nameLabel, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField("Quiz " + System.currentTimeMillis() % 1000);
        nameField.setFont(fieldFont);
        nameField.setBorder(null);
        form.add(new FieldWrapper(nameField), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel amountLabel = new JLabel("Number of Questions:");
        amountLabel.setFont(labelFont);
        amountLabel.setForeground(TEXT_MAIN);
        form.add(amountLabel, gbc);

        gbc.gridx = 1;
        JTextField amountField = new JTextField("10");
        amountField.setFont(fieldFont);
        amountField.setBorder(null);

        ((AbstractDocument) amountField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("\\d*")) super.insertString(fb, offset, string, attr);
            }
            @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("\\d*")) super.replace(fb, offset, length, text, attrs);
            }
        });

        form.add(new FieldWrapper(amountField), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel catLabel = new JLabel("Category:");
        catLabel.setFont(labelFont);
        catLabel.setForeground(TEXT_MAIN);
        form.add(catLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> categoryBox = new JComboBox<>(CategoryMapper.getCategoryNames());
        categoryBox.setUI(new FlatComboUI());
        categoryBox.setFont(fieldFont);
        categoryBox.setBorder(null);
        categoryBox.setBackground(FIELD_BG);

        form.add(new FieldWrapper(categoryBox), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel diffLabel = new JLabel("Difficulty:");
        diffLabel.setFont(labelFont);
        diffLabel.setForeground(TEXT_MAIN);
        form.add(diffLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> difficultyBox = new JComboBox<>(new String[]{"Any", "Easy", "Medium", "Hard"});
        difficultyBox.setUI(new FlatComboUI());
        difficultyBox.setFont(fieldFont);
        difficultyBox.setBorder(null);
        difficultyBox.setBackground(FIELD_BG);

        form.add(new FieldWrapper(difficultyBox), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(labelFont);
        typeLabel.setForeground(TEXT_MAIN);
        form.add(typeLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Any", "Multiple Choice", "Boolean"});
        typeBox.setUI(new FlatComboUI());
        typeBox.setFont(fieldFont);
        typeBox.setBorder(null);
        typeBox.setBackground(FIELD_BG);

        form.add(new FieldWrapper(typeBox), gbc);

        card.add(form);

        card.add(Box.createVerticalStrut(20));
        JButton importBtn = createPrimaryButton("Import Quiz");
        importBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(importBtn);

        importBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a name.");
                    return;
                }

                int amount = Integer.parseInt(amountField.getText().trim());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be > 0");
                    return;
                }

                String category = (String) categoryBox.getSelectedItem();
                String difficulty = ((String) difficultyBox.getSelectedItem()).toLowerCase();
                String type = ((String) typeBox.getSelectedItem()).toLowerCase();

                String typeForInteractor = "any";
                if (type.startsWith("multi")) typeForInteractor = "multiple";
                else if (type.startsWith("bool") || type.contains("true")) typeForInteractor = "boolean";

                controller.importQuiz(name, amount, category, difficulty, typeForInteractor);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Number of questions must be a number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        GridBagConstraints outer = new GridBagConstraints();
        outer.gridx = 0;
        outer.gridy = 0;
        add(card, outer);

        setVisible(true);
    }

    public void showSuccess(entity.Quiz quiz) {
        Window w = SwingUtilities.getWindowAncestor(this);
        JFrame parent = w instanceof JFrame ? (JFrame) w : null;

        new ImportSuccessDialog(parent, "Imported: " + quiz.getName());

        if (onImported != null) onImported.run();
        dispose();
    }


    public void showError(String message) {
        Window w = SwingUtilities.getWindowAncestor(this);
        JFrame parent = w instanceof JFrame ? (JFrame) w : null;

        new HttpErrorDialog(parent, "Import error: " + message);
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
        b.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        b.setForeground(fg);
        return b;
    }

    private static JButton createPrimaryButton(String text) {
        return baseButton(text, PRIMARY, PRIMARY_HOVER, Color.WHITE);
    }

    private static JButton createSecondaryButton(String text) {
        return baseButton(text, SECONDARY, SECONDARY_HOVER, Color.WHITE);
    }
}