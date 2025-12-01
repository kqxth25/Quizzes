package use_case.quiz;

import interface_adapter.quiz.QuizState;

/**
 * Provider interface used by use-cases to fetch current quiz state.
 * Implement this with an adapter to your QuizViewModel (below).
 */
public interface QuizStateProvider {
    QuizState getQuizState();
}
