package com.example.securepassword.manager;

// PasswordStrengthMeterView.java

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.securepassword.R;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Attr;

/**
 * Custom view to display and evaluate password strength.
 */
public class PasswordStrengthMeterView extends TextInputEditText {

    private PasswordStrengthManager passwordStrengthManager;
    private EditText editTextPassword;
    private ProgressBar progressBarStrength;

    public PasswordStrengthMeterView(Context context) {
        super(context);
        init(context, null);
    }

    public PasswordStrengthMeterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PasswordStrengthMeterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordStrengthLayout);
            String passwordHint = typedArray.getString(R.styleable.PasswordStrengthLayout_android_hint);

            if (passwordHint != null) {
                setHint(passwordHint);
            }

            typedArray.recycle();
        }

        passwordStrengthManager = new PasswordStrengthManager();

        editTextPassword = findViewById(R.id.editTextPassword);
        progressBarStrength = findViewById(R.id.progressBarStrength);

        // Listen for text changes in EditText to update password strength
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = editable.toString();
                int strengthScore = passwordStrengthManager.getStrengthScore(password);
                int maxStrengthScore = passwordStrengthManager.getMaxScore();
                int strengthLevel = strengthScore / maxStrengthScore;
                updatePasswordStrengthUI(strengthLevel);
            }
        });
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
        } else {
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
