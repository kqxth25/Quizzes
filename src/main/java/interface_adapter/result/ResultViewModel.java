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
        support.firePropertyChange("resultState", old, this.state);
    }

    public void setScore(int score) {
        ResultState old = this.state;
        this.state.setScore(score);
        support.firePropertyChange("resultState", old, this.state);
    }

    public void setTotal(int total) {
        ResultState old = this.state;
        this.state.setTotal(total);
        support.firePropertyChange("resultState", old, this.state);
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
