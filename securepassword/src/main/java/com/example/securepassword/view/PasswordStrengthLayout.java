package com.example.securepassword.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.securepassword.R;
import com.example.securepassword.manager.PasswordStrengthManager;

import java.util.ArrayList;

public class PasswordStrengthLayout extends LinearLayout {

    private EditText editTextPassword;
    private ProgressBar progressBarStrength;
    private PasswordStrengthManager passwordStrengthManager;

    public PasswordStrengthLayout(Context context) {
        super(context);
        init(context, null);
    }

    public PasswordStrengthLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PasswordStrengthLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.edit_text_password_strength, this);

        // Initialize views
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(context);
        editTextPassword = findViewById(R.id.editTextPassword);
        progressBarStrength = findViewById(R.id.progressBarStrength);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordStrengthLayout);
            String passwordHint = typedArray.getString(R.styleable.PasswordStrengthLayout_android_hint);
            boolean mustContainLowerCaseLetters = Boolean.parseBoolean(typedArray
                    .getString(R.styleable.PasswordStrengthLayout_mustContainLowerCaseLetters)),

                    mustContainUpperCaseLetters = Boolean.parseBoolean(typedArray
                            .getString(R.styleable.PasswordStrengthLayout_mustContainUpperCaseLetters)),

                    mustContainSpecialCharacters = Boolean.parseBoolean(typedArray
                            .getString(R.styleable.PasswordStrengthLayout_mustContainSpecialCharacters)),

                    mustContainDigits = Boolean.parseBoolean(typedArray
                            .getString(R.styleable.PasswordStrengthLayout_mustContainDigits));

            ArrayList<Boolean> mustContain = getMustContainAttrs(typedArray);

            if (passwordHint != null) {
                editTextPassword.setHint(passwordHint);
            }
            if (mustContainLowerCaseLetters) {
                passwordStrengthManager.setMustContainLowerCaseLetter(mustContainLowerCaseLetters);
            }
            if (mustContainUpperCaseLetters) {
                passwordStrengthManager.setMustContainUpperCaseLetter(mustContainUpperCaseLetters);
            }
            if (mustContainSpecialCharacters) {
                passwordStrengthManager.setMustContainSpecialCharacter(mustContainSpecialCharacters);
            }
            if (mustContainDigits) {
                passwordStrengthManager.setMustContainDigit(mustContainDigits);
            }

            typedArray.recycle();
        }
        addView(linearLayoutCompat);

        // Initialize PasswordStrengthMeter
        passwordStrengthManager = new PasswordStrengthManager();

        // Monitor text changes in password EditText
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                if(password.isEmpty()){
                    updatePasswordStrengthUI(-1);
                    return;
                }
                if(passwordStrengthManager.checkIfPasswordIsLegal(password)) {
                    updatePasswordStrengthUI(-1);
                    return;
                }
                int strengthScore = passwordStrengthManager.getStrengthScore(password);
                int maxStrengthScore = passwordStrengthManager.getMaxScore();
                int strengthLevel = 0;
                if (maxStrengthScore > 0) {
                    strengthLevel = (strengthScore * 100)/ maxStrengthScore ;
                }

                updatePasswordStrengthUI(strengthLevel);
                Log.d("TEST123", "afterTextChanged: maxStrengthScore = " + maxStrengthScore +
                        " strengthScore" + strengthScore);

            }
        });
    }

    private boolean getBooleanAttrs(String attr) {
        return Boolean.parseBoolean(attr);
    }

    private ArrayList<Boolean> getMustContainAttrs(TypedArray typedArray) {

        ArrayList<Boolean> mustContain = new ArrayList<>();
        mustContain.add(getBooleanAttrs(typedArray.getString(R.styleable.PasswordStrengthLayout_mustContainLowerCaseLetters)));
        mustContain.add(getBooleanAttrs(typedArray.getString(R.styleable.PasswordStrengthLayout_mustContainUpperCaseLetters)));
        mustContain.add(getBooleanAttrs(typedArray.getString(R.styleable.PasswordStrengthLayout_mustContainSpecialCharacters)));
        mustContain.add(getBooleanAttrs(typedArray.getString(R.styleable.PasswordStrengthLayout_mustContainDigits)));
        return mustContain;
    }


    private void updatePasswordStrengthUI(int strengthLevel) {
        // Update ProgressBar and visual indicator based on strengthLevel
        progressBarStrength.setProgress(strengthLevel);
        int progressBarStrengthColor = -1;
        if (strengthLevel > 80) {
            progressBarStrengthColor = 4;
        } else if (strengthLevel > 60) {
            progressBarStrengthColor = 3;
        } else if (strengthLevel > 40) {
            progressBarStrengthColor = 2;
        } else if (strengthLevel > 20) {
            progressBarStrengthColor = 1;
        } else if (strengthLevel > 0){
            progressBarStrengthColor = 0;
        }
        switch (progressBarStrengthColor) {
            case 0:
                // Very weak
                progressBarStrength.setProgressDrawable(getContext().getDrawable(R.drawable.progress_bar_red));
                break;
            case 1:
                // Weak
                progressBarStrength.setProgressDrawable(getContext().getDrawable(R.drawable.progress_bar_orange));
                break;
            case 2:
                // Moderate
                progressBarStrength.setProgressDrawable(getContext().getDrawable(R.drawable.progress_bar_yellow));
                break;
            case 3:
                // Strong
                progressBarStrength.setProgressDrawable(getContext().getDrawable(R.drawable.progress_bar_green));
                break;
            case 4:
                // Very strong
                progressBarStrength.setProgressDrawable(getContext().getDrawable(R.drawable.progress_bar_blue));
                break;
            default:
                progressBarStrength.setProgressDrawable(getContext().getDrawable(R.drawable.progress_bar_gray));
                break;
        }
    }

}
