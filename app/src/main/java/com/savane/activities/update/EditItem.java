package com.savane.activities.update;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.savane.R;
import com.savane.api.RetrofitClient;
import com.savane.data.model.item.Brand;
import com.savane.data.model.item.BrandResponse;
import com.savane.data.model.item.ItemFullInformation;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private NiceSpinner brandSpinner,quantitySpinner;
    private List<String> brandList= new ArrayList<>();
    List<Integer> quantity = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    private EditText edPrice,edAddinformation,edModel,edBrand;
    private TextView dateTv;
    private ImageButton imgBtnNext,imgBtnCancel;
    int qua;
    String brand,itemBrand;
    Intent intent;
    ItemFullInformation model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        edPrice= findViewById(R.id.ed_price);
        edAddinformation=findViewById(R.id.add_info);
        dateTv=findViewById(R.id.item_date);
        edModel=findViewById(R.id.ed_model);
        imgBtnNext=findViewById(R.id.next);
        brandSpinner = findViewById(R.id.sp_brand);
        quantitySpinner = findViewById(R.id.sp_qt);
        edBrand = findViewById(R.id.ed_brand);
        imgBtnCancel = findViewById(R.id.cancel);
        intent = getIntent();
//        loadItemDetails(intent.getIntExtra("itemId",0),intent.getStringExtra("categoryName"));
//        itemBrand =intent.getStringExtra("categoryName");
//        int subCategoryId =intent.getIntExtra("subCategoryId",0);
//        if (itemBrand.equals("mobile")){
//            LoadBrands(subCategoryId,itemBrand);
//            brandSpinner.setVisibility(View.VISIBLE);
//        }else {
//            brandSpinner.setVisibility(View.GONE);
//            edBrand.setVisibility(View.VISIBLE);
//        }
//        loadQuantity(quantity);
//        imgBtnNext.setOnClickListener(v -> {
////            saveDate();
//        });
//        dateTv.setOnClickListener(v -> {
//            DialogFragment datepiker= new datePickerFragment();
//            datepiker.show(getSupportFragmentManager(),"Date picker");
//        });
//        quantitySpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
//            if (quantity.get(position)==0){
//                quantitySpinner.setError("please select the quantity");
//                quantitySpinner.requestFocus();
//                return;
//            }else {
//                qua = quantity.get(position);
//                Toast.makeText(getApplicationContext(),"the "+qua,Toast.LENGTH_LONG).show();
//            }
//        });
//        brandSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
//            if (brandList.get(position).equals("*Brand")){
//                brandSpinner.setError("please select the Brand");
//                brandSpinner.requestFocus();
//                return;
//            }else {
//                brand = brandList.get(position);
//            }
//        });
//

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
//    private void saveDate(){
//        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
//        String pric=edPrice.getText().toString().trim();
//        String year=dateTv.getText().toString().trim();
//        String addinf=edAddinformation.getText().toString().trim();
//        String model = edModel.getText().toString().trim();
//
//        if (!itemBrand.equals("mobile")){
//            brand=edBrand.getText().toString().trim();
//        }
//        if(pric.isEmpty()){
//            edPrice.setError("Please enter the price");
//            edPrice.requestFocus();
//            return;
//        }
//        if(year.isEmpty()){
//            dateTv.setError("Please enter the date");
//            dateTv.requestFocus();
//            return;
//        }
//        if(model.isEmpty()){
//            edModel.setError("please enter the model");
//            edModel.requestFocus();
//            return;
//        }
//        if (addinf.isEmpty()){
//            edAddinformation.setError("add description about your item");
//            edAddinformation.requestFocus();
//            return;
//        }
////        ItemModel itemModel = new ItemModel(brand,model,qua,pric,year,addinf);
//        Call<DefaultResponse> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .addItem(brand,model,qua,pric,year,addinf,user.getId(),5);
//        call.enqueue(new Callback<DefaultResponse>() {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                if (response.code()==201){
//                    Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
//
//                }else if (response.code()==202){
//                    Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
//
//                }else {
////                    Toast.makeText(getApplicationContext(),"internal server error"+response.code(),Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(),qua,Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }
//    private void loadItemDetails(int itemId,String categoryName){
//        Call<ItemFullDetailRespose> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .getItemFullDetails(itemId,categoryName);
//        call.enqueue(new Callback<ItemFullDetailRespose>() {
//            @Override
//            public void onResponse(Call<ItemFullDetailRespose> call, Response<ItemFullDetailRespose> response) {
//                if (response.isSuccessful()){
//                    model = response.body().getItems();
//                    edModel.setText(model.getItemModel().toString());
//                    edAddinformation.setText(model.getAdditionalInfo().toString());
//                    edPrice.setText(model.getItemPrice().toString());
//                    dateTv.setText(model.getItemYear());
//                    int index=brandList.indexOf(intent.getStringExtra("categoryName"));
//                    brandSpinner.setSelectedIndex(index);
//
//
//
//                }else {
//                    Toast.makeText(getApplicationContext(),"error in server",Toast.LENGTH_LONG).show();
//                }
//            }

//            @Override
//            public void onFailure(Call<ItemFullDetailRespose> call, Throwable t) {
//
//            }
//        });
   // }

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