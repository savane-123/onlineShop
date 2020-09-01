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
import com.savane.data.model.item.Brand;
import com.savane.data.model.item.BrandResponse;
import com.savane.data.model.item.CreateItemResponse;
import com.savane.data.model.item.ItemInformation;
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

public class SellItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private NiceSpinner brandSpinner,quantitySpinner;
    private List<String> brandList= new ArrayList<>();
    List<Integer> quantity = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    private EditText edPrice,edAddinformation,edModel,edBrand;
    private TextView dateTv;
    private Button btnNext;
    int qua;
    String brand,itemBrand;
    Intent intent;
    ItemInformation itemInformation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_item);
        edPrice= findViewById(R.id.ed_price);
        edAddinformation=findViewById(R.id.add_info);
        dateTv=findViewById(R.id.item_date);
        edModel=findViewById(R.id.ed_model);
        btnNext=findViewById(R.id.next);
        brandSpinner = findViewById(R.id.sp_brand);
        quantitySpinner = findViewById(R.id.sp_qt);
        edBrand = findViewById(R.id.ed_brand);
        intent = getIntent();
        itemBrand =intent.getStringExtra("categoryName");
        int subId = intent.getIntExtra("subId",0);
        Toast.makeText(getApplicationContext(), itemBrand, Toast.LENGTH_LONG).show();

        if (itemBrand.equalsIgnoreCase("mobile phone")){
            LoadBrands(subId,"mobile");
            edBrand.setVisibility(View.GONE);
            brandSpinner.setVisibility(View.VISIBLE);

        }else {
            brandSpinner.setVisibility(View.GONE);
            edBrand.setVisibility(View.VISIBLE);
        }
        loadQuantity(quantity);
        btnNext.setOnClickListener(v -> {
            saveDate(intent.getIntExtra("subId",0));
        });
        dateTv.setOnClickListener(v -> {
            DialogFragment datepiker= new datePickerFragment();
            datepiker.show(getSupportFragmentManager(),"Date picker");
        });
        quantitySpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            if (quantity.get(position)==0){
                quantitySpinner.setError("please select the quantity");
                quantitySpinner.requestFocus();
                return;
            }else {
                qua = quantity.get(position);
            }
        });
        brandSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            if (brandList.get(position).equals("*Brand")){
                brandSpinner.setError("please select the Brand");
                brandSpinner.requestFocus();
                return;
            }else {
                brand = brandList.get(position);
            }
        });

    }


    private  void LoadBrands(int subId,String categoryName){
        Call<BrandResponse> call = RetrofitClient
        .getInstance().getApi().getBrandsList(subId,categoryName);
        call.enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
                if (response.isSuccessful()){
                    List<Brand> brands = response.body().getBrands();
                    for (Brand brand:brands){
                        brandList.add(brand.getTitle());
                    }
                    brandList.add(0,"*Brand");
                    brandSpinner.attachDataSource(brandList);
                }else {
                    Toast.makeText(getApplicationContext(),"internal error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BrandResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadQuantity(List<Integer> quantity){
        quantitySpinner.attachDataSource(quantity);
    }
    private void saveDate(int subCategoryId){

        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String pric=edPrice.getText().toString().trim();
        String year=dateTv.getText().toString().trim();
        String addinf=edAddinformation.getText().toString().trim();
        String model = edModel.getText().toString().trim();

        if (!itemBrand.equalsIgnoreCase("mobile phone")){
            brand=edBrand.getText().toString().trim();
        }
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
        if(model.isEmpty()){
            edModel.setError("please enter the model");
            edModel.requestFocus();
            return;
        }
        if (addinf.isEmpty()){
            edAddinformation.setError("add description about your item");
            edAddinformation.requestFocus();
            return;
        }
//        ItemModel itemModel = new ItemModel(brand,model,qua,pric,year,addinf);
        Call<CreateItemResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .addItem(brand,model,qua,pric,year,addinf,user.getId(),subCategoryId);
        call.enqueue(new Callback<CreateItemResponse>() {
            @Override
            public void onResponse(Call<CreateItemResponse> call, Response<CreateItemResponse> response) {
                if (response.code()==201){
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), ItemImage.class);
                    Thread addImage = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                sleep(2000);
                                i.putExtra("itemId",response.body().getItemId());
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    addImage.start();

                }else if (response.code()==202){
                    Toast.makeText(getApplicationContext(),"quantity updated",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"brand "+brand+"\n"+ "model "+model+"\n"+
                            "quantity "+qua+"\n"+"price "+pric+"\n"+"year "+year+"\n"+
                            "add info "+addinf+"\n"+"user Id "+user.getId()+"\n"+
                            "sub id "+subCategoryId,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<CreateItemResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

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