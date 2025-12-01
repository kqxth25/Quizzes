package interface_adapter.confirm_submit;

import use_case.confirm.ConfirmInputBoundary;
import view.ConfirmDialog;
import java.awt.Window;

/**
 * Controller used by the view to request actions.
 * 负责把 view 的动作转给 interactor，并在需要时显示 ConfirmDialog。
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
     * 由外部（例如 QuizController.prepareConfirmation）调用，要求 interactor 准备确认状态，
     * 然后显示注入进来的 dialog（如果有注入的话）。
     */
    public void prepareConfirmation() {
        // 先让 interactor 计算/准备好 ConfirmViewModel（通过 presenter -> viewmodel）
        interactor.prepareConfirmation();

        // 然后弹出 dialog（如果已经注入）
        if (dialog != null) {
            // 使 dialog 居中在 owner 上（owner 在 AppBuilder 创建 dialog 时传入）
            Window owner = dialog.getOwner();
            dialog.setLocationRelativeTo(owner);
            dialog.open(); // ConfirmDialog.open() 会 setVisible(true)
        } else {
            // 开发时方便定位问题
            System.out.println("ConfirmDialog not injected into ConfirmController");
        }
    }

    /**
     * 用户在 ConfirmDialog 上按下 “Still Submit / Confirm Submit” 时调用。
     */
    public void forceSubmit() {
        if (dialog != null) {
            dialog.dispose();
        }
        interactor.forceSubmit();
    }
}
