package com.savane.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;
import com.savane.activities.MainActivity;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConformPassword extends AppCompatActivity {
      TextView email;
      EditText pass,confPass,otp;
      Button conform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_password);
        confPass=findViewById(R.id.conform);
         email=findViewById(R.id.email);
         pass=findViewById(R.id.password);
        conform=findViewById(R.id.confpassword);
         otp=findViewById(R.id.otp);
        Intent intent=getIntent();
        String emai=intent.getStringExtra("Email");
           email.setText(emai);
        conform.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                   confpassword();
             }
         });

    }
      private void confpassword(){
          Intent intent=getIntent();
          String email1=intent.getStringExtra("Email");
          email.setText(email1);
          String conotp=otp.getText().toString().trim();
          String password=pass.getText().toString().trim();
          String conformpassword=confPass.getText().toString().trim();
          if (conotp.isEmpty()){
              otp.setError("The OTP is required");
              otp.requestFocus();
              return;
          }
          if (password.isEmpty()){
              pass.setError("New password is required");
              pass.requestFocus();
              return;
          }
          if (conformpassword.isEmpty()){
              confPass.setError("Pleas comform the password");
              confPass.requestFocus();
              return;
          }
          Call<DefaultResponse> call= RetrofitClient
                  .getInstance()
                  .getApi()
                  .conformPassword(email1,conotp,password,conformpassword);
          call.enqueue(new Callback<DefaultResponse>(){
              @Override
              public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                  if(response.code()==201){
                      DefaultResponse dr=response.body();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                  }else {
                      Toast.makeText(ConformPassword.this,"Http code "+response.code()+"message "+response.message(),Toast.LENGTH_LONG).show();
                  }

              }
              @Override
              public void onFailure(Call<DefaultResponse> call, Throwable t) {
                  Toast.makeText(ConformPassword.this,t.getMessage()+"unable to update user",Toast.LENGTH_LONG).show();

              }
          });
          //Intent i= new Intent(getApplicationContext(),conformPassword.class);
          //startActivity(i);
      }
      }

