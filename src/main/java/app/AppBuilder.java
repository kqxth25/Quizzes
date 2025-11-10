package app;

import view.ResultView;

import javax.swing.*;

public class AppBuilder {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ResultView();     // or new TakeQuizFrame()
        });
    }
}
