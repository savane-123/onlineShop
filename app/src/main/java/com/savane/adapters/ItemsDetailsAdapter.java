package com.savane.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.savane.R;
import com.savane.activities.items.ItemFulldetails;
import com.savane.data.model.item.ItemInformation;
import com.savane.share.Constant;

import java.util.ArrayList;
import java.util.List;

public class ItemsDetailsAdapter extends RecyclerView.Adapter<ItemsDetailsAdapter.ViewHolder> {
    Context context;
    List<ItemInformation> items = new ArrayList<>();

    public ItemsDetailsAdapter(Context context, List<ItemInformation> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.items_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsDetailsAdapter.ViewHolder holder, int position) {
        ItemInformation item=items.get(position);
        holder.itemPrice.setText(item.getPrice());
        holder.address.setText(item.getState());
        Glide.with(context)
                .load(Constant.ITEM_UPLOAD_FOLDER+item.getImage())
                .centerCrop()
                .into(holder.itemImg);
        holder.mainLinearLayout.setOnClickListener(v -> {
            Intent intent=new Intent(context, ItemFulldetails.class);
            switch (item.getCategoryName()){
                case "bike":
                    intent.putExtra("isCarOrBike",true);
                    break;
                case "car":
                    intent.putExtra("isCarOrBike", true);
                    break;
                default:
                    intent.putExtra("isCarOrBike",false);
                    break;
            }
            intent.putExtra("categoryName",item.getCategoryName());
            intent.putExtra("itemId",item.getId());
//            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImg;
        TextView  itemPrice,ownerName,address;
        LinearLayout mainLinearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg= itemView.findViewById(R.id.item_img);
            itemPrice=itemView.findViewById(R.id.item_price);
            ownerName=itemView.findViewById(R.id.owner_name);
            address=itemView.findViewById(R.id.addresse);
            mainLinearLayout = itemView.findViewById(R.id.lin_main);
        }
    }
}
