package com.savane.api;

import androidx.annotation.Nullable;

import com.savane.data.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface Api {
    @FormUrlEncoded
    @POST("createUser")
    Call<DefaultResponse> createUser(

            @Nullable @Field("Firstname") String firsName,
            @Nullable @Field("LastName") String LastName,
            @Nullable @Field("Email") String Email,
            @Nullable @Field("Phone") String Phone,
            @Nullable @Field("DateOfBirth") String DateOfBirth,
            @Nullable @Field("Password") String Password,
           // @Nullable @Field("DateOfregistration") String DateOfregistration,
           // @Nullable @Field("DateOfUpdate") String DateOfUpdate,
            @Nullable @Field("Gender") String Gender
            //@Nullable @Field("AddressId") int AddressId,
            //@Nullable @Field("UserType") String UserType
    );

    @FormUrlEncoded
    @POST("userLogin")
    Call<LoginResponse> userLogin(
            @Nullable @Field("email") String email,
            @Nullable @Field("password") String password
    );
   /* @GET("getAll")
    Call<UserResponse> getUsers();*/
}
