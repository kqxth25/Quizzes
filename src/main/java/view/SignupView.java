package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;

    private SignupController signupController;

    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);

    private final JButton signUp;
    private final JButton cancel;
    private final JButton toLogin;

    private static final Color BG_APP = new Color(243, 244, 246);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color PRIMARY = new Color(59, 130, 246);
    private static final Color PRIMARY_HOVER = new Color(96, 165, 250);
    private static final Color SECONDARY = new Color(148, 163, 184);
    private static final Color SECONDARY_HOVER = new Color(148, 163, 184).brighter();

    public SignupView(SignupViewModel signupViewModel,
                      ViewManagerModel viewManagerModel,
                      LoginViewModel loginViewModel) {
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;

        this.signupViewModel.addPropertyChangeListener(this);

        setBackground(BG_APP);
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_MAIN);

        JLabel subtitle = new JLabel("Create an account to start quizzes");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);

        JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        repeatPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        LabelTextPanel usernameInfo =
                new LabelTextPanel(usernameLabel, usernameInputField);
        LabelTextPanel passwordInfo =
                new LabelTextPanel(passwordLabel, passwordInputField);
        LabelTextPanel repeatPasswordInfo =
                new LabelTextPanel(repeatPasswordLabel, repeatPasswordInputField);

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(16));

        card.add(usernameInfo);
        card.add(Box.createVerticalStrut(8));
        card.add(passwordInfo);
        card.add(Box.createVerticalStrut(8));
        card.add(repeatPasswordInfo);
        card.add(Box.createVerticalStrut(16));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setOpaque(false);

        toLogin = createSecondaryButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        signUp = createPrimaryButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        cancel = createSecondaryButton(SignupViewModel.CANCEL_BUTTON_LABEL);

        buttons.add(toLogin);
        buttons.add(signUp);
        buttons.add(cancel);

        card.add(buttons);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);


        signUp.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() == signUp && signupController != null) {
                    SignupState s = signupViewModel.getState();
                    signupController.execute(
                            s.getUsername(),
                            s.getPassword(),
                            s.getRepeatPassword()
                    );
                }
            }
        });

        toLogin.addActionListener(e ->
                viewManagerModel.navigate(loginViewModel.getViewName())
        );

        cancel.addActionListener(this);

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void sync() {
                SignupState s = signupViewModel.getState();
                s.setUsername(usernameInputField.getText());
                signupViewModel.setState(s);
            }
            @Override public void insertUpdate(DocumentEvent e) { sync(); }
            @Override public void removeUpdate(DocumentEvent e) { sync(); }
            @Override public void changedUpdate(DocumentEvent e) { sync(); }
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void sync() {
                SignupState s = signupViewModel.getState();
                s.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(s);
            }
            @Override public void insertUpdate(DocumentEvent e) { sync(); }
            @Override public void removeUpdate(DocumentEvent e) { sync(); }
            @Override public void changedUpdate(DocumentEvent e) { sync(); }
        });
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void sync() {
                SignupState s = signupViewModel.getState();
                s.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(s);
            }
            @Override public void insertUpdate(DocumentEvent e) { sync(); }
            @Override public void removeUpdate(DocumentEvent e) { sync(); }
            @Override public void changedUpdate(DocumentEvent e) { sync(); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == cancel) {
            usernameInputField.setText("");
            passwordInputField.setText("");
            repeatPasswordInputField.setText("");

            SignupState s = signupViewModel.getState();
            s.setUsername("");
            s.setPassword("");
            s.setRepeatPassword("");
            s.setUsernameError(null);
            s.setPasswordError(null);
            s.setRepeatPasswordError(null);
            signupViewModel.setState(s);
            signupViewModel.firePropertyChange();

            viewManagerModel.navigate("home");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) return;

        SignupState s = (SignupState) evt.getNewValue();

        if (!usernameInputField.getText().equals(s.getUsername())) {
            usernameInputField.setText(s.getUsername());
        }

        if (s.getUsernameError() != null) {
            JOptionPane.showMessageDialog(
                    this,
                    s.getUsernameError(),
                    "Sign up failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        if (s.getPasswordError() != null) {
            JOptionPane.showMessageDialog(
                    this,
                    s.getPasswordError(),
                    "Sign up failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        if (s.getRepeatPasswordError() != null) {
            JOptionPane.showMessageDialog(
                    this,
                    s.getRepeatPasswordError(),
                    "Sign up failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
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
}
