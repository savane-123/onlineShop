package com.savane.api;

import androidx.annotation.Nullable;

import com.savane.activities.items.ItemImageresonse;
import com.savane.data.model.Address;
import com.savane.data.model.DefaultResponse;
import com.savane.data.model.DefaultUpdateResponse;
import com.savane.data.model.Image;
import com.savane.data.model.bike.BikeModelResponse;
import com.savane.data.model.car.CarCompanyModelResponse;
import com.savane.data.model.car.CarCompanyResponse;
import com.savane.data.model.category.CategoryResponse;
import com.savane.data.model.category.SubCategoryResponse;
import com.savane.data.model.image.ImageSliderResponse;
import com.savane.data.model.image.ResponseUpdateProfileImage;
import com.savane.data.model.item.BrandResponse;
import com.savane.data.model.item.CreateItemResponse;
import com.savane.data.model.item.ItemFullDetailRespose;
import com.savane.data.model.item.ItemInformationResponse;
import com.savane.data.model.user.UserInfoResponse;
import com.savane.data.model.user.UserItemResponse;

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
    Call<DefaultResponse> conformPassword(
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
    @POST("itemApi.php/testImages")
    Call<DefaultResponse> testImages(
            @Nullable @Field("imageOne") String imageOne,
            @Nullable @Field("imageTwo") String imageTwo,
            @Nullable @Field("imageThree") String imageThree,
            @Nullable @Field("imageFour") String imageFour,
            @Nullable @Field("itemId") Integer itemId
    );
    @FormUrlEncoded
    @POST("carApI.php/createCars")
    Call<CreateItemResponse> createCars(
            @Nullable @Field("itemBrand") String itemBrand,
            @Nullable @Field("ItemModel") String ItemModel,
            @Nullable @Field("itemYear") String itemYear,
            @Nullable @Field("ItemQuantity") Integer ItemQuantity,
            @Nullable @Field("itemPrice") String itemPrice,
            @Nullable @Field("additionalInfo") String additionalInfo,
            @Nullable @Field("FuelType") String FuelType,
            @Nullable @Field("KilometerCovered") String KilometerCovered,
            @Nullable @Field("subCategoryId") Integer subCategoryId,
            @Nullable @Field("userId") Integer userId
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

    @GET("itemApi.php/getCategories")
    Call<CategoryResponse> getCategories();
    @GET("itemApi.php/getSubCategories")
    Call<SubCategoryResponse> getSubCategories(@Query("categoryName") String categoryName);
    @GET("itemApi.php/getCompanies")
    Call<CarCompanyResponse> getCompanies();
    @GET("itemApi.php/getModles")
    Call<CarCompanyModelResponse> getModles(@Query("carCompany") String carCompany);
   //retrive bike_make
   @GET("itemApi.php/getBikesCompanies")
   Call<BikeModelResponse> getBikesCompanies(@Query("subId") int subId);
    //add bike
    @FormUrlEncoded
    @POST("BikeApi.php/createBikes")
    Call<CreateItemResponse> addBike(
            @Nullable @Field("itemBrand") String itemBrand,
            @Nullable @Field("ItemModel") String ItemModel,
            @Nullable @Field("itemYear") String itemYear,
            @Nullable @Field("ItemQuantity") Integer ItemQuantity,
            @Nullable @Field("itemPrice") String itemPrice,
            @Nullable @Field("additionalInfo") String additionalInfo,
            @Nullable @Field("FuelType") String FuelType,
            @Nullable @Field("KilometerCovered") String KilometerCovered,
            @Nullable @Field("subCategoryId") Integer subCategoryId,
            @Nullable @Field("userId") Integer userId
    );
    // load brand
    @GET("itemApi.php/getBrands")
    Call<BrandResponse> getBrandsList(@Query("subId") int subId,@Query("categoryName") String categoryName);
    /**
     * save addItem
     */
    @FormUrlEncoded
    @POST("itemApi.php/createItems")
    Call<CreateItemResponse> addItem(
            @Nullable @Field("itemBrand") String itemBrand,
            @Nullable @Field("ItemModel") String ItemModel,
            @Nullable @Field("ItemQuantity") Integer ItemQuantity,
            @Nullable @Field("itemPrice") String itemPrice,
            @Nullable @Field("itemYear") String itemYear,
            @Nullable @Field("additionalInfo") String additionalInfo,
            @Nullable @Field("userId") Integer userId,
            @Nullable @Field("subCategoryId") int subCategoryId

    );
    @GET("utilApi.php/getAllImages")
    Call<ImageSliderResponse> getAllImages();

    @GET("itemApi.php/getItems")
    Call<ItemInformationResponse> getItems();

    @GET("itemApi.php/getSubCategoyItems")
    Call<ItemInformationResponse> getSubCategoyItems(@Query("subId") int subId);

    @GET("itemApi.php/getItemFullDetails")
    Call<ItemFullDetailRespose> getItemFullDetails(@Query("itemId") int itemId,
                                                   @Query("categoryName")String categoryName);

    @GET("itemApi.php/getItemImages")
    Call<ItemImageresonse> getItemImage(@Query("itemId") int itemId);
    @GET("itemApi.php/getUserInfo")
    Call<UserInfoResponse> getVendorInfo(@Query("itemId") int itemId);
    //get items by uer id
    @GET("itemApi.php/getItemByUserId")
    Call<UserItemResponse> getItemByUserId(@Query("userId") int userId);
    @FormUrlEncoded
    @POST("itemApi.php/updateItem")
    Call<DefaultResponse> updateItem(
            @Nullable @Field("itemId") Integer id,
            @Nullable @Field("itemBrand") String itemBrand,
            @Nullable @Field("itemModel") String ItemModel,
            @Nullable @Field("itemQuantity") Integer ItemQuantity,
            @Nullable @Field("itemPrice") String itemPrice,
            @Nullable @Field("itemYear") String itemYear,
            @Nullable @Field("additionalInfo") String additionalInfo,
            @Nullable @Field("userId") Integer userId

    );
    @FormUrlEncoded
    @POST("BikeApi.php/update")
    Call<DefaultUpdateResponse> updateBike(
            @Nullable @Field("itemId") int itemId,
            @Nullable @Field("itemBrand") String itemBrand,
            @Nullable @Field("ItemModel") String ItemModel,
            @Nullable @Field("itemYear") String itemYear,
            @Nullable @Field("ItemQuantity") Integer ItemQuantity,
            @Nullable @Field("itemPrice") String itemPrice,
            @Nullable @Field("additionalInfo") String additionalInfo,
            @Nullable @Field("FuelType") String FuelType,
            @Nullable @Field("KilometerCovered") String KilometerCovered,
            @Nullable @Field("subCategoryId") Integer subCategoryId,
            @Nullable @Field("userId") Integer userId
    );
    @FormUrlEncoded
    @POST("carApI.php/update")
    Call<DefaultUpdateResponse> updateCar(
            @Nullable @Field("itemId") int itemId,
            @Nullable @Field("itemBrand") String itemBrand,
            @Nullable @Field("ItemModel") String ItemModel,
            @Nullable @Field("itemYear") String itemYear,
            @Nullable @Field("ItemQuantity") Integer ItemQuantity,
            @Nullable @Field("itemPrice") String itemPrice,
            @Nullable @Field("additionalInfo") String additionalInfo,
            @Nullable @Field("FuelType") String FuelType,
            @Nullable @Field("KilometerCovered") String KilometerCovered,
            @Nullable @Field("subCategoryId") Integer subCategoryId,
            @Nullable @Field("userId") Integer userId
    );
    @FormUrlEncoded
    @POST("RegisterApi.php/updateUserPassword")
    Call<DefaultResponse> updatePassword(
            @Nullable @Field("email") String email,
            @Nullable @Field("password") String password,
            @Nullable @Field("newPassword") String newPassword

    );
    @FormUrlEncoded
    @POST("AddressApi.php/updateAddress")
    Call<DefaultResponse> updateAddress(
            @Nullable @Field("email") String email,
            @Nullable @Field("address") String address,
            @Nullable @Field("longitude") String longitude,
            @Nullable @Field("lagitude") String lagitude,
            @Nullable @Field("city") String city,
            @Nullable @Field("state") String state,
            @Nullable @Field("country") String country
    );
    @FormUrlEncoded
    @POST("ImageApi.php/updateProfileImage")
    Call<ResponseUpdateProfileImage> updateImage(
            @Nullable @Field("image1") String image,
            @Nullable @Field("userId") int userId
    );

}
