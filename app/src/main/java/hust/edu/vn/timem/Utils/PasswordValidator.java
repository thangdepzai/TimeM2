package hust.edu.vn.timem.Utils;

public class PasswordValidator {

    private static final String LENGTH_PATTERN = ".{6,20}";
    private static final String DIGITS_PATTERN = ".*\\d.*";
    private static final String LOWERCASE_PATTERN = ".*[a-z].*";
    private static final String UPPERCASE_PATTERN = ".*[A-Z].*";
    private static final String SPECIALS_PATTERN = ".*[!@#$%^&+=~_*].*";

    public static boolean isPasswordLengthValid(String password) {
        return password.matches(LENGTH_PATTERN);
    }

    public static boolean isPasswordContainsDigits(String password) {
        return password.matches(DIGITS_PATTERN);
    }

    public static boolean isPasswordContainsLowercase(String password) {
        return password.matches(LOWERCASE_PATTERN);
    }

    public static boolean isPasswordContainsUppercase(String password) {
        return password.matches(UPPERCASE_PATTERN);
    }

    public static boolean isPasswordContainsSpecials(String password) {
        return password.matches(SPECIALS_PATTERN);
    }
}
