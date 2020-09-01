package com.savane.activities.items;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.savane.R;
import com.savane.activities.MainActivity;
import com.savane.activities.fragments.ItemImageSlider;
import com.savane.adapters.ItemsDetailsAdapter;
import com.savane.api.RetrofitClient;
import com.savane.data.model.item.ItemFullDetailRespose;
import com.savane.data.model.item.ItemFullInformation;
import com.savane.data.model.item.ItemInformation;
import com.savane.data.model.item.ItemInformationResponse;
import com.savane.data.model.user.UserInfoResponse;
import com.savane.data.model.user.UserItemInfo;
import com.savane.share.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFulldetails extends AppCompatActivity implements OnMapReadyCallback {
    RecyclerView recyclerView;
    LinearLayout extraInfo;
    List<ItemInformation> items = new ArrayList<>();
    Intent intent;
    ItemsDetailsAdapter itemsDetailsAdapter;
    private TextView tvBrand,tvModel,tvPrice,tvYear,tvDescription,tvFuelType,tvKilometer,tvPhone,tvUserName;
   private ItemFullInformation fullInformation;
    private Button back;
   private ImageView userImage;
    private GoogleMap mMap;
    private Double lon,lat;
    private String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_fulldetails);
        recyclerView=findViewById(R.id.item_recycle_view);
        LinearLayoutManager itemLyaout = new LinearLayoutManager(getApplicationContext());
        itemLyaout.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(itemLyaout);
         back=findViewById(R.id.back);
        tvBrand = findViewById(R.id.tv_brand);
        tvModel = findViewById(R.id.tv_model);
        tvPrice = findViewById(R.id.tv_price);
        tvYear = findViewById(R.id.tv_year);
        tvDescription = findViewById(R.id.tv_descript);
        tvFuelType = findViewById(R.id.tv_fuel);
        tvKilometer = findViewById(R.id.tv_Km_drive);
        tvUserName = findViewById(R.id.tv_user_name);
        tvPhone = findViewById(R.id.tv_user_phone);
        userImage = findViewById(R.id.im_user_image);
        extraInfo=findViewById(R.id.detail_linear);
        intent=getIntent();
        extraInfo.setVisibility(View.GONE);
        FragmentManager fragmentManager =  ItemFulldetails.this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction;
        Fragment fragment=new ItemImageSlider(intent.getIntExtra("itemId",0));
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.image_view_frame,fragment);
        fragmentTransaction.commit();
        intent=getIntent();
        boolean isCarOrBike=intent.getBooleanExtra("isCarOrBike",false);
        if (isCarOrBike)
        {
            extraInfo.setVisibility(View.VISIBLE);
        }
            else {
                extraInfo.setVisibility(View.GONE);
        }
        //super.onCreate(savedInstanceState);
        loadDate(intent.getIntExtra("itemId",0),intent.getStringExtra("categoryName"));
        LoadUserInfo(intent.getIntExtra("itemId",0));
        loadDataToRecycle();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void loadDate(int itemId,String categoryName){
        Call<ItemFullDetailRespose> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItemFullDetails(itemId,categoryName);
        call.enqueue(new Callback<ItemFullDetailRespose>() {
            @Override
            public void onResponse(Call<ItemFullDetailRespose> call, Response<ItemFullDetailRespose> response) {
                if (response.isSuccessful()){
                    fullInformation = response.body().getItems();
                    tvBrand.setText(fullInformation.getItemBrand());
                    tvModel.setText(fullInformation.getItemModel());
                    tvYear.setText(fullInformation.getItemYear());
                    tvDescription.setText(fullInformation.getAdditionalInfo());
                    tvPrice.setText(fullInformation.getItemPrice());
                    tvKilometer.setText(fullInformation.getKilometerCovered());
                    tvFuelType.setText(fullInformation.getFuelType());
                }else {
                    Toast.makeText(getApplicationContext(), "Internal server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemFullDetailRespose> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internal server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LoadUserInfo(int itemId){
        Call<UserInfoResponse> call =  RetrofitClient
                .getInstance()
                .getApi()
                .getVendorInfo(itemId);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if(response.isSuccessful()){
                    UserItemInfo userItemInfo = response.body().getUserInfo();
                    Glide.with(ItemFulldetails.this)
                            .load(Constant.UPLOAD_FOLDER+userItemInfo.getProfileImage())
                            .fitCenter()
                            .into(userImage);
                    tvUserName.setText(userItemInfo.getFirestName()+" "+userItemInfo.getLastName());
                    tvPhone.setText(userItemInfo.getPhone());
                    lon = userItemInfo.getLon();
                    lat = userItemInfo.getLat();
                    city = userItemInfo.getCity();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.user_map);
                    mapFragment.getMapAsync(ItemFulldetails.this);
                }else{
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMinZoomPreference(13.0f);
        googleMap.setMaxZoomPreference(16.0f);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("near to "+city));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void loadDataToRecycle(){
        Call<ItemInformationResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItems();
        call.enqueue(new Callback<ItemInformationResponse>() {
            @Override
            public void onResponse(Call<ItemInformationResponse> call, Response<ItemInformationResponse> response) {
                if (response.isSuccessful()){
                    List<ItemInformation> items= response.body().getItems();
                    itemsDetailsAdapter=new ItemsDetailsAdapter(getApplicationContext(),items);
                    recyclerView.setAdapter(itemsDetailsAdapter);
                }else {
                    Toast.makeText(getApplicationContext(),"internal srver error"+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ItemInformationResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();


            }
        });
    }
}