package com.savane.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitclit3 {

    private static final String BASED_URL="http://192.168.64.2/slim/onlineShop/onlineShop/image/ImageApi.php/";
    private static retrofitclit3 mInstance;
    private Retrofit retrofit;
    public retrofitclit3(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASED_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized retrofitclit3 getInstance(){
        if(mInstance==null) {
            mInstance = new retrofitclit3();
        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
