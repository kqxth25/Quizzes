package interface_adapter.result;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ResultViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ResultState state;

    public ResultViewModel(ResultState state) {
        this.state = state;
    }

    public ResultState getState() {
        return state;
    }

    public void setState(ResultState state) {
        ResultState old = this.state;
        this.state = state;
        support.firePropertyChange("resultState", old, state);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("resultState", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
}
