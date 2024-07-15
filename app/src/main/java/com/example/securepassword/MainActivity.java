 package com.example.securepassword;

 import android.os.Bundle;

 import androidx.activity.EdgeToEdge;
 import androidx.appcompat.app.AppCompatActivity;


 public class MainActivity extends AppCompatActivity {

//    private EditText editTextPassword;
//    private ProgressBar progressBarStrength;
    //private PasswordStrengthMeterView passwordStrengthMeterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);

        findViews();
        initViews();
    }

    private void initViews() {

    }

    private void findViews() {

    }


}