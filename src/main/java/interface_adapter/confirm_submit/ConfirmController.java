package interface_adapter.confirm_submit;

import use_case.confirm.ConfirmInputBoundary;
import view.ConfirmDialog;
/**
 * Controller used by the view to request actions.
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
     * Ask interactor to prepare confirmation state (show dialog).
     */
    public void prepareConfirmation() {
        interactor.prepareConfirmation(); // 填充 ConfirmViewModel（假设这个方法存在）
        if (dialog != null) {
            // 显示对话框（如果需要固定 owner 可在 AppBuilder 传入）
            dialog.setLocationRelativeTo(dialog.getOwner());
            dialog.setVisible(true);
        } else {
            System.out.println("ConfirmDialog not injected into ConfirmController");
        }
    }

    /**
     * User pressed action button (either confirm or still submit). We choose behavior based on interactor.
     */
    public void forceSubmit() {
        interactor.forceSubmit();
    }
}
