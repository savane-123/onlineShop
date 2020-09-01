package com.savane.activities.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;
import com.savane.data.model.User;
import com.savane.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePassword extends AppCompatActivity {
    private EditText password,newPassword,conformPassword;
    private Button change;
    private TextView errorMessage,successMessage;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        password = findViewById(R.id.password);
        newPassword = findViewById(R.id.new_password);
        conformPassword = findViewById(R.id.conform_password);
        change = findViewById(R.id.change);
        errorMessage=findViewById(R.id.error_msg);
        successMessage=findViewById(R.id.success_msg);
         user = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        change.setOnClickListener(v -> {

            changePassword(user.getEmail());
        });
    }

    private void changePassword(String email) {
        String stPassword=password.getText().toString().trim();
        String stNewPassword=newPassword.getText().toString().trim();
        String stConformPassword=conformPassword.getText().toString().trim();
        if (stPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (stNewPassword.isEmpty()){
            newPassword.setError("New password is required");
            newPassword.requestFocus();
            return;
        }
        if (stConformPassword.isEmpty()){
            conformPassword.setText("conform password is required");
            conformPassword.requestFocus();
            return;
        }
        if (!stNewPassword.equals(stConformPassword)){
            newPassword.setError("passwords are not matching");
            newPassword.requestFocus();
            conformPassword.setText("passwords are not matching");
            conformPassword.requestFocus();
            return;
        }
        Call<DefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .updatePassword(email,stPassword,stNewPassword);
        call.enqueue(new Callback<DefaultResponse>(){
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code()==201){
                    DefaultResponse dr=response.body();
                    errorMessage.setVisibility(View.GONE);
                    successMessage.setVisibility(View.VISIBLE);
                }else {
                    successMessage.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                successMessage.setVisibility(View.GONE);
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}