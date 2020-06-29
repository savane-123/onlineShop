package com.savane;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.api.RetrofitClient;
import com.savane.data.model.Address;
import com.savane.data.model.Image;
import com.savane.data.model.User;
import com.savane.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class userProfile extends AppCompatActivity {
    Button button;
    ImageView imageIV;
    TextView fn,ln,ph,gend,add,dob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        imageIV=findViewById(R.id.imageIV);
        fn=findViewById(R.id.fName);
        ln=findViewById(R.id.lName);
        ph=findViewById(R.id.phone);
        gend=findViewById(R.id.gender);
        add=findViewById(R.id.address);
        dob=findViewById(R.id.dateOfBirth);
        button = findViewById(R.id.bntgetImage);


        User userDetails= SharedPrefManager.getInstance(getApplication()).getUser();
        Address addressDetails=SharedPrefManager.getInstance(getApplication()).getAddress();

         fn.setText(userDetails.getFirstName());
         ln.setText(userDetails.getLastName());
         ph.setText(userDetails.getPhone());
        gend.setText(userDetails.getGender());
        dob.setText(userDetails.getDateOfBirth());

        Call<Image> call = RetrofitClient
                .getInstance()
                .getApi()
                .getProfileImage(userDetails.getId());
        call.enqueue(new Callback<Image>(){
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if (response.isSuccessful()){
                    Image image = response.body();
                    Picasso.get()
                            .load(Constant.UPLOAD_FOLDER+image.getProfile())
                            .into(imageIV);
                    Toast.makeText(getApplicationContext(),userDetails.getId().toString(),Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"code is:"+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
        Toast.makeText(getApplicationContext(),userDetails.getId().toString()+"before call",Toast.LENGTH_LONG).show();
        Call<Address> call1= RetrofitClient
                        .getInstance().getApi()
                        .getAddressByUserId(userDetails.getId());

       call1.enqueue(new Callback<Address>() {
           @Override
           public void onResponse(Call<Address> call1, Response<Address> response) {
               if(response.code()==201){
                   Address dr=response.body();
                   add.setText(dr.getUserFullAddress());
                   Toast.makeText(userProfile.this,dr.getUserFullAddress(),Toast.LENGTH_LONG).show();
               }
               else {
                   Toast.makeText(userProfile.this,"Http code "+response.code()+"message "+response.message(),Toast.LENGTH_LONG).show();
               }

           }

           @Override
           public void onFailure(Call<Address> call1, Throwable t) {
               Toast.makeText(userProfile.this,t.getMessage()+"unable to create user",Toast.LENGTH_LONG).show();
           }
       });
    }

}