package utils;

public class Utils {
    public static String replaceLast(String str, String target, String replacement) {
        int lastIndex = str.lastIndexOf(target);
        if (lastIndex >= 0) {
            str = str.substring(0, lastIndex) + str.substring(lastIndex).replaceFirst(target, replacement);
        }
        return str;
    }
}
