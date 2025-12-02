package app;

import data_access.FileQuizRepository;
import data_access.FileUserDataAccessObject;
import interface_adapter.selectquiz.QuizListDataAccessAdapter;
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
import use_case.user_login.LoginInputBoundary;
import use_case.user_login.LoginInteractor;
import use_case.user_login.LoginOutputBoundary;
import use_case.quizimport.QuizRepository_import;
import use_case.user_signup.SignupInputBoundary;
import use_case.user_signup.SignupInteractor;
import use_case.user_signup.SignupOutputBoundary;
import use_case.selectquiz.ListQuizzesDataAccessInterface;
import use_case.selectquiz.ListQuizzesInputBoundary;
import use_case.selectquiz.ListQuizzesInteractor;
import use_case.selectquiz.ListQuizzesOutputBoundary;
import view.*;
import interface_adapter.creator_login.CreatorLoginController;
import interface_adapter.creator_login.CreatorLoginPresenter;
import interface_adapter.creator_login.CreatorLoginViewModel;
import use_case.creator_login.*;
import interface_adapter.quiz.QuizController;
import interface_adapter.quiz.QuizPresenter;
import interface_adapter.quiz.QuizViewModel;
import interface_adapter.quiz.QuizState;
import use_case.quiz.QuizRepository_answer;
import use_case.quiz.AnswerQuizInteractor;
import use_case.quiz.AnswerQuizInputBoundary;

import interface_adapter.history.HistoryController;
import use_case.history.HistoryInputBoundary;
import use_case.history.HistoryInteractor;
import use_case.history.HistoryOutputBoundary;
import interface_adapter.history.HistoryPresenter;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final UserFactory userFactory = new UserFactory();
    private final FileUserDataAccessObject userDao = new FileUserDataAccessObject("users.csv", userFactory);

    private final QuizRepository_import manageQuizRepository = new FileQuizRepository("quizzes.csv");
    private ListQuizzesDataAccessInterface quizDao = new QuizListDataAccessAdapter(manageQuizRepository);

    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private SelectQuizViewModel selectQuizViewModel;

    private HomeView homeView;
    private LoginView loginView;
    private SignupView signupView;
    private SelectQuizView selectQuizView;
    private CreatorLoginView creatorLoginView;
    private CreatorLoginViewModel creatorLoginViewModel;

    private QuizViewModel quizViewModel;
    private QuizController quizController;
    private QuizView quizView;
    private use_case.quiz.QuizRepository_answer quizAnswerRepository;

    private HistoryView historyView;
    private ManageQuizView manageQuizView;

    private final ViewManager viewManager;

    // ----------- CONFIRM SUBMIT FEATURE (created later) -----------
    private interface_adapter.confirm_submit.ConfirmViewModel confirmVm;
    private interface_adapter.confirm_submit.ConfirmController confirmController;
    // -------------------------------------------------------------
