package com.savane.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sendOtp extends AppCompatActivity {
   EditText enterMail;
    Button sendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        enterMail=findViewById(R.id.mail);
        sendEmail=findViewById(R.id.send_mail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }
    private void sendMail(){
        String umail=enterMail.getText().toString().trim();
        if(umail.isEmpty()){
            enterMail.setError("Plese enter the email");
            enterMail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(umail).matches()){
            enterMail.setError("Please enter the valid email");
            enterMail.requestFocus();
            return;
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
                    Intent i= new Intent(getApplicationContext(), ConformPassword.class);
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
