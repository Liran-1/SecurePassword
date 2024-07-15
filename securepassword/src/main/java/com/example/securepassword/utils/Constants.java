package com.example.securepassword.utils;

public class Constants {

    public static final int AMOUNT_OF_UPPER_CASE_LETTERS = 26;
    public static final int AMOUNT_OF_LOWER_CASE_LETTERS = 26;
    public static final int AMOUNT_OF_DIGITS = 10;
    public static final int AMOUNT_OF_SPECIAL_CHARACTERS = 18;

    public static final int MINIMUM_LENGTH = 8;
    public static final int RECOMMENDED_LENGTH = 12;
    public static final int SAFE_LENGTH = 16;

    public static final String REGEX_LARGE_LETTERS = ".*[A-Z].*";
    public static final String REGEX_SMALL_LETTERS = ".*[a-z].*";
    public static final String REGEX_NUMBERS = ".*\\d.*";
    public static final String REGEX_WHITELISTED_SPECIAL_CHARACTERS = "^[!@#$%^&*()]*$";
    public static final String REGEX_BLACKLIST_CHARACTERS = "^[^A-Za-z0-9!@#$%^&*()]*$";

    public static final double MINIMUM_ENTROPY = 37.60;
    public static final double WEAK_ENTROPY = 50.57;
    public static final double MEDIUM_ENTROPY = 80.15;
    public static final double STRONG_ENTROPY = 110.57;

    public static final int POINTS_FOR_ENTROPY = 4;
    public static final int POINTS_FOR_LENGTH = 2;
}
