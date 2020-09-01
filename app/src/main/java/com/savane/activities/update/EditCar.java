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
import com.savane.data.model.User;
import com.savane.data.model.car.CarCompany;
import com.savane.data.model.car.CarCompanyModelResponse;
import com.savane.data.model.car.CarCompanyResponse;
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

public class EditCar extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private NiceSpinner brandSpinner,modelSpinner,quantitySpinner,fuelTypeSpinner;
    private List<String> companyList= new ArrayList<>();
    private List<String> modelList= new ArrayList<>();
    private EditText edPrice,edAddinformation,edKm;
    private TextView dateTv;
    private ImageButton next,cancel;
    Integer qua=null;
    String stFuelType,stCarcondition,company,model;
    Intent intent;
    ItemFullInformation modelInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);
        edPrice= findViewById(R.id.ed_price);
        edAddinformation=findViewById(R.id.add_info);
        dateTv=findViewById(R.id.item_date);
        edKm=findViewById(R.id.ed_km);
        next=findViewById(R.id.next);
        brandSpinner = findViewById(R.id.sp_brand);
        modelSpinner = findViewById(R.id.sp_model);
        quantitySpinner = findViewById(R.id.sp_qt);
        fuelTypeSpinner = findViewById(R.id.sp_fuel);
        intent=getIntent();

        loadItemDetails(intent.getIntExtra("itemId",0),intent.getStringExtra("categoryName"));
        brandSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
             company = companyList.get(position);
            if (company.equals("*Brand")) {
                brandSpinner.setError("Plase select the brand");
                brandSpinner.requestFocus();
                return;
            } else {
                modelList.clear();
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
                Toast.makeText(EditCar.this,"please select model",Toast.LENGTH_LONG).show();
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

                   next.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                            updateCar();
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
    private void loadCompanise(String brand){
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
                    int index = companyList.indexOf(brand);
                    brandSpinner.setSelectedIndex(index);

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
                    loadCompanise(modelInformation.getItemBrand());
                    quantitySpinner.setSelectedIndex(modelInformation.getItemQuantity());
                    edKm.setText(modelInformation.getKilometerCovered());
                    if (modelList.size()==0){
                        loadBrandModel("car",modelInformation.getItemModel());

                    }
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

            }
        });
    }
    private void loadBrandModel(String carModel,String model){
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
//                    modelSpinner.setSelectedIndex(modelList.indexOf(model));
                    Toast.makeText(EditCar.this, "model is "+modelList.indexOf(model), Toast.LENGTH_SHORT).show();

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

    private void updateCar(){
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
        Call<DefaultUpdateResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .updateCar(intent.getIntExtra("itemId",0),company,model,year,qua,pric,addinfo, stFuelType,kilometer,intent.getIntExtra("subCategoryId",0),user.getId());
        call.enqueue(new Callback<DefaultUpdateResponse>() {
            @Override
            public void onResponse(Call<DefaultUpdateResponse> call, Response<DefaultUpdateResponse> response) {
                if (response.code()==200){
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "internal server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultUpdateResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        }
    }
