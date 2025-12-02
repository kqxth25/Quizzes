package interface_adapter.result_detail;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Objects;

/** State holder used by DetailView (simple observable) */
public class DetailViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private DetailState state;

    public DetailViewModel(DetailState initial) {
        this.state = initial;
    }

    public DetailState getState() {
        return state;
    }

    public void setState(DetailState newState) {
        DetailState old = this.state;
        this.state = newState;
        support.firePropertyChange("detailState", old, newState);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
}
