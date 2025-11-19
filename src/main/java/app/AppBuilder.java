package app;

import data_access.InMemoryUserDataAccessObject;
import data_access.InMemoryQuizDataAccess;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.selectquiz.ListQuizzesController;
import interface_adapter.selectquiz.ListQuizzesPresenter;
import interface_adapter.selectquiz.SelectQuizViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.selectquiz.ListQuizzesDataAccessInterface;
import use_case.selectquiz.ListQuizzesInputBoundary;
import use_case.selectquiz.ListQuizzesInteractor;
import use_case.selectquiz.ListQuizzesOutputBoundary;
import view.ViewManager;
import view.HomeView;
import view.LoginView;
import view.SignupView;
import view.SelectQuizView;
import interface_adapter.creator_login.CreatorLoginController;
import interface_adapter.creator_login.CreatorLoginPresenter;
import interface_adapter.creator_login.CreatorLoginViewModel;
import use_case.creator_login.*;
import view.CreatorLoginView;
import view.QuizView;
import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizPresenter;
import interface_adapter.quiz.QuizViewModel;
import interface_adapter.quiz.QuizState;
import use_case.quiz.LocalQuizRepository;
import use_case.quiz.QuizRepository;
import use_case.quiz.SubmitAnswerInteractor;
import use_case.quiz.SubmitAnswerInputBoundary;
import use_case.quiz.SubmitAnswerOutputBoundary;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final UserFactory userFactory = new UserFactory();
    private final InMemoryUserDataAccessObject userDao = new InMemoryUserDataAccessObject();
    private ListQuizzesDataAccessInterface quizDao = new InMemoryQuizDataAccess();

    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private SelectQuizViewModel selectQuizViewModel;

    private HomeView homeView;
    private LoginView loginView;
    private SignupView signupView;
    private SelectQuizView selectQuizView;
    private CreatorLoginView creatorLoginView;
    private CreatorLoginViewModel creatorLoginViewModel;

    private final ViewManager viewManager;


    public AppBuilder() {
        this.cardPanel.setLayout(this.cardLayout);
        this.viewManager = new ViewManager(this.cardPanel, this.cardLayout, this.viewManagerModel);
    }

    public AppBuilder addHomeView() {
        this.homeView = new HomeView(this.viewManagerModel);
        this.cardPanel.add(this.homeView, this.homeView.getViewName());
        return this;
    }

    public AppBuilder addCreatorLoginView() {
        this.creatorLoginViewModel = new CreatorLoginViewModel();
        this.creatorLoginView = new CreatorLoginView();
        this.cardPanel.add(this.creatorLoginView, this.creatorLoginView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        this.loginViewModel = new LoginViewModel();
        this.loginView = new LoginView(this.loginViewModel, this.viewManagerModel);
        this.cardPanel.add(this.loginView, this.loginView.getViewName());
        return this;
    }

    public AppBuilder addSignupView() {
        this.signupViewModel = new SignupViewModel();
        this.signupView = new SignupView(this.signupViewModel, this.viewManagerModel, this.loginViewModel);
        this.cardPanel.add(this.signupView, this.signupView.getViewName());
        return this;
    }

    public AppBuilder addSelectQuizView() {
        this.selectQuizViewModel = new SelectQuizViewModel();
        this.selectQuizView = new SelectQuizView(this.selectQuizViewModel, this.viewManagerModel);
        this.cardPanel.add(this.selectQuizView, this.selectQuizView.getViewName());
        return this;
    }


    public AppBuilder addSignupUseCase() {
        SignupOutputBoundary presenter =
                new SignupPresenter(this.viewManagerModel, this.signupViewModel, this.loginViewModel);
        SignupInputBoundary interactor =
                new SignupInteractor(this.userDao, presenter, this.userFactory);
        this.signupView.setSignupController(new SignupController(interactor));
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginOutputBoundary presenter =
                new LoginPresenter(this.viewManagerModel, this.selectQuizViewModel, this.loginViewModel);
        LoginInputBoundary interactor = new LoginInteractor(this.userDao, presenter);
        this.loginView.setLoginController(new LoginController(interactor));
        return this;
    }

    public AppBuilder addSelectQuizUseCase() {
        ListQuizzesOutputBoundary presenter = new ListQuizzesPresenter(this.selectQuizViewModel);
        ListQuizzesInputBoundary interactor = new ListQuizzesInteractor(this.quizDao, presenter);
        ListQuizzesController controller = new ListQuizzesController(interactor);
        this.selectQuizView.setController(controller);
        return this;
    }

    public AppBuilder addCreatorLoginUseCase() {
        CreatorLoginOutputBoundary presenter =
                new CreatorLoginPresenter(this.viewManagerModel);

        CreatorLoginInputBoundary interactor =
                new CreatorLoginInteractor(presenter);

        CreatorLoginController controller =
                new CreatorLoginController(interactor);

        this.creatorLoginView.setController(controller);

        return this;
    }
    private QuizView quizView;
    private QuizViewModel quizViewModel;

    public AppBuilder addQuizView() {
        this.quizViewModel = new QuizViewModel(new QuizState(2));
        this.quizView = new QuizView(this.quizViewModel, this.viewManagerModel);
        this.cardPanel.add(this.quizView, "hh");
        return this;
    }

    public AppBuilder addQuizUseCase() {

        String[][] questions = {
                {"What is 2 + 2?"},
                {"What is 3 + 5?"}
        };
        String[][] options = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"}
        };

        QuizRepository repository = new LocalQuizRepository(questions, options);
        QuizPresenter presenter = new QuizPresenter(this.quizViewModel);
        SubmitAnswerInputBoundary interactor = new SubmitAnswerInteractor(presenter, repository);
        QuizController controller = new QuizController(interactor);

        this.quizView.setController(controller);

        return this;
    }
    public AppBuilder addSelectQuizUseCase(ListQuizzesDataAccessInterface externalQuizDao) {
        this.quizDao = externalQuizDao;
        return addSelectQuizUseCase();
    }


    public JFrame build() {
        final JFrame app = new JFrame("Quiz Application");
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.add(this.cardPanel);
        app.setSize(900, 600);
        app.setLocationRelativeTo(null);

        if (this.homeView != null) {
            this.viewManagerModel.navigate(this.homeView.getViewName());
        }
        return app;
    }
}
