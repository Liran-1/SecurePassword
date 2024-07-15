package com.example.securepassword.manager;

import android.util.Log;

import com.example.securepassword.utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordStrengthManager {

    public PasswordStrengthManager() {
    }

    protected boolean mustContainUpperCaseLetter = true, mustContainLowerCaseLetter = true,
            mustContainDigit = false, mustContainSpecialCharacters = false;


    protected int score = 0, maxScore = 0, entropy = 0;

    ///////////////////START GETTERS AND SETTERS///////////////////
    public boolean isMustContainUpperCaseLetter() {
        return mustContainUpperCaseLetter;
    }

    public void setMustContainUpperCaseLetter(boolean mustContainUpperCaseLetter) {
        this.mustContainUpperCaseLetter = mustContainUpperCaseLetter;
    }

    public boolean isMustContainLowerCaseLetter() {
        return mustContainLowerCaseLetter;
    }

    public void setMustContainLowerCaseLetter(boolean mustContainLowerCaseLetter) {
        this.mustContainLowerCaseLetter = mustContainLowerCaseLetter;
    }

    public boolean isMustContainDigit() {
        return mustContainDigit;
    }

    public void setMustContainDigit(boolean mustContainDigit) {
        this.mustContainDigit = mustContainDigit;
    }

    public boolean isMustContainSpecialCharacters() {
        return mustContainSpecialCharacters;
    }

    public void setMustContainSpecialCharacter(boolean mustContainSpecialCharacters) {
        this.mustContainSpecialCharacters = mustContainSpecialCharacters;
    }

    public int getStrengthScore(String password) {
        calcScore(password);
        return score;
    }

    public int getMaxScore() {
        calcMaxScore();
        return maxScore;
    }

    ///////////////////END GETTERS AND SETTERS///////////////////

    ///////////////////START SCORE CALCULATION///////////////////


    private void calcScore(String usersPassword) {
        score = 0;
        int passwordLength = usersPassword.length();
        if(passwordLength < Constants.MINIMUM_LENGTH){
            return;
        }

        calculateEntropy(usersPassword);
//        if(entropy < Constants.MINIMUM_ENTROPY){
//            score = 0;
//            return;
//        }

        int pointsForPattern = 1, pointsForLength = 2, pointsForEntropy = 4;

        calcScoreByPattern(mustContainLowerCaseLetter, Constants.REGEX_SMALL_LETTERS, usersPassword, pointsForPattern);
        calcScoreByPattern(mustContainUpperCaseLetter, Constants.REGEX_LARGE_LETTERS, usersPassword, pointsForPattern);
        calcScoreByPattern(mustContainDigit, Constants.REGEX_NUMBERS, usersPassword, pointsForPattern);
        calcScoreByPattern(mustContainSpecialCharacters, Constants.REGEX_WHITELISTED_SPECIAL_CHARACTERS, usersPassword, pointsForPattern);

        calcScoreByLength(passwordLength, pointsForLength);

        calcScoreByConsecutiveChars(usersPassword, passwordLength);

        calcScoreByEntropy(pointsForEntropy);

    }

    private void calcScoreByPattern(boolean mustContainPattern, String patternRegex, String usersPassword, int pointsForPattern) {
        if (mustContainPattern) {
            getScoreByPattern(patternRegex, usersPassword, pointsForPattern);
        }
    }

    private void getScoreByPattern(String patternRegex, String password, int points) {
        Pattern pattern = Pattern.compile(patternRegex);
        Matcher matcher = pattern.matcher(password);
        boolean matchFound = matcher.matches();

        if (matchFound) {
            Log.d("TEST12345", "getScoreByPattern: points:" + points + " " + score);
            score += points;
        }
        maxScore += points;
    }

    private void calcScoreByLength(int passwordLength, int pointsForLength) {
        if (passwordLength > Constants.SAFE_LENGTH) {
            score += pointsForLength;
        } else if (passwordLength > Constants.RECOMMENDED_LENGTH) {
            score += pointsForLength - 1;
        }
    }

    private void calculateEntropy(String usersPassword) {
        int passwordLength = usersPassword.length();
        int possibleCharacters = 0;
        if (mustContainUpperCaseLetter) {
            possibleCharacters += Constants.AMOUNT_OF_UPPER_CASE_LETTERS;
        }
        if (mustContainLowerCaseLetter) {
            possibleCharacters += Constants.AMOUNT_OF_LOWER_CASE_LETTERS;
        }
        if (mustContainDigit) {
            possibleCharacters += Constants.AMOUNT_OF_DIGITS;
        }
        if (mustContainSpecialCharacters) {
            possibleCharacters += Constants.AMOUNT_OF_SPECIAL_CHARACTERS;
        }

        int numberOfPossibleCombinations = (int) Math.pow(possibleCharacters, passwordLength);
        entropy = calculateLog2(numberOfPossibleCombinations);

    }

    private void calcScoreByEntropy(int points) {
        if (entropy > Constants.STRONG_ENTROPY) {
            score += points;
        } else if (entropy > Constants.MEDIUM_ENTROPY) {
            score += (points - 1);
        } else if (entropy > Constants.WEAK_ENTROPY) {
            score += (points - 2);
        }
    }

    private void calcScoreByConsecutiveChars(String usersPassword, int passwordLength) {
        int consecutiveCounter = 0;
        for (int i = 0; i < passwordLength - 1; i++) {
            int charDiff = usersPassword.charAt(i) - usersPassword.charAt(i + 1);
            if (charDiff == 0 || charDiff == 1) {
                consecutiveCounter++;
            } else if (consecutiveCounter > 0) {
                consecutiveCounter--;
            }
            if (consecutiveCounter == 2 && score > 0) {
                Log.d("TEST1234", "calcScoreByConsecutiveChars: lost point " +
                        usersPassword.charAt(i) + " " + usersPassword.charAt(i + 1));
                score--;
                consecutiveCounter = 0;
            }
        }
    }

    private int calculateLog2(int number) {
        return (int) (Math.log(number) / Math.log(2));
    }

    ///////////////////END PASSWORD SCORE CALCULATION///////////////////

    ///////////////////START PASSWORD MAX SCORE CALCULATION///////////////////

    public void calcMaxScore() {
        maxScore = Constants.POINTS_FOR_LENGTH + Constants.POINTS_FOR_ENTROPY;
        calcMaxScoreByPattern(mustContainUpperCaseLetter);
        calcMaxScoreByPattern(mustContainDigit);
        calcMaxScoreByPattern(mustContainSpecialCharacters);
    }

    private void calcMaxScoreByPattern(boolean mustContainPattern) {
        if (mustContainPattern) {
            maxScore++;
        }
    }

    ///////////////////END PASSWORD MAX SCORE CALCULATION///////////////////

    ///////////////////START FUNCTIONS///////////////////

    public boolean checkIfPasswordIsLegal(String password) {
            Pattern pattern = Pattern.compile(Constants.REGEX_BLACKLIST_CHARACTERS);
            Matcher matcher = pattern.matcher(password);
            boolean matchFound = matcher.matches();
            return matchFound ? true : false;
    }




    ///////////////////END FUNCTIONS///////////////////


}
