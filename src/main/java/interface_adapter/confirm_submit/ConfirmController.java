package interface_adapter.confirm_submit;

import use_case.confirm.ConfirmInputBoundary;
import view.ConfirmDialog;
import java.awt.Window;

/**
 * Controller used by the view to request actions.
 * send view action to interactor，and show ConfirmDialog when necessary
 */
public class ConfirmController {
    private final ConfirmInputBoundary interactor;
    private ConfirmDialog dialog;

    public ConfirmController(ConfirmInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void setConfirmDialog(ConfirmDialog dialog) {
        this.dialog = dialog;
    }

    /**
     * called externally
     * show in put dialog (if there is)
     */
    public void prepareConfirmation() {
        interactor.prepareConfirmation();

        if (dialog != null) {
            Window owner = dialog.getOwner();
            dialog.setLocationRelativeTo(owner);
            dialog.open();
        } else {
            System.out.println("ConfirmDialog not injected into ConfirmController");
        }
    }

    /**
     * called when user click “Still Submit / Confirm Submit” on ConfirmDialog
     */
    public void forceSubmit() {
        if (dialog != null) {
            dialog.dispose();
        }
        interactor.forceSubmit();
    }
}
