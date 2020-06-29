package com.savane.api;

import androidx.annotation.Nullable;

import com.savane.data.model.Address;
import com.savane.data.model.DefaultResponse;
import com.savane.data.model.Image;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("RegisterApi.php/createUser")
    Call<DefaultResponse> createUser(
            @Nullable @Field("Firstname") String firsName,
            @Nullable @Field("LastName") String LastName,
            @Nullable @Field("Email") String Email,
            @Nullable @Field("Phone") String Phone,
            @Nullable @Field("DateOfBirth") String DateOfBirth,
            @Nullable @Field("Password") String Password,
            @Nullable @Field("Gender") String Gender
    );

    @FormUrlEncoded
    @POST("LoginApi.php/userLogin")
    Call<LoginResponse> userLogin(
            @Nullable @Field("email") String email,
            @Nullable @Field("password") String password
    );
    @FormUrlEncoded
    @POST("RegisterApi.php/sendResetOtp")
    Call<DefaultResponse> sendResetOtp(
            @Nullable @Field("email") String email
    );
    @FormUrlEncoded
    @POST("RegisterApi.php/updatePassword")
    Call<DefaultResponse> updatePassword(
            @Nullable @Field("email") String email,
            @Nullable @Field("otp") String otp,
            @Nullable @Field("password") String password,
            @Nullable @Field("conformpassword") String conformpassword
    );
    @FormUrlEncoded
    @POST("RegisterApi.php/updateUser")
    Call<DefaultResponse> updateUser(
            @Nullable @Field("Firstname") String Firstname,
            @Nullable @Field("LastName") String LastName,
            @Nullable @Field("Email") String Email,
            @Nullable @Field("Phone") String Phone,
            @Nullable @Field("DateOfBirth") String DateOfBirth,
            @Nullable @Field("Gender") String Gender
    );
    @FormUrlEncoded
    @POST("RegisterApi.php/deleteUser")
    Call<DefaultResponse> deleteUser(
            @Nullable @Field("email") String email
    );
    @FormUrlEncoded
    @POST("AddressApi.php/addAddress")
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
    @POST("ImageApi.php/addImage")
    Call<DefaultResponse> addImage(
            @Nullable @Field("image1") String image1,
            @Nullable @Field("userId") String user_id
    );

    @GET("ImageApi.php/getImageByUserId")
    Call<Image> getProfileImage(@Query("userId") Integer userId);


    @GET("AddressApi.php/getAddressByUserId")
    Call<Address> getAddressByUserId(@Query("userId") int userId);
}
