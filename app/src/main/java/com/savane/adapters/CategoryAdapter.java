package com.savane.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.savane.R;
import com.savane.activities.category.SubCategory;
import com.savane.data.model.category.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categories;
    Context context;

    public CategoryAdapter(List<CategoryModel> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.category_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryModel categoryModel = categories.get(position);
        holder.textName.setText(categoryModel.getName());
        Integer categoryId = categoryModel.getId();
        holder.textName.setOnClickListener((View v)->{
            Intent i = new Intent(context, SubCategory.class);
            i.putExtra("categoryId",categoryId);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
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
