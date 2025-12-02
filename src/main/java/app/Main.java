package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame app = new AppBuilder()
                        .addHomeView()
                        .addLoginView()
                        .addSignupView()
                        .addQuizView()
                        .addSelectQuizView()
                        .addManageQuizView()
                        .addCreatorLoginView()
                        .addSignupUseCase()
                        .addLoginUseCase()
                        .addSelectQuizUseCase()
                        .addCreatorLoginUseCase()
                        .addHistoryView()
                        .addHistoryView()
                        .build();
                app.setVisible(true);
            }
        });
    }
}
