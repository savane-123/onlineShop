package com.savane.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;
import com.savane.data.model.User;
import com.savane.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class deleteUser extends AppCompatActivity {
    Intent intent;
    TextView email,sendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        intent=getIntent();
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
       User user= SharedPrefManager.getInstance(getApplication()).getUser();
        Call<DefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .deleteUser(user.getEmail());
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()==201){
                    DefaultResponse dr=response.body();
                    Toast.makeText(com.savane.activities.deleteUser.this,dr.getMsg(),Toast.LENGTH_LONG).show();
                    Intent i= new Intent(getApplicationContext(), sign_up.class);
                    startActivity(i);
                    SharedPrefManager.getInstance(getApplicationContext()).clear();
                }else {
                    Toast.makeText(deleteUser.this,"Http code "+user.getEmail()+"message "+response.message(),Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(deleteUser.this,t.getMessage()+"unable to send otp",Toast.LENGTH_LONG).show();

            }
        });
    }
}
