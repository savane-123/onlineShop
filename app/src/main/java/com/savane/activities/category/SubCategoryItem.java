package com.savane.activities.category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.savane.R;
import com.savane.adapters.ItemsDetailsAdapter;
import com.savane.api.RetrofitClient;
import com.savane.data.model.item.ItemInformation;
import com.savane.data.model.item.ItemInformationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryItem extends AppCompatActivity {
    Intent intent;
    RecyclerView recyclerView;
    ItemsDetailsAdapter  detailsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_item);
        intent = getIntent();
        int subId=intent.getIntExtra("subId",0);
        recyclerView=findViewById(R.id.get_item_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        loadDataToRecycle(subId);
    }
    private void loadDataToRecycle(int subId){
        Call<ItemInformationResponse>call= RetrofitClient
                .getInstance()
                .getApi()
                .getSubCategoyItems(subId);
           call.enqueue(new Callback<ItemInformationResponse>() {
               @Override
               public void onResponse(Call<ItemInformationResponse> call, Response<ItemInformationResponse> response) {
                   if(response.isSuccessful()){
                       List<ItemInformation> items= response.body().getItems();
                       detailsAdapter=new ItemsDetailsAdapter(getApplicationContext(),items);
                       recyclerView.setAdapter(detailsAdapter);
                   }
                   else {
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
