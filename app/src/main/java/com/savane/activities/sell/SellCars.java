package com.savane.activities.sell;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.savane.R;
import com.savane.activities.items.ItemImage;
import com.savane.api.RetrofitClient;
import com.savane.data.model.User;
import com.savane.data.model.car.CarCompany;
import com.savane.data.model.car.CarCompanyModelResponse;
import com.savane.data.model.car.CarCompanyResponse;
import com.savane.data.model.item.CreateItemResponse;
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

public class SellCars extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Intent intent;
    private NiceSpinner brandSpinner,modelSpinner,quantitySpinner,fuelTypeSpinner;
    private List<String> companyList= new ArrayList<>();
    private List<String> modelList= new ArrayList<>();
    private EditText edPrice,edAddinformation,edKm;
    private TextView dateTv;
    private Button selecImage;
    Integer qua=null;
    String stFuelType,stCarcondition,company,model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_cars);
        intent=getIntent();
        edPrice= findViewById(R.id.ed_price);
        edAddinformation=findViewById(R.id.add_info);
        dateTv=findViewById(R.id.item_date);
        edKm=findViewById(R.id.ed_km);
        selecImage=findViewById(R.id.select_image);
        brandSpinner = findViewById(R.id.sp_brand);
        modelSpinner = findViewById(R.id.sp_model);
        quantitySpinner = findViewById(R.id.sp_qt);
        fuelTypeSpinner = findViewById(R.id.sp_fuel);
        loadCompanise();
        brandSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
             company = companyList.get(position);
            if (company.equals("*Brand")) {
                brandSpinner.setError("Plase select the brand");
                brandSpinner.requestFocus();
                return;
            } else {
                loadModel(company);

            }
        });
        List<Integer> quantity = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        loadQuantity(quantity);
        quantitySpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            if (quantity.get(position) == 0) {
                quantitySpinner.setError("The quantity is required");
                quantitySpinner.requestFocus();
                return;
            } else {
                qua = quantity.get(position);
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

        modelSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            if (modelList.get(position).equals("*Model")){
                Toast.makeText(SellCars.this,"please select model",Toast.LENGTH_LONG).show();
                modelSpinner.requestFocus();
                return;
            }else{
                model = modelList.get(position);
            }
        });
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepiker= new datePickerFragment();
                datepiker.show(getSupportFragmentManager(),"Date picker");
            }
        });

                   selecImage.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           selectImage();
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

    private void loadModel(String carModel){
        Call<CarCompanyModelResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getModles(carModel);
        call.enqueue(new Callback<CarCompanyModelResponse>() {
            @Override
            public void onResponse(Call<CarCompanyModelResponse> call, Response<CarCompanyModelResponse> response) {
                if (response.isSuccessful()){
                    List<CarCompany> companies = response.body().getModels();
                    for (CarCompany company:companies){
                        modelList.add(company.getTitle());
                    }
                    modelList.add(0,"*Model");
                    modelSpinner.attachDataSource(modelList);
                }else {
                    Toast.makeText(getApplicationContext(),"error in request",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CarCompanyModelResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
    private void loadCompanise(){
        Call<CarCompanyResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCompanies();
        call.enqueue(new Callback<CarCompanyResponse>() {
            @Override
            public void onResponse(Call<CarCompanyResponse> call, Response<CarCompanyResponse> response) {
                if (response.isSuccessful()){
                    List<CarCompany> carCompanies = response.body().getCompanies();
                    for(CarCompany carCompany:carCompanies){
                        companyList.add(carCompany.getTitle());
                    }
                    companyList.add(0,"*Brand");
                    brandSpinner.attachDataSource(companyList);

                }else{
                    Toast.makeText(getApplicationContext(),"error in request",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CarCompanyResponse> call, Throwable t) {
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

    private void selectImage(){
        int subId=intent.getIntExtra("subId",0);
        User user= SharedPrefManager.getInstance(getApplication()).getUser();
        String pric=edPrice.getText().toString().trim();
        String year=dateTv.getText().toString().trim();
        String addinfo=edAddinformation.getText().toString().trim();
        String kilometer=edKm.getText().toString().trim();
          if(pric.isEmpty()){
              edPrice.setError("Please enter the price");
              edPrice.requestFocus();
          }
        if(year.isEmpty()){
            dateTv.setError("Please enter the date");
            dateTv.requestFocus();
        }
        if(kilometer.isEmpty()){
            edKm.setError("Please enter the kilometer covered by your car");
            edKm.requestFocus();
        }
        Call<CreateItemResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .createCars(company,model,year,qua,pric,addinfo, stFuelType,kilometer,subId,user.getId());

        call.enqueue(new Callback<CreateItemResponse>() {
            @Override
            public void onResponse(Call<CreateItemResponse> call, Response<CreateItemResponse> response) {
                if(response.code()==201 ){
                    CreateItemResponse dr=response.body();
                    Intent intent=new Intent(getApplicationContext(),ItemImage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("itemId",dr.getItemId());
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(getApplicationContext(),"internal server error",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CreateItemResponse> call, Throwable t) {
                Toast.makeText(SellCars.this,t.getMessage()+"unable to create user",Toast.LENGTH_LONG).show();

            }
        });
        }
    }
