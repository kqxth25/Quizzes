package use_case.confirm;

import java.util.List;

/**
 * Response model - simple DTO describing incomplete questions.
 */
public class ConfirmResponseModel {
    private final List<Integer> incompleteQuestionIndices; // zero-based indices
    private final boolean allCompleted;

    public ConfirmResponseModel(List<Integer> incompleteQuestionIndices, boolean allCompleted) {
        this.incompleteQuestionIndices = incompleteQuestionIndices;
        this.allCompleted = allCompleted;
    }

    public List<Integer> getIncompleteQuestionIndices() {
        return incompleteQuestionIndices;
    }

    public boolean isAllCompleted() {
        return allCompleted;
    }
}
