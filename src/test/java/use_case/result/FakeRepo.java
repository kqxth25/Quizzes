package use_case.result;

import use_case.quiz.QuizRepository_answer;

class FakeRepository implements QuizRepository_answer {

    private final int[] saved;
    private final int[] correct;

    FakeRepository(int[] saved, int[] correct) {
        this.saved = saved;
        this.correct = correct;
    }

    @Override
    public int[] getSavedAnswers() {
        return saved;
    }

    @Override
    public int[] getCorrectAnswers() {
        return correct;
    }

    @Override
    public String[][] getQuestions() {
        return new String[0][];
    }

    @Override
    public String[][] getOptions() {
        return new String[0][];
    }

    @Override
    public void saveAnswer(int index, int selectedOption) {
    }

    @Override
    public void loadQuiz(String quizName) {
    }
}
