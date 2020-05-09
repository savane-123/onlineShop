package com.savane;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.activities.sign_up;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class conformPassword extends AppCompatActivity {
      TextView conform,creatAccount,email;
      EditText pass,confPass,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_password);
         conform=findViewById(R.id.comform);
         email=findViewById(R.id.email);
         pass=findViewById(R.id.password);
         confPass=findViewById(R.id.confpassword);
         creatAccount=findViewById(R.id.createAccount);
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
           creatAccount.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent i= new Intent(getApplicationContext(),sign_up.class);
                   startActivity(i);
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
                  .updatePassword(email1,conotp,password,conformpassword);
          call.enqueue(new Callback<DefaultResponse>(){
              @Override
              public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                  if(response.code()==201){
                      DefaultResponse dr=response.body();
                      Toast.makeText(conformPassword.this,dr.getMsg(),Toast.LENGTH_LONG).show();
                  }else {
                      Toast.makeText(conformPassword.this,"Http code "+response.code()+"message "+response.message(),Toast.LENGTH_LONG).show();
                  }

              }
              @Override
              public void onFailure(Call<DefaultResponse> call, Throwable t) {
                  Toast.makeText(conformPassword.this,t.getMessage()+"unable to update user",Toast.LENGTH_LONG).show();

              }
          });
          //Intent i= new Intent(getApplicationContext(),conformPassword.class);
          //startActivity(i);
      }
      }

