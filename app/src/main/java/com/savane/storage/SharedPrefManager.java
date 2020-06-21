package com.savane.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.savane.data.model.User;

public class SharedPrefManager {
    private String SHARED_PREF_MANAGER="my_shared_manager";
    private static  SharedPrefManager mInstance;
    private Context mCtx;

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }
    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if (mInstance==null){
            mInstance=new SharedPrefManager(mCtx);
        }
        return mInstance;
    }
    public void saveUser(User user){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_MANAGER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("id",user.getId());
        editor.putString("First name",user.getFirstName());
        editor.putString("Last name",user.getLastName());
        editor.putString("phone",user.getPhone());
        editor.putString("Date of birth",user.getDateOfBirth());
        editor.putString("gender",user.getGender());
        editor.putString("email",user.getEmail());
        editor.apply();
    }
    public boolean isLogIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_MANAGER,Context.MODE_PRIVATE);
        return (sharedPreferences.getInt("id",-1))!=-1;
    }
    public User getUser(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_MANAGER,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("first name",null),
                sharedPreferences.getString("last name",null),
                sharedPreferences.getString("phone",null),
                sharedPreferences.getString("Date of birth",null),
                sharedPreferences.getString("gender",null),
                sharedPreferences.getString("email",null)
        );
    }

    public void clear(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_MANAGER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
