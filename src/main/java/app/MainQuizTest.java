package app;

import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizPresenter;
import interface_adapter.quiz.QuizState;
import interface_adapter.quiz.QuizViewModel;
import use_case.quiz.LocalQuizRepository;
import use_case.quiz.QuizRepository;
import use_case.quiz.AnswerQuizInteractor;
import use_case.quiz.AnswerQuizOutputData;
import view.QuizView;

import javax.swing.*;

public class MainQuizTest {

    public static void main(String[] args) {
        JFrame application = new JFrame("Quiz Test");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[][] questions = {
                {"1. What is the capital of France?"},
                {"2. Which planet is known as the Red Planet?"},
                {"3. What is the largest mammal in the world?"},
                {"4. Who wrote 'Romeo and Juliet'?"},
                {"5. The chemical symbol for water is H2O."},
                {"6. How many continents are there?"},
                {"7. The square root of 81 is 9."},
                {"8. In which country are the Pyramids of Giza located?"},
                {"9. What is the main ingredient in guacamole?"},
                {"10. Who painted the Mona Lisa?"}
        };

        String[][] options = {
                {"Paris", "London", "Berlin", "Madrid"},
                {"Earth", "Mars", "Jupiter", "Venus"},
                {"Elephant", "Blue Whale", "Giraffe", "Great White Shark"},
                {"Charles Dickens", "William Shakespeare", "Jane Austen", "Mark Twain"},
                {"True", "False"},
                {"5", "6", "7", "8"},
                {"True", "False"},
                {"Greece", "Mexico", "Egypt", "Italy"},
                {"Tomato", "Avocado", "Onion", "Lime"},
                {"Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"}
        };


        int[] correctAnswers = {0, 1, 1, 1, 0, 2, 0, 2, 1, 2};

        QuizRepository quizRepository = new LocalQuizRepository(questions, options, correctAnswers);


        int totalQuestions = quizRepository.getQuestions().length;
        QuizState quizState = new QuizState(totalQuestions);
        QuizViewModel quizViewModel = new QuizViewModel(quizState);

        QuizPresenter quizPresenter = new QuizPresenter(quizViewModel);
        AnswerQuizInteractor submitAnswerInteractor = new AnswerQuizInteractor(quizPresenter, quizRepository);

        QuizController quizController = new QuizController(submitAnswerInteractor);

        QuizView quizView = new QuizView(quizViewModel, null);
        quizView.setController(quizController);

        String firstQuestionText = quizRepository.getQuestions()[0][0];
        String[] firstOptions = quizRepository.getOptions()[0];
        AnswerQuizOutputData firstQuestionData = new AnswerQuizOutputData(
                -1, -1, 0, firstQuestionText, firstOptions
        );
        quizPresenter.presentNextQuestion(firstQuestionData);

        application.add(quizView);
        application.pack();
        application.setSize(600, 400);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}