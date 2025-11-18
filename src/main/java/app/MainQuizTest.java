package app;

import javax.swing.*;
import view.QuizView;
import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizPresenter;
import interface_adapter.quiz.QuizViewModel;
import interface_adapter.quiz.QuizState;
import use_case.quiz.*;

public class MainQuizTest {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {


            String[][] questions = {
                    {"What is 2+2?"},
                    {"What is 3+3?"}
            };
            String[][] options = {
                    {"1","2","3","4"},
                    {"4","5","6","7"}
            };
            QuizRepository repo = new LocalQuizRepository(questions, options);

    s
            QuizState state = new QuizState(questions.length);
            QuizViewModel viewModel = new QuizViewModel(state);


            QuizView quizView = new QuizView(viewModel, null);


            QuizPresenter presenter = new QuizPresenter(viewModel);
            SubmitAnswerInteractor interactor = new SubmitAnswerInteractor(presenter, repo);


            QuizController controller = new QuizController(interactor);
            quizView.setController(controller);


            interactor.submitAnswer(new SubmitAnswerInputData(-1, -1));



            JFrame frame = new JFrame("Quiz Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(quizView);
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

