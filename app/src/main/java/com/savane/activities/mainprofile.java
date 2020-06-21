package com.savane.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.savane.R;
import com.savane.storage.SharedPrefManager;

public class mainprofile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainprofile);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sandwishmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.logout:
               SharedPrefManager.getInstance(getApplicationContext()).clear();
               Toast.makeText(this,"logout", Toast.LENGTH_LONG).show();
               Intent intent=new Intent(mainprofile.this, MainActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
               return true;
           case R.id.logoutAndDelete:
               Toast.makeText(this,"delete and logout", Toast.LENGTH_LONG).show();
               Intent i= new Intent(getApplicationContext(), deleteUser.class);
               startActivity(i);
               return true;
       }
       return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_sell:
                fragment = new SellFragment();
                break;
            case R.id.nav_setting:
                fragment = new settingFragment();
                break;
        }
        if (fragment != null) {
            displayFragment(fragment);
        }
        return false;
    }

    public void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_contenair, fragment)
                .commit();
    }
}