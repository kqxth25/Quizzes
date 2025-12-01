package use_case.quiz;

import entity.Question;
import entity.Quiz;
import use_case.quizimport.QuizRepository_import;

import java.util.ArrayList;
import java.util.List;

public class ImportedQuizRepositoryAdapter implements QuizRepository_answer {

    private final QuizRepository_import importRepository;
    private String[][] questions;
    private String[][] options;
    private int[] correctAnswers;

    public ImportedQuizRepositoryAdapter(QuizRepository_import importRepository) {
        this.importRepository = importRepository;
        this.questions = new String[0][0];
        this.options = new String[0][0];
        this.correctAnswers = new int[0];
    }

    @Override
    public void loadQuiz(String quizName) {
        Quiz quiz = importRepository.getByName(quizName);

        if (quiz == null) {
            this.questions = new String[0][0];
            this.options = new String[0][0];
            this.correctAnswers = new int[0];
            return;
        }

        List<Question> questionList = quiz.getQuestions();
        int size = questionList.size();

        this.questions = new String[size][1];
        this.options = new String[size][];
        this.correctAnswers = new int[size];

        for (int i = 0; i < size; i++) {
            Question q = questionList.get(i);

            // question text
            this.questions[i][0] = q.getQuestionText();

            // options: correct answer first, then incorrect answers
            List<String> allOptions = new ArrayList<>();
            allOptions.add(q.getCorrectAnswer());
            allOptions.addAll(q.getIncorrectAnswers());
            this.options[i] = allOptions.toArray(new String[0]);

            // correct answer index is always 0 here
            this.correctAnswers[i] = 0;
        }
    }

    public int getQuestionCount() {
        return questions.length;
    }

    @Override
    public String[][] getQuestions() {
        return questions;
    }

    @Override
    public String[][] getOptions() {
        return options;
    }

    @Override
    public int[] getCorrectAnswers() {
        return correctAnswers;
    }

    // Imported quizzes do not have saved answers (user answers)
    @Override
    public int[] getSavedAnswers() {
        return new int[0];
    }

    @Override
    public void saveAnswer(int questionIndex, int answerIndex) {
        // imported quizzes do not store answers
    }

}
