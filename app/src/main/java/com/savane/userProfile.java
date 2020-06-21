package com.savane;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.api.retrofitclit3;
import com.savane.data.model.User;
import com.savane.data.model.UserResponse;
import com.savane.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userProfile extends AppCompatActivity {
    Button button;
    ImageView imageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        imageIV=findViewById(R.id.imageIV);
        button = findViewById(R.id.bntgetImage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

    }

    private void getImage() {
        User user= SharedPrefManager.getInstance(getApplication()).getUser();
        Call<UserResponse> call = retrofitclit3
                .getInstance()
                .getApi()
                .getAllImages(user.getId().toString());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse > call, Response<UserResponse > response) {

             //   try{
                   UserResponse ur  =response.body();
                    String image=ur.getImage();
                Toast.makeText(getApplicationContext(),image , Toast.LENGTH_LONG).show();
                   if(ur.isError()) {

                       Picasso.get()
                               .load(image)
                               .resize(50, 50)
                               .centerCrop().into(imageIV);

                       //}else{
                       //
                       // }
                       // }catch(Exception e){
                       //  Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();


                   }



            }

            @Override
            public void onFailure(Call<UserResponse > call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage()+"hfhfh",Toast.LENGTH_LONG).show();

            }
        });
    }
}