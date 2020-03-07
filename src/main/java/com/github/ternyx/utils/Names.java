package com.github.ternyx.utils;
/**
 * Names
 */
public class Names {
    public static String verifyName(String name) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        String trimmed = name.trim();

        if (trimmed.length() == 0) {
            throw new IllegalArgumentException("Name cannot be of 0 length");
        } 

        String[] names = trimmed.split("\\s+");
        for (String subName: names) {
            if (!isValidNameOrSurname(subName))
                throw new IllegalArgumentException("Invalid name");
        }

        return String.join(" ", names);
    }

    public static String verifySurname(String surname) {
        if (surname == null) {
            throw new NullPointerException("Surname cannot be null");
        }

        String trimmed = surname.trim();
        if (trimmed.length() == 0) {
            throw new IllegalArgumentException("Surname cannot be of 0 length");
        } 

        if (!isValidNameOrSurname(surname))
            throw new IllegalArgumentException("Invalid surname");

        return trimmed;
    }

    private static boolean isValidNameOrSurname(String str) {
        char[] charArr = str.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if (!Character.isLetter(charArr[i])) {
                return false;
            } 
            if (i == 0 && Character.isLowerCase(charArr[i]) || Character.isUpperCase(charArr[i])) {
                return false;
            }
        }
        return true;
    }
}
