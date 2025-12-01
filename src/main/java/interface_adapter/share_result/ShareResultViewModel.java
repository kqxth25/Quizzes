package interface_adapter.share_result;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShareResultViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ShareResultState state = new ShareResultState();

    public ShareResultState getState() {
        return state;
    }

    public void setState(ShareResultState newState) {
        ShareResultState old = this.state;
        this.state = newState;
        support.firePropertyChange("shareResultState", old, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
