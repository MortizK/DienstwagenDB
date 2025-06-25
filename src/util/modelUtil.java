package util;

public class modelUtil {
    /**
     * Formats the id of a driver or car to an int.
     * @param id String in the form: 'X123' or '123456'
     * @param removeFirstChar if true, the first character will be removed.
     * @return the id as an int.
     * @throws IllegalArgumentException if the id is not in the correct form.
     */
    public static int formatId(String id, boolean removeFirstChar) {
        String errorMsg = "Invalid id, should in Form: 'X123' or '123456', but was: '" + id + "'!";
        try {
            id = id.trim();
            if (removeFirstChar && id.length() > 1) {
                id = id.substring(1);
            }
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
