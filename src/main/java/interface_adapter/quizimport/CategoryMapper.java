package interface_adapter.quizimport;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CategoryMapper {
    private static final Map<String, Integer> MAP = new LinkedHashMap<>();

    static {
        MAP.put("Any", 0);
        MAP.put("General Knowledge", 9);
        MAP.put("Entertainment: Books", 10);
        MAP.put("Entertainment: Film", 11);
        MAP.put("Entertainment: Music", 12);
        MAP.put("Entertainment: Musicals & Theatres", 13);
        MAP.put("Entertainment: Television", 14);
        MAP.put("Entertainment: Video Games", 15);
        MAP.put("Entertainment: Board Games", 16);
        MAP.put("Science & Nature", 17);
        MAP.put("Science: Computers", 18);
        MAP.put("Science: Mathematics", 19);
        MAP.put("Mythology", 20);
        MAP.put("Sports", 21);
        MAP.put("Geography", 22);
        MAP.put("History", 23);
        MAP.put("Politics", 24);
        MAP.put("Art", 25);
        MAP.put("Celebrities", 26);
        MAP.put("Animals", 27);
        MAP.put("Vehicles", 28);
        MAP.put("Entertainment: Comics", 29);
        MAP.put("Science: Gadgets", 30);
        MAP.put("Entertainment: Japanese Anime & Manga", 31);
        MAP.put("Entertainment: Cartoon & Animations", 32);
    }

    public static int getCategoryId(String name) {
        return MAP.getOrDefault(name, 0);
    }

    public static String[] getCategoryNames() {
        Set<String> keys = MAP.keySet();
        return keys.toArray(new String[0]);
    }
}
