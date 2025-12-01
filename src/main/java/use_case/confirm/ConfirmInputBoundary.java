package use_case.confirm;

/**
 * Input boundary for confirm/submit use case.
 * Two operations:
 *  - prepareConfirmation: evaluate current quiz and prepare confirm state (no forced submit)
 *  - forceSubmit: submit anyway (called when user chooses "still submit")
 */
public interface ConfirmInputBoundary {
    void prepareConfirmation();
    void forceSubmit();
}
