package com.resipsave.resipsave.resipsave;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.resipsave.resipsave.R;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openLoginPage(View view) {
        startActivity(new Intent(this, Login.class));
    }

    public void openSignupPage(View view) {
        startActivity(new Intent(this,Register.class));
    }
}
