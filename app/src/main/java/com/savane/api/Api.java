package com.savane.api;

import androidx.annotation.Nullable;

import com.savane.data.model.DefaultResponse;
import com.savane.data.model.UserResponse;

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
    @FormUrlEncoded
    @POST("sendResetOtp")
    Call<DefaultResponse> sendResetOtp(
            @Nullable @Field("email") String email
    );
    @FormUrlEncoded
    @POST("updatePassword")
    Call<DefaultResponse> updatePassword(
            @Nullable @Field("email") String email,
            @Nullable @Field("otp") String otp,
            @Nullable @Field("password") String password,
            @Nullable @Field("conformpassword") String conformpassword
    );
    @FormUrlEncoded
    @POST("updateUser")
    Call<DefaultResponse> updateUser(
            @Nullable @Field("Firstname") String Firstname,
            @Nullable @Field("LastName") String LastName,
            @Nullable @Field("Email") String Email,
            @Nullable @Field("Phone") String Phone,
            @Nullable @Field("DateOfBirth") String DateOfBirth,
            @Nullable @Field("Gender") String Gender
    );
    @FormUrlEncoded
    @POST("deleteUser")
    Call<DefaultResponse> deleteUser(
            @Nullable @Field("email") String email
    );
    @FormUrlEncoded
    @POST("addAddress")
    Call<DefaultResponse> addAddress(
            @Nullable @Field("email") String email,
            @Nullable @Field("address") String address,
            @Nullable @Field("longitude") String longitude,
            @Nullable @Field("lagitude") String lagitude,
            @Nullable @Field("city") String city,
            @Nullable @Field("state") String state,
            @Nullable @Field("country") String country
    );
    @FormUrlEncoded
    @POST("addImage")
    Call<DefaultResponse> addImage(
            @Nullable @Field("image1") String image1,
            @Nullable @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("getAllImages")
    Call<UserResponse> getAllImages(
            @Nullable @Field("userId") String userId
    );
}
