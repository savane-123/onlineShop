package com.savane.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.savane.data.model.Address;
import com.savane.data.model.User;

public class SharedPrefManager {
    private String SHARED_PREF_MANAGER = "my_shared_manager";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_MANAGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("firstName", user.getFirstName());
        editor.putString("lastName", user.getLastName());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putString("dateOfBirth", user.getDateOfBirth());
        editor.putString("gender", user.getGender());
        editor.putString("AddressId", user.getAddressId());
        editor.apply();
    }

    public boolean isLogIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_MANAGER, Context.MODE_PRIVATE);
        return (sharedPreferences.getInt("id", -1)) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_MANAGER, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("firstName", null),
                sharedPreferences.getString("lastName", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("dateOfBirth", null),
                sharedPreferences.getString("gender", null),
                sharedPreferences.getString("AddressId", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_MANAGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void saveAddress(Address add) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_MANAGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("addressId", add.getAddressId());
        editor.putString("userFullAddress", add.getUserFullAddress());
        editor.putString("userCity", add.getUserCity());
        editor.putString("userState", add.getUserState());
        editor.putString("userCountry", add.getUserCountry());
        editor.putInt("userId", add.getUserId());
        editor.apply();
    }

    public Address getAddress() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_MANAGER, Context.MODE_PRIVATE);
        return new Address(
                sharedPreferences.getInt("addressId", -1),
                sharedPreferences.getString("userFullAddress", null),
                sharedPreferences.getString("userCity", null),
                sharedPreferences.getString("userState", null),
                sharedPreferences.getString("userCountry", null),
                sharedPreferences.getInt("userId", -1)
        );
    }
}