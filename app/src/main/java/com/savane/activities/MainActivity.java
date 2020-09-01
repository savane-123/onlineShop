package com.savane.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.savane.R;
import com.savane.activities.fragments.HomeFragment;
import com.savane.activities.fragments.SellFragment;
import com.savane.activities.user.Login;
import com.savane.activities.user.UserProfile;
import com.savane.data.model.User;
import com.savane.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
     TextView myAd;
     View view;
     private ImageView profileImage,topCover;
     LinearLayout topLine;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        profileImage=findViewById(R.id.profile_image);
        topCover=findViewById(R.id.top_cover);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        topLine = findViewById(R.id.top_line);
        Fragment fragment = new HomeFragment();
        anim();
        displayFragment(fragment);
        //setting the title
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        toolbar.setTitle(user.getFirstName());
        int colorWhite= ContextCompat.getColor(getApplicationContext(),R.color.white);
        toolbar.setTitleTextColor(colorWhite);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sandwishmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuAbout:
                Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuLogout:
                SharedPrefManager.getInstance(getApplicationContext()).clear();
                Intent intent=new Intent(getApplicationContext(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);                break;
            case R.id.menuDelete:
                Toast.makeText(this, "You clicked delete", Toast.LENGTH_SHORT).show();


        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                topCover.setVisibility(View.VISIBLE);
                topLine.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_sell:
                fragment = new SellFragment();
                topCover.setVisibility(View.VISIBLE);
                topLine.setVisibility(View.VISIBLE);
                break;
            case R.id.profile:
                fragment = new UserProfile();
                topCover.setVisibility(View.GONE);
                topLine.setVisibility(View.GONE);
                break;
        }
        if (fragment != null) {
            displayFragment(fragment);
        }else if(fragment==null){
            fragment=new HomeFragment();
            displayFragment(fragment);
            topCover.setVisibility(View.VISIBLE);
            topLine.setVisibility(View.VISIBLE);
        }
        return false;
    }

    public void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_contenair, fragment)
                .commit();
    }
    public void anim(){
        Animation profileImageAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        profileImage.startAnimation(profileImageAnim);
        Animation lineTop = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        topLine.setAnimation(lineTop);
        Animation topCarveAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        topCover.setAnimation(topCarveAnim);
        Animation bnv = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down_top);
        bottomNavigationView.startAnimation(bnv);

    }
}