package com.savane.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient1 {
    private static final String BASED_URL="http://192.168.64.2/slim/onlineShop/onlineShop/RestApi/LoginApi.php/";
    private static RetrofitClient1 mInstance;
    private Retrofit retrofit;
    public RetrofitClient1(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASED_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient1 getInstance(){
        if(mInstance==null) {
            mInstance = new RetrofitClient1();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
