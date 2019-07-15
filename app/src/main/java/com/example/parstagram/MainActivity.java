package com.example.parstagram;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signUpButton;
    private ParseUser currentUser;
    AnimationDrawable animationDrawable;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            switchToHomeActivity();
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            setContentView(R.layout.activity_login);
            usernameEditText = findViewById(R.id.username);
            passwordEditText = findViewById(R.id.password);
            loginButton = findViewById(R.id.login);
            signUpButton = findViewById(R.id.signUp);

            constraintLayout = findViewById(R.id.container);
            animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(10);
            animationDrawable.setExitFadeDuration(5000);
            animationDrawable.start();


            logInListener();
            signUpListener();
        }

    }

    private void logInListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                login(username, password);
            }
        });
    }

    private void signUpListener() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_signup);


                constraintLayout = findViewById(R.id.ConstraintLayout);
                animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
                animationDrawable.setEnterFadeDuration(10);
                animationDrawable.setExitFadeDuration(5000);
                animationDrawable.start();

                final TextView tvUsername = findViewById(R.id.tvUsername);
                final TextView tvEmail = findViewById(R.id.tvEmail);
                final TextView tvPassword1 = findViewById(R.id.password1);
                final TextView tvPassword2 = findViewById(R.id.password2);
                Button signUpBtn = findViewById(R.id.signUpBtn);

                signUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String username = tvUsername.getText().toString();
                        final String email = tvEmail.getText().toString();
                        final String password1 = tvPassword1.getText().toString();
                        final String password2 = tvPassword2.getText().toString();

                        signUp(username,password1,password2,email);
                    }
                });
            }
        });
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null) {
                    Log.d("LoginActivity","Login Successful!");
                    switchToHomeActivity();
                } else {
                    Log.d("LoginActivity","Login Failure!");
                    e.printStackTrace();
                }
            }
        });
    }

    private void switchToHomeActivity() {
        final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void signUp(String username, String password1, String password2, String email) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties

        user.setUsername(username);

        if (!isValidPassword(password1)) { // check if password doesn't meet requirements
            Toast.makeText(this.getApplicationContext(),"password don't meet requirements",
                    Toast.LENGTH_SHORT);
        } else if (!password1.equals(password2)) { // check if passwords don't match
            Toast.makeText(this.getApplicationContext(),"passwords don't match",
                    Toast.LENGTH_SHORT);
        } else {
            user.setPassword(password1);
        }

        if (isValidEmail(email)) {
            user.setEmail(email);
        } else {
            Toast.makeText(this.getApplicationContext(),"invalid email",
                    Toast.LENGTH_SHORT);
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password1);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.d("SignUpActivity","SignUp Successful!");
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.d("SignUpActivity","SignUp Failure!");
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean isValidPassword(String password) {
        return (password.length() >= 8) && // password is at least 8 characters long
                (password.matches(getString(R.string.password_regex))); //password matches Regex
    }

    private boolean isValidEmail(String email) {
        return (email.matches(getString(R.string.email_regex))); //check email against Regex
    }



}
