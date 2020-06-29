package com.savane.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;
import com.savane.data.model.User;
import com.savane.storage.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class uploadImage extends AppCompatActivity {
    private int ch;
    private Bitmap bitmap1;
    private TextView tvImageOne;
    private String KEY_IMAGE = "image";
    private String KEY_IMAGE_Tow = "image2";
    private String KEY_NAME = "savane123";
    private int PICK_IMAGE_REQUEST =1;
    private ImageView imageOne;
    private final String string="Preview  Photo";
    private String imgString,imgStringTow;
    private TextView btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

            imageOne=findViewById(R.id.tv_img);
            tvImageOne=findViewById(R.id.img_one);
            btnUpload=findViewById(R.id.btn_upload);

            tvImageOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ch=1;
                    showFileChooser();

                }
            });

            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadProfileImage();
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
                Uri filePath = data.getData();
                //Getting the Bitmap from Gallery
                if (ch == 1) {
                    bitmap1 = getBitmap(filePath,imageOne,tvImageOne);
                }
               // bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);

                //Setting the Bitmap to ImageView
               // imageOne.setImageBitmap(bitmap);
            }
        }
        private void showFileChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        public String getStringImage(Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[]imageBytes = baos.toByteArray();
            String encodedImage  = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
        private Bitmap getBitmap(Uri filePath ,ImageView imageView,TextView textView){
            Bitmap bitmap=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
                textView.setText(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        private void uploadProfileImage(){
            if (bitmap1==null){
                Toast.makeText(getApplicationContext(),"bitmap1 null",Toast.LENGTH_LONG).show();
                return;
            }else {
                User user= SharedPrefManager.getInstance(getApplication()).getUser();
                String imgOne=getStringImage(bitmap1);
                Call<DefaultResponse> call=  RetrofitClient
                        .getInstance()
                        .getApi()
                        .addImage(imgOne,user.getId().toString());
                call.enqueue(new Callback<DefaultResponse>(){
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse responseData=response.body();
                        if (response.code()==201){
                            Toast.makeText(getApplicationContext(),responseData.getMsg(),Toast.LENGTH_LONG).show();
                        }else if(response.code()==404) {
                            Toast.makeText(getApplicationContext(),"error1 "+responseData.getMsg(),Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"error 2 "+response.code(),Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                          Toast.makeText(getApplicationContext(),"anable to insert"+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

