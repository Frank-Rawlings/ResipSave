package com.resipsave.resipsave.resipsave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.resipsave.resipsave.R;
import com.wang.avi.AVLoadingIndicatorView;


public class Login extends AppCompatActivity {
    TextView loginBtn, email,password;
    ImageView googleBtn,facebookBtn;
    FrameLayout blur;
    AVLoadingIndicatorView login_loader;

    private static final String TAG = "AndroidClarified";
    private GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleBtn = findViewById(R.id.google_sign_btn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);


        login_loader = findViewById(R.id.login_loader);
        loginBtn = findViewById(R.id.sign_in_login);
        blur = findViewById(R.id.background_blur_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (email.getText().toString().equals("admin") &&
                            password.getText().toString().equals("admin")) {
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                        blur.setBackgroundResource(R.drawable.button_background);
                        login_loader.setVisibility(View.VISIBLE);
                        login_loader.show();
                    }
                }

        });


        googleBtn = findViewById(R.id.google_sign_btn);
        googleBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent signInIntent = googleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, 101);
                    }



//                login_loader.hide();
//                blur.setBackgroundResource(R.drawable.button_background);
//                login_loader.setVisibility(View.VISIBLE);
//                login_loader.show();
        });


        facebookBtn = findViewById(R.id.facebook_signin_btn);
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_loader.hide();
                blur.setBackgroundResource(R.drawable.button_background);
                login_loader.setVisibility(View.VISIBLE);
                login_loader.show();
            }
        });

    }

    public void openSignupPage(View view) {
        startActivity(new Intent(this,Register.class));
    }


    public void openLoginPage(View view) {

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.GOOGLE_ACCOUNT, googleSignInAccount);

        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }

}

