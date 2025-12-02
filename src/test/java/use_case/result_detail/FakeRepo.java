package use_case.result_detail;

import use_case.quiz.QuizRepository_answer;

class FakeRepo implements QuizRepository_answer {

    boolean returnNull = false;

    @Override
    public String[][] getQuestions() {
        if (returnNull) return null;
        return new String[][]{{"Q1?"}};
    }

    @Override
    public String[][] getOptions() {
        if (returnNull) return null;
        return new String[][]{{"A", "B", "C"}};
    }

    @Override
    public int[] getCorrectAnswers() {
        if (returnNull) return null;
        return new int[]{1};
    }

    @Override
    public int[] getSavedAnswers() {
        if (returnNull) return null;
        return new int[]{2};
    }

    @Override
    public void saveAnswer(int index, int selectedOption) {
    }

    @Override
    public void loadQuiz(String quizName) {
    }
}
