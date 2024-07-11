package com.example.securepassword.manager;

import com.example.securepassword.utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordManager {

    protected boolean mustContainLargeLetter = false, mustContainSmallLetter = false,
            mustContainNumber = false, mustContainSpecialCharacters = false;


    protected int score = 0, entropy = 0;

    private void calcScore(String usersPassword) {

        Pattern pLargeLetters = Pattern.compile(Constants.REGEX_LARGE_LETTERS);
        Matcher mLargeLetters = pLargeLetters.matcher(usersPassword);
        boolean bLargeLetters = mLargeLetters.matches();

        if(bLargeLetters){
            score++;
        }

        Pattern pSmallLetters = Pattern.compile(Constants.REGEX_SMALL_LETTERS);
        Matcher mSmallLetters = pSmallLetters.matcher(usersPassword);
        boolean bSmallLetters = mSmallLetters.matches();

        if(bSmallLetters){
            score++;
        }

        Pattern pNumbers = Pattern.compile(Constants.REGEX_NUMBERS);
        Matcher mNumbers = pNumbers.matcher(usersPassword);
        boolean bNumbers = mNumbers.matches();

        if(bNumbers){
            score++;
        }

        Pattern pSpecialCharacters = Pattern.compile(Constants.REGEX_WHITELISTED_SPECIAL_CHARACTERS);
        Matcher mSpecialCharacters = pSpecialCharacters.matcher(usersPassword);
        boolean bSpecialCharacters = mSpecialCharacters.matches();
        if(bSpecialCharacters){
            score++;
        }


        int passwordLength = usersPassword.length();

        if(passwordLength == Constants.MAX_LENGTH){
            score += 2;
        }
        else if(passwordLength > Constants.RECOMMENDED_LENGTH){
            score++;
        }

        for (int i = 0; i < passwordLength; i++) {

        }

    }

}
