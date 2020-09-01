package com.savane.activities.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.savane.R;
import com.savane.adapters.SubCategoryAdapter;
import com.savane.api.RetrofitClient;
import com.savane.data.model.category.CategoryModel;
import com.savane.data.model.category.SubCategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategory extends AppCompatActivity {

    private Intent intent;
    RecyclerView recyclerView;
    private List<CategoryModel> categoryModels;
    SubCategoryAdapter subCategoryAdapter;
    String toWhere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        recyclerView = findViewById(R.id.recycl_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        intent = getIntent();
        CategoryModel categoryModel =null;
        //Integer subid=categoryModel.getId();
        //intent.putExtra("id",subid);
        String categoryName = intent.getStringExtra("categoryName");
        toWhere=intent.getStringExtra("operation");
        loadCategory(categoryName);
    }
    private void loadCategory(String categoryName){

        Call<SubCategoryResponse> call= RetrofitClient
                .getInstance().getApi()
                .getSubCategories(categoryName);
        call.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                if(response.isSuccessful()) {
                    categoryModels = response.body().getSubCategories();
                    subCategoryAdapter = new SubCategoryAdapter(categoryModels,getApplicationContext(),toWhere,categoryName);
                    recyclerView.setAdapter(subCategoryAdapter);
                }
                else{
                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}