package com.savane.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savane.activities.items.Bike;
import com.savane.R;
import com.savane.activities.category.SubCategoryItem;
import com.savane.activities.sell.SellCars;
import com.savane.activities.sell.SellItem;
import com.savane.data.model.category.CategoryModel;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {


    private List<CategoryModel> categories;
    Context context;
    private String toWhere;
    private String categoryName;
    public SubCategoryAdapter(List<CategoryModel> categories, Context context,String toWhere,String categoryName) {
        this.categories = categories;
        this.context = context;
        this.toWhere= toWhere;
        this.categoryName=categoryName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.category_list, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel categoryModel = categories.get(position);
        holder.textName.setText(categoryModel.getName());
        holder.textName.setOnClickListener(v -> {
            if (toWhere.equals("getItem")){
                Intent intent=new Intent(context, SubCategoryItem.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("subId",categoryModel.getId());
                intent.putExtra("categoryName",categoryModel.getName());
                context.startActivity(intent);
            }else if(toWhere.equals("sellItems") ){
                Intent intent;
                switch (categoryName){
                    case "car":
                        intent = new Intent(context, SellCars.class);
                        intent.putExtra("subId",categoryModel.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("categoryName",categoryModel.getName());
                        context.startActivity(intent);
                        break;
                    case "bike":
                        intent = new Intent(context, Bike.class);
                        intent.putExtra("subId",categoryModel.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("categoryName",categoryModel.getName());
                        context.startActivity(intent);
                        break;
                    default:
                        intent = new Intent(context, SellItem.class);
                        intent.putExtra("subId",categoryModel.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("categoryName",categoryModel.getName());
                        context.startActivity(intent);
                        break;

                }
            }


        });

    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.tv_name);
        }
    }
}
