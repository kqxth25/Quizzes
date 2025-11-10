package interface_adapter;

import java.util.Map;

public class ViewManagerModel extends ViewModel<ViewManagerState> {

    public ViewManagerModel() {
        super("view-manager");
        this.setState(new ViewManagerState());
    }

    public void navigate(String targetViewName, Map<String, Object> params) {
        ViewManagerState s = getState();
        s.clearRouteParams();
        if (params != null) params.forEach(s::putRouteParam);
        s.setActiveView(targetViewName);
        firePropertyChange("active");
    }

    public void navigate(String targetViewName) {
        navigate(targetViewName, null);
    }

    public void setCurrentUser(String username) {
        getState().setCurrentUser(username);
        firePropertyChange("currentUser");
    }

    public String getCurrentUser() {
        return getState().getCurrentUser();
    }

    public String getActiveView() {
        return getState().getActiveView();
    }
}
