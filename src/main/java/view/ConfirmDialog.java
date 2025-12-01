package view;

import interface_adapter.confirm_submit.ConfirmController;
import interface_adapter.confirm_submit.ConfirmViewModel;

import javax.swing.*;
import java.awt.*;

public class ConfirmDialog extends JDialog {

    public ConfirmDialog(Window owner, ConfirmViewModel viewModel, ConfirmController controller) {
        super(owner, "Confirm Submit", ModalityType.APPLICATION_MODAL);
        ConfirmView content = new ConfirmView(viewModel);
        content.setController(controller);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().add(content);
        setMinimumSize(new Dimension(500, 250));
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    public void open() {
        setVisible(true);
    }
}
