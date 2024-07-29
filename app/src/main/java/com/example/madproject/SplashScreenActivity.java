package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Simulate a loading process with a delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if the user is already logged in
                boolean isLoggedIn = checkLoginStatus();

                if (isLoggedIn) {

                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                // Close SplashScreenActivity
                finish();
            }
        }, 2000); // 2 second delay for the splash screen
    }

    private boolean checkLoginStatus() {
        // Replace with actual logic to check login status
        // For example, check shared preferences or a session manager
        return false;
    }
}
