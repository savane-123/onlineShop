package com.savane.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;
import com.savane.api.LoginResponse;
import com.savane.api.RetrofitClient;
import com.savane.sendOtp;
import com.savane.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
  EditText editEmail,edidPassword;
  TextView buttonLogin,buttonSigin,forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmail=findViewById(R.id.text1);
        edidPassword=findViewById(R.id.text2);
        buttonLogin=findViewById(R.id.login);
        buttonSigin=findViewById(R.id.sigin);
        forgotPassword=findViewById(R.id.forgotPassword);
       // buttonBack=findViewById(R.id.back);

        buttonSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),sign_up.class);
                startActivity(i);
            }
        });

       forgotPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(), sendOtp.class);
               startActivity(i);
           }
       });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 {
                    login();
                 }
            }
        });
    }

    private void login() {
        String email = editEmail.getText().toString().trim();
        String password = edidPassword.getText().toString().trim();
        if (email.isEmpty()) {
            editEmail.setError("email is required");
            editEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("The correct email is required");
            editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edidPassword.setError("The password is required");
            edidPassword.requestFocus();
            return;
        }
        if (password.length() < 4) {
            edidPassword.setError("The password lenght should be more than 3 character");
            edidPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse lr=response.body();
                if(!lr.isError()) {
                   Toast.makeText(MainActivity.this, lr.getMsg(), Toast.LENGTH_LONG).show();
                    SharedPrefManager.getInstance(MainActivity.this)
                            .saveUser(lr.getUser());
                    Intent intent=new Intent(MainActivity.this,mainprofile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    SharedPrefManager.getInstance(MainActivity.this).isLogIn();
                }
                 else {
                    Toast.makeText(MainActivity.this,lr.getMsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
    protected void onStart() {    super.onStart();
        Log.i("df",String.valueOf(SharedPrefManager.getInstance(this).isLogIn()));
        if(SharedPrefManager.getInstance(this).isLogIn())
        {
            Intent intent=new Intent(getApplicationContext(), mainprofile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(intent);}}
}
