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

    public SignupView(SignupViewModel signupViewModel,
                      ViewManagerModel viewManagerModel,
                      LoginViewModel loginViewModel) {
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;

        this.signupViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel usernameInfo =
                new LabelTextPanel(new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        LabelTextPanel passwordInfo =
                new LabelTextPanel(new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
        LabelTextPanel repeatPasswordInfo =
                new LabelTextPanel(new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);

        JPanel buttons = new JPanel();
        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(toLogin);
        buttons.add(signUp);
        buttons.add(cancel);

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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(buttons);
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
            JOptionPane.showMessageDialog(this, s.getUsernameError());
        }
        if (s.getPasswordError() != null) {
            JOptionPane.showMessageDialog(this, s.getPasswordError());
        }
        if (s.getRepeatPasswordError() != null) {
            JOptionPane.showMessageDialog(this, s.getRepeatPasswordError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
