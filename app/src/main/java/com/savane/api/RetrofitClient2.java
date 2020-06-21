package com.savane.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {
    private static final String BASED_URL="http://192.168.64.2/slim/onlineShop/onlineShop/RestApi/AddressApi.php/";
    private static RetrofitClient2 mInstance;
    private Retrofit retrofit;
    public RetrofitClient2(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASED_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient2 getInstance(){
        if(mInstance==null) {
            mInstance = new RetrofitClient2();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
