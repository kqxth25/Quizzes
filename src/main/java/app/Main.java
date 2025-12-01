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
                        .addCreatorLoginView()
                        .addManageQuizView()
                        .addSignupUseCase()
                        .addLoginUseCase()
                        .addSelectQuizUseCase()
                        .addCreatorLoginUseCase()
                        .addHistoryView()
                        .build();
                app.setVisible(true);
            }
        });
    }
}
