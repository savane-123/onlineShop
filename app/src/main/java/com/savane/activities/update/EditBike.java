package com.savane.activities.update;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.savane.R;
import com.savane.api.RetrofitClient;
import com.savane.data.model.DefaultUpdateResponse;
import com.savane.data.model.bike.BikeCompany;
import com.savane.data.model.bike.BikeModelResponse;
import com.savane.data.model.item.ItemFullDetailRespose;
import com.savane.data.model.item.ItemFullInformation;
import com.savane.share.datePickerFragment;
import com.savane.storage.SharedPrefManager;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBike extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private NiceSpinner bikeBrandSpinner,quantitySpinner,fuelTypeSpinner;
    private List<String> bikeCopanyList= new ArrayList<>();
    private EditText edPrice,edAddinformation,edKm,edModel;
    private TextView dateTv;
    private ImageButton btnNext;
    Integer qua=null;
    String stFuelType,brand;
    ItemFullInformation modelInformation;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bike);
        edPrice= findViewById(R.id.ed_price);
        edAddinformation=findViewById(R.id.add_info);
        dateTv=findViewById(R.id.item_date);
        edKm=findViewById(R.id.ed_km);
        edModel=findViewById(R.id.ed_model);
        btnNext=findViewById(R.id.next);
        bikeBrandSpinner = findViewById(R.id.sp_brand);
        quantitySpinner = findViewById(R.id.sp_qt);
        fuelTypeSpinner = findViewById(R.id.sp_fuel);
        intent=getIntent();
        loadBikeCompanies(intent.getIntExtra("subCategoryId",0));
        loadItemDetails(intent.getIntExtra("itemId",0),intent.getStringExtra("categoryName"));
        bikeBrandSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            String company = bikeCopanyList.get(position);
            if (company.equals("*Brand")) {
                bikeBrandSpinner.setError("Plase select the brand");
                bikeBrandSpinner.requestFocus();
                return;
            } else {
                brand=bikeCopanyList.get(position).toString();
            }
        });
        List<Integer> quantity = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        loadQuantity(quantity);
        quantitySpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            if (quantity.indexOf(position) == 0) {
                quantitySpinner.setError("The quantity is required");
                quantitySpinner.requestFocus();
                return;
            } else {
                qua = quantity.indexOf(position);
            }

        });
        List<String> fuel = new ArrayList<>(Arrays.asList("Fuel type", "Diesel", "gasoline"));
        loadFuel(fuel);
        fuelTypeSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            if (fuel.get(position).equals("Fuel type")) {
                fuelTypeSpinner.setError("Fuel type is required");
                fuelTypeSpinner.requestFocus();
                return;
            } else {
                stFuelType = fuel.get(position);
            }
        });

        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepiker= new datePickerFragment();
                datepiker.show(getSupportFragmentManager(),"Date picker");
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }




 private void loadBikeCompanies(Integer subId){
    Call<BikeModelResponse>  call = RetrofitClient
            .getInstance()
            .getApi().getBikesCompanies(subId);
    call.enqueue(new Callback<BikeModelResponse>() {
        @Override
        public void onResponse(Call<BikeModelResponse> call, Response<BikeModelResponse> response) {
            if (response.isSuccessful()){
                List<BikeCompany> bikeCompanies = response.body().getBikesCompanies();
                for(BikeCompany company:bikeCompanies){
                    bikeCopanyList.add(company.getTitle());
                }
                bikeCopanyList.add(0,"*Brand");
                bikeBrandSpinner.attachDataSource(bikeCopanyList);
//                bikeBrandSpinner.setSelectedIndex(bikeCopanyList.indexOf(brand));

            }else {
                Toast.makeText(getApplicationContext(),"internal sterver error",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<BikeModelResponse> call, Throwable t) {
            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
        }
    });
 }
    private void loadQuantity(List<Integer> quantity){
        quantitySpinner.attachDataSource(quantity);
    }
    private void loadFuel(List<String> fuelType){
        fuelTypeSpinner.attachDataSource(fuelType);
    }
    private void loadItemDetails(int itemId,String categoryName){
        Call<ItemFullDetailRespose> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItemFullDetails(itemId,categoryName);
        call.enqueue(new Callback<ItemFullDetailRespose>() {
            @Override
            public void onResponse(Call<ItemFullDetailRespose> call, Response<ItemFullDetailRespose> response) {
                if (response.isSuccessful()){
                    modelInformation = response.body().getItems();
                    edAddinformation.setText(modelInformation.getAdditionalInfo().toString());
                    edPrice.setText(modelInformation.getItemPrice().toString());
                    dateTv.setText(modelInformation.getItemYear());
                    bikeBrandSpinner.setSelectedIndex(bikeCopanyList.indexOf(modelInformation.getItemBrand()));
                    quantitySpinner.setSelectedIndex(modelInformation.getItemQuantity());
                    edKm.setText(modelInformation.getKilometerCovered());
                    edModel.setText(modelInformation.getItemModel());
                    if (modelInformation.getFuelType().equalsIgnoreCase("Diesel")){
                        fuelTypeSpinner.setSelectedIndex(1);
                    }else {
                        fuelTypeSpinner.setSelectedIndex(2);
                    }


                }else {
                    Toast.makeText(getApplicationContext(),"error in server",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ItemFullDetailRespose> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void update(){
        int userId=SharedPrefManager.getInstance(getApplication()).getUser().getId();
        String pric=edPrice.getText().toString().trim();
        String year=dateTv.getText().toString().trim();
        String addinf=edAddinformation.getText().toString().trim();
        String fu=fuelTypeSpinner.getText().toString().trim();
        String kilometer=edKm.getText().toString().trim();
        String model = edModel.getText().toString().trim();
          if(pric.isEmpty()){
              edPrice.setError("Please enter the price");
              edPrice.requestFocus();
              return;
          }
        if(year.isEmpty()){
            dateTv.setError("Please enter the date");
            dateTv.requestFocus();
            return;
        }
        if(kilometer.isEmpty()){
            edKm.setError("Please enter the kilometer covered by your car");
            edKm.requestFocus();
            return;
        }
        if(model.isEmpty()){
            edModel.setError("please enter the model");
            edModel.requestFocus();
            return;
        }
        Call<DefaultUpdateResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .updateBike(intent.getIntExtra("itemId",0),brand,model,year,qua,pric,addinf,fu,kilometer,intent.getIntExtra("subCategoryId",0),userId);
        call.enqueue(new Callback<DefaultUpdateResponse>() {
            @Override
            public void onResponse(Call<DefaultUpdateResponse> call, Response<DefaultUpdateResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "error code "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultUpdateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "id is "+intent.getIntExtra("subCategoryId",0), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String dateFormate = new SimpleDateFormat("yyyy/MM/dd").format(c.getTime());
        dateTv.setText(dateFormate);
    }
}