package com.savane.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.savane.share.Constant;
import com.savane.R;
import com.savane.activities.items.ItemFulldetails;
import com.savane.share.PopUpClass;
import com.savane.activities.update.EditBike;
import com.savane.activities.update.EditCar;
import com.savane.activities.update.EditItem;
import com.savane.data.model.user.UserAdverisementItems;

import java.util.ArrayList;
import java.util.List;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder> {
    Intent intent;
    Context context;
    List<UserAdverisementItems> items = new ArrayList<>();
  // List<ItemInformation> itemInformations =new ArrayList<>();
    public UserItemAdapter(Context context, List<UserAdverisementItems> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public  UserItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.user_item_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserAdverisementItems  item = items.get(position);
       // ItemInformation itemInformation=itemInformations.get(position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ItemFulldetails.class);
                intent.putExtra("userId",item.getUserId());
                intent.putExtra("itemId",item.getId());
                intent.putExtra("categoryName", item.getCategoryName());
                intent.putExtra("subCategoryId", item.getSubCategoryId());
               // intent.putExtra("state", itemInformation.getState());
                context.startActivity(intent);
            }
        });

        Glide.with(holder.itemView)
                .load(Constant.ITEM_UPLOAD_FOLDER+item.getItemImage())
                .centerCrop()
                .into(holder.itemImg);
        String id=String.valueOf(item.getId());
         holder.itemPrice.setText(id);
         holder.update.setOnClickListener(v -> {

             switch (item.getCategoryName()) {
                 case  "mobile":
                 Intent mobile = new Intent(context, EditItem.class);
                     mobile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     mobile.putExtra("itemId", item.getId());
                     mobile.putExtra("categoryName", item.getCategoryName());
                     mobile.putExtra("subCategoryId", item.getSubCategoryId());
                 context.startActivity(mobile);
                 break;
                 case "car":
                     Intent car=new Intent(context,EditCar.class);
                     car.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     car.putExtra("itemId", item.getId());
                     car.putExtra("categoryName", item.getCategoryName());
                     car.putExtra("subCategoryId", item.getSubCategoryId());
                     context.startActivity(car);
                     break;
                 case "bike":
                     Intent i = new Intent(context, EditBike.class);
                     i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     i.putExtra("itemId", item.getId());
                     i.putExtra("categoryName", item.getCategoryName());
                     i.putExtra("subCategoryId", item.getSubCategoryId());
                     context.startActivity(i);
                     break;
             }

         });
         holder.delete.setOnClickListener(v -> {
             PopUpClass popUpClass = new PopUpClass();
             popUpClass.showPopupWindow(v,item.getId());
         });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImg;
        TextView itemYear,itemBrand,itemPrice;
        ImageButton view,update,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg= itemView.findViewById(R.id.item_img);
            itemPrice=itemView.findViewById(R.id.tv_price);
//            itemYear=itemView.findViewById(R.id.tv_year);
//            itemBrand=itemView.findViewById(R.id.item_brand);
            view=itemView.findViewById(R.id.view);
            update=itemView.findViewById(R.id.update);
            delete=itemView.findViewById(R.id.delet);

        }
    }
}
