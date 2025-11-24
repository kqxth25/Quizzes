package interface_adapter;

import java.util.HashMap;
import java.util.Map;

public final class ViewManagerState {
    private String activeView = "";
    private String currentUser = null;
    private final Map<String, Object> routeParams = new HashMap<>();

    public String getActiveView() { return activeView; }
    public void setActiveView(String activeView) { this.activeView = activeView; }

    public String getCurrentUser() { return currentUser; }
    public void setCurrentUser(String currentUser) { this.currentUser = currentUser; }

    public Map<String, Object> getRouteParams() { return routeParams; }
    public void clearRouteParams() { routeParams.clear(); }
    public void putRouteParam(String k, Object v) { routeParams.put(k, v); }
}
