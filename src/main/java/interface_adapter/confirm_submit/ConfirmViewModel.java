package interface_adapter.confirm_submit;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ConfirmViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ConfirmState state;

    public ConfirmViewModel(ConfirmState state) {
        this.state = state;
    }

    public ConfirmState getState() {
        return state;
    }

    public void setState(ConfirmState state) {
        ConfirmState old = this.state;
        this.state = state;
        support.firePropertyChange("confirmState", old, state);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("confirmState", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
