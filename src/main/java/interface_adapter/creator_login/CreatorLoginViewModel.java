package interface_adapter.creator_login;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreatorLoginViewModel {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private String errorMessage;
    private boolean loginSuccess;

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void firePropertyChanged() {
        pcs.firePropertyChange("update", null, null);
    }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isLoginSuccess() { return loginSuccess; }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }
}