package com.savane.activities.items;

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
import com.savane.activities.MainActivity;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemImage extends AppCompatActivity {
    private int ch;
    private Bitmap bitmap1,bitmap2,bitmap3,bitmap4;
    private TextView tvImageOne;
    private String KEY_IMAGE = "image";
    private String KEY_IMAGE_Tow = "image2";
    private String KEY_IMAGE_Three = "image3";
    private String KEY_IMAGE_Four = "image4";
    private String KEY_NAME = "savane123";
    private int PICK_IMAGE_REQUEST =1;
    private ImageView imageOne,imageTwo,imageThree,imageFour;
    private final String string="Preview  Photo";
    private String imgString,imgStringTow;
    private TextView btnUpload;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_image);
        imageOne=findViewById(R.id.img_one);
        imageTwo=findViewById(R.id.img_two);
        imageThree=findViewById(R.id.img_three);
        imageFour=findViewById(R.id.img_four);
        btnUpload=findViewById(R.id.uploadImg);
        intent=getIntent();

        imageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch=1;
                showFileChooser();
            }
        });

        imageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch=2;
                showFileChooser();
            }
        });
        imageThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch=3;
                showFileChooser();
            }
        });
        imageFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch=4;
                showFileChooser();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadItemImage();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            //Getting the Bitmap from Gallery
            switch (ch) {
                case 1: bitmap1 = getBitmap(filePath,imageOne);
                break;
                case 2: bitmap2 = getBitmap(filePath,imageTwo);
                break;
                case 3: bitmap3 = getBitmap(filePath,imageThree);
                    break;
                case 4: bitmap4 = getBitmap(filePath,imageFour);
                    break;
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
    private Bitmap getBitmap(Uri filePath ,ImageView imageView){
        Bitmap bitmap=null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
            //Setting the Bitmap to ImageView
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    private void uploadItemImage(){
        if (bitmap1==null){
            Toast.makeText(getApplicationContext(),"bitmap1 null",Toast.LENGTH_LONG).show();
            return;
        }else {
            int itemId=intent.getIntExtra("itemId",0);
            String imgOne=getStringImage(bitmap1);
            String imgTwo=getStringImage(bitmap2);
            String imgThree=getStringImage(bitmap3);
            String imgFour=getStringImage(bitmap4);
            Call<DefaultResponse> call=  RetrofitClient
                    .getInstance()
                    .getApi()
                    .testImages(imgOne,imgTwo,imgThree,imgFour,itemId);
            call.enqueue(new Callback<DefaultResponse>(){
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    DefaultResponse responseData=response.body();
                    if (response.code()==201){
                        Toast.makeText(getApplicationContext(),responseData.getMsg(),Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
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