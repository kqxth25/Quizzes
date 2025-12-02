package entity;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String questionText;
    private final String correctAnswer;
    private final List<String> incorrectAnswers;

    public Question(String questionText, String correctAnswer, List<String> incorrectAnswers) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestionText() {return questionText;
    }

    public String getCorrectAnswer() {return correctAnswer;
    }

    public List<String> getIncorrectAnswers() {return incorrectAnswers;
    }
}
