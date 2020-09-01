package com.savane.activities.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.savane.R;
import com.savane.adapters.CategoryAdapter;
import com.savane.api.RetrofitClient;
import com.savane.data.model.category.CategoryModel;
import com.savane.data.model.category.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categoaries extends AppCompatActivity {
    private   Intent intent;
       TextView tvId,tvStatus;
       RecyclerView recyclerView;
       private List<CategoryModel> categoryModels;
       CategoryAdapter categoryAdapter;
    String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoaries);
        recyclerView = findViewById(R.id.recycl_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        loadCategory();
        intent = getIntent();
        categoryName = intent.getStringExtra("categoryName");

    }
    private void loadCategory(){

        Call<CategoryResponse> call= RetrofitClient
                .getInstance().getApi()
                .getCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()) {
                    categoryModels = response.body().getCategories();
                    categoryAdapter = new CategoryAdapter(categoryModels,getApplicationContext());
                    recyclerView.setAdapter(categoryAdapter);
                    intent.putExtra(categoryName,"categoryName");
                }
                else{
                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}