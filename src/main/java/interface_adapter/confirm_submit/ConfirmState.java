package interface_adapter.confirm_submit;

import java.util.List;

/**
 * State object used by ConfirmViewModel and ConfirmView.
 */
public class ConfirmState {
    private final List<Integer> incompleteQuestionIndices; // zero-based
    private final boolean allCompleted;
    private final String message;
    private final String actionButtonText; // "Confirm Submit" or "Still Submit"

    public ConfirmState(List<Integer> incompleteQuestionIndices, boolean allCompleted, String message, String actionButtonText) {
        this.incompleteQuestionIndices = incompleteQuestionIndices;
        this.allCompleted = allCompleted;
        this.message = message;
        this.actionButtonText = actionButtonText;
    }

    public List<Integer> getIncompleteQuestionIndices() {
        return incompleteQuestionIndices;
    }

    public boolean isAllCompleted() {
        return allCompleted;
    }

    public String getMessage() {
        return message;
    }

    public String getActionButtonText() {
        return actionButtonText;
    }
}
