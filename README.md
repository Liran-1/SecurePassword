# PasswordStrengthLayout

PasswordStrengthLayout is a custom view provided by the SecurePassword library, designed to visually represent the strength of a password using an EditText and a ProgressBar.

## Features

- **Password Length**: Checks the length of the password.
- **Character Composition**: Analyzes which character types (e.g., lowercase, uppercase, digits, symbols) are present.
- **Entropy Calculation**: Measures the randomness or unpredictability of the password.
- **Consecutive Letters**: Detects sequences of consecutive letters.

## Usage

To integrate PasswordStrengthLayout into your project, follow these steps:

1. **Include SecurePassword library**: Ensure that SecurePassword library is added to your project dependencies.

2. **Configure PasswordStrengthManager**:

   The `PasswordStrengthManager` class allows you to set criteria for password strength and provides methods to check password legality and retrieve strength scores.

   ```java
   PasswordStrengthManager passwordStrengthManager = new PasswordStrengthManager();
   
   // Set criteria for password strength
   passwordStrengthManager.setMustContainUpperCaseLetter(true);
   passwordStrengthManager.setMustContainLowerCaseLetter(true);
   passwordStrengthManager.setMustContainDigit(true);
   passwordStrengthManager.setMustContainSpecialCharacter(true);
   
   // Get strength score for a password
   int strengthScore = passwordStrengthManager.getStrengthScore("password123!@#");
   
   // Get maximum possible score
   int maxScore = passwordStrengthManager.getMaxScore();
   
   // Check if a password meets legal requirements
   boolean isLegal = passwordStrengthManager.checkIfPasswordIsLegal("password123!@#");

3. **Add PasswordStrengthLayout to your layout XML**

   ```XML
   <com.example.securepassword.view.PasswordStrengthLayout
      android:id="@+id/passwordStrengthLayout"
      android:layout_width="240dp"
      android:layout_height="wrap_content"
      android:hint="Enter password" />

## Strength Levels

The strength of the password is visually represented using the ProgressBar with the following color scheme:

- **Grey**: Initial state
- **Red**: Very Weak
- **Orange**: Weak
- **Yellow**: Moderate
- **Green**: Strong
- **Blue**: Very Strong

## Known Issues

- The ProgressBar does not reach 100%.
- Password scoring algorithm needs improvement for more accurate strength assessment.

## Contributing

Contributions to PasswordStrengthLayout are welcome! If you find any issues or have suggestions for improvements, please submit a pull request or open an issue on GitHub.

## License

SecurePassword library and PasswordStrengthLayout are licensed under the Apache License, Version 2.0. See the LICENSE file for more details.

## Authors
- [Liran](Github.com/Liran-1)
