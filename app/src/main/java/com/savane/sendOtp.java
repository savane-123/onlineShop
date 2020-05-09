package com.savane;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sendOtp extends AppCompatActivity {
   EditText sendOtp;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        sendOtp=findViewById(R.id.mail);
        email=findViewById(R.id.sendMail);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }
    private void sendMail(){
        String umail=sendOtp.getText().toString().trim();
        if(umail.isEmpty()){
            sendOtp.setError("Plese enter the email");
            sendOtp.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(umail).matches()){
            sendOtp.setError("Please enter the valid email");
            sendOtp.requestFocus();
        }
        Call<DefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .sendResetOtp(umail);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()==201){
                    DefaultResponse dr=response.body();
                    Toast.makeText(sendOtp.this,dr.getMsg(),Toast.LENGTH_LONG).show();
                    Intent i= new Intent(getApplicationContext(),conformPassword.class);
                    i.putExtra("Email",umail);
                    startActivity(i);
                }else {
                    Toast.makeText(sendOtp.this,"Http code "+response.code()+"message "+response.message(),Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(sendOtp.this,t.getMessage()+"unable to send otp",Toast.LENGTH_LONG).show();

            }
        });
    }
}
