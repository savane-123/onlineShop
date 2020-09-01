package com.savane.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.savane.R;

public class GoogleSingIn extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {

    LinearLayout profSession;
    Button logout;
    TextView email,userName;
    ImageView profileImage;
    SignInButton singIn;
    GoogleApiClient googleApiClient;
    private static final int REQ_CODE=9001;
    GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sing_in);
        logout = findViewById(R.id.logout);
        email = findViewById(R.id.email);
        userName = findViewById(R.id.userName);
        profileImage =  findViewById(R.id.profile_image);
        singIn = findViewById(R.id.sing_in);
        profSession=findViewById(R.id.prof_session);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        singIn.setOnClickListener(v -> {
            googleSingIn();
        });
        logout.setOnClickListener(v -> {
            googgleSingOut();
        });
    }


    private void googgleSingOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void googleSingIn() {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }
    public  void handelResult(GoogleSignInResult result){
        GoogleSignInAccount account = result.getSignInAccount();
        String stEmail = account.getEmail();
        String name = account.getDisplayName();
        account.isExpired();

//        String imageUrl = account.getPhotoUrl().toString();
        String token = account.getIdToken();
        email.setText(stEmail);
        userName.setText(name);
    //    Glide.with(this)
             //   .load(imageUrl)
              //  .into(profileImage);
        updateUI(true);

    }

    private void updateUI(boolean isLogin) {
        if (isLogin) {
            profSession.setVisibility(View.VISIBLE);
            singIn.setVisibility(View.GONE);
        }else {
            profSession.setVisibility(View.GONE);
            singIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handelResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();

    }
}