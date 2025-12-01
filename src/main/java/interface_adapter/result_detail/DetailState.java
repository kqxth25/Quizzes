package interface_adapter.result_detail;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Objects;

public class DetailState {
    public final String[] questions;
    public final String[][] options;
    public final int[] correctIndex;
    public final int[] savedAnswers;

    public DetailState(String[] questions, String[][] options, int[] correctIndex, int[] savedAnswers) {
        this.questions = questions != null ? questions.clone() : new String[0];
        this.options = options != null ? deepCopy(options) : new String[0][];
        this.correctIndex = correctIndex != null ? correctIndex.clone() : new int[0];
        this.savedAnswers = savedAnswers != null ? savedAnswers.clone() : new int[0];
    }

    private static String[][] deepCopy(String[][] src) {
        String[][] dst = new String[src.length][];
        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i] != null ? src[i].clone() : new String[0];
        }
        return dst;
    }
}