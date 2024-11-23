package com.example.prjtraveltrovesprint.utils;


public class ValidatorUtils {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidFirstName(String fn) {
        return fn != null && (fn.length() >= 1 && fn.length() < 20) && !fn.contains(" ");
    }

    public static boolean isValidLastName(String ln) {
        return ln != null && (ln.length() >= 1 && ln.length() < 30);
    }
}
