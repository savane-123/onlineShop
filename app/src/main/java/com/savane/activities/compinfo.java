package com.savane.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;


public class compinfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_compinfo);
        //loader image - will be shown befor loading image
       //int loader = R.drawable.loader;
       ImageView image =(ImageView)findViewById(R.id.img);
     //ImageLoader imgLoader = new ImageLoader(getApplicationContext());
     String img_url="https://api.androidhive.info/sample.jpg";
    }
}
