package dev.team.githubtrendanalysis.controllers;
import java.util.HashMap;
import java.util.Map;

public class EmojiConverter {

    private static final Map<String, String> emojiMap = new HashMap<>();

    static {
        emojiMap.put(":desktop_computer:", "\uD83D\uDCBB");
        // Diğer emoji kısa kodları ve karşılık gelen emoji karakterlerini buraya ekleyin.
    }

    public static String convertToEmoji(String text) {
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }
        return text;
    }
}
