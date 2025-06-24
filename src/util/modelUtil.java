package util;

public class modelUtil {
    public static int formatId(String id, boolean removeFirstChar) {
        try {
            id = id.trim();
            if (removeFirstChar && id.length() > 1) {
                id = id.substring(1);
            }
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id, should in Form: 'X1234'");
        }
    }
}
