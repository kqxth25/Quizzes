package interface_adapter.quiz;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class QuizViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private QuizState state;

    public QuizViewModel(QuizState state) {
        this.state = state;
    }

    public QuizState getState() {
        return state;
    }

    public void setState(QuizState state) {
        QuizState old = this.state;
        this.state = state;
        support.firePropertyChange("state", old, state);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