// ================= RESULT FEATURE =================
    private interface_adapter.result.ResultViewModel resultVm;
    private interface_adapter.result.ResultController resultController;

    public AppBuilder addResultFeature() {

        // (1) view model
        this.resultVm = new interface_adapter.result.ResultViewModel(new interface_adapter.result.ResultState());

        // (2) callback: navigate to "resultView"
        Runnable showResult = () -> {
            this.viewManagerModel.navigate("resultView");
        };

        // (3) presenter
        interface_adapter.result.ResultPresenter presenter =
                new interface_adapter.result.ResultPresenter(this.resultVm, showResult);

        // (4) interactor
        use_case.result.ResultInteractor interactor =
                new use_case.result.ResultInteractor(this.quizAnswerRepository, presenter);

        // (5) controller
        this.resultController =
                new interface_adapter.result.ResultController(interactor);

        // (6) Result View (card layout)
        view.ResultView resultView =
                new view.ResultView(resultVm, this.viewManagerModel);

        this.cardPanel.add(resultView, "resultView");

        return this;
    }
    private HistoryController historyController;

    public AppBuilder() {
        this.cardPanel.setLayout(this.cardLayout);
        this.viewManager = new ViewManager(this.cardPanel, this.cardLayout, this.viewManagerModel);

        addHistoryUseCase();
    }

    public AppBuilder addHomeView() {
        this.homeView = new HomeView(this.viewManagerModel);
        this.cardPanel.add(this.homeView, this.homeView.getViewName());
        return this;
    }

    public AppBuilder addCreatorLoginView() {
        this.creatorLoginViewModel = new CreatorLoginViewModel();
        this.creatorLoginView = new CreatorLoginView(this.viewManagerModel, this.creatorLoginViewModel);
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
        this.selectQuizView = new SelectQuizView(
                this.selectQuizViewModel,
                this.viewManagerModel,
                this.quizViewModel,
                this.quizController,
                this.historyController
        );
        this.cardPanel.add(this.selectQuizView, this.selectQuizView.getViewName());
        return this;
    }

    public AppBuilder addManageQuizView() {
        this.manageQuizView = new ManageQuizView(this.viewManagerModel, this.manageQuizRepository);
        this.cardPanel.add(this.manageQuizView, this.manageQuizView.getViewName());
        return this;
    }

    public AppBuilder addHistoryView() {
        this.historyView = new HistoryView(this.viewManagerModel);
        this.cardPanel.add(this.historyView, this.historyView.getViewName());
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
                new CreatorLoginPresenter(this.viewManagerModel, this.creatorLoginViewModel);
        CreatorLoginInputBoundary interactor =
                new CreatorLoginInteractor(presenter);
        CreatorLoginController controller =
                new CreatorLoginController(interactor);
        this.creatorLoginView.setController(controller);
        return this;
    }

    public AppBuilder addQuizView() {
        this.quizAnswerRepository = new use_case.quiz.ImportedQuizRepositoryAdapter(this.manageQuizRepository);

        this.quizViewModel = new QuizViewModel(new QuizState(10));
        QuizPresenter presenter = new QuizPresenter(this.quizViewModel);
        AnswerQuizInputBoundary interactor = new AnswerQuizInteractor(presenter, this.quizAnswerRepository);
        this.quizController = new QuizController(interactor);

        this.quizView = new QuizView(this.quizViewModel, this.viewManagerModel);
        this.quizView.setController(this.quizController);
        this.cardPanel.add(this.quizView, "quiz");

        return this;
    }

    // ----------- NEW: confirm submit feature, created AFTER quizViewModel exists ----------------
    public AppBuilder addConfirmSubmitFeature(JFrame app) {

        interface_adapter.confirm_submit.ConfirmState initialState =
                new interface_adapter.confirm_submit.ConfirmState(
                        java.util.Collections.emptyList(),
                        false,
                        "",
                        "Confirm Submit"
                );

        this.confirmVm = new interface_adapter.confirm_submit.ConfirmViewModel(initialState);

        interface_adapter.confirm_submit.ConfirmPresenter confirmPresenter =
                new interface_adapter.confirm_submit.ConfirmPresenter(this.viewManagerModel, this.confirmVm);

        use_case.quiz.QuizStateProvider provider =
                new interface_adapter.quiz.QuizStateProviderImpl(this.quizViewModel, this.quizAnswerRepository);

        use_case.confirm.ConfirmInteractor confirmInteractor =
                new use_case.confirm.ConfirmInteractor(provider, confirmPresenter);

        this.confirmController =
                new interface_adapter.confirm_submit.ConfirmController(confirmInteractor);


        view.ConfirmDialog confirmDialog =
                new view.ConfirmDialog(app, this.confirmVm, this.confirmController);

        this.confirmController.setConfirmDialog(confirmDialog);

        this.quizController.setConfirmController(this.confirmController);

        return this;
    }

    // -------------------------------------------------------------------------------------------
    public AppBuilder addHistoryUseCase() {
        HistoryOutputBoundary presenter = new HistoryPresenter(this.viewManagerModel);

        HistoryInputBoundary interactor = new HistoryInteractor(presenter);

        this.historyController = new HistoryController(interactor);

        return this;
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

        // MUST: create result feature before confirm submit
        this.addResultFeature();

        // confirm submit depends on result controller
        this.addConfirmSubmitFeature(app);

        return app;
    }
}
