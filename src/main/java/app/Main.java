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
                        .addSelectQuizView()
                        .addCreatorLoginView()
                        .addSelectQuizUseCase()
                        .addCreatorLoginUseCase()
                        .addSignupUseCase()
                        .addLoginUseCase()
                        .build();

                app.setVisible(true);
            }
        });
    }
}
