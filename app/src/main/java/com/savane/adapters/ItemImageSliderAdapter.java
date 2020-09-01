package com.savane.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.savane.R;
import com.savane.share.Constant;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public  class ItemImageSliderAdapter extends SliderViewAdapter<ItemImageSliderAdapter.SliderAdapterVH> {
    private Context context;
    private List<String> images;

    public ItemImageSliderAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    public void renewItems(List<String> images) {
        this.images= images;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.images.remove(position);
        notifyDataSetChanged();
    }
    public void addItem(String sliderItem) {
        this.images.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slider_content, null);
        return new ItemImageSliderAdapter.SliderAdapterVH(inflate);
    }

    public void onBindViewHolder(ItemImageSliderAdapter.SliderAdapterVH viewHolder, int position) {
        String itemImage = images.get(position);

        viewHolder.description.setText(itemImage);
        viewHolder.description.setTextSize(16);
        viewHolder.description.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(Constant.ITEM_UPLOAD_FOLDER+itemImage)
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public int getCount() {
        return images.size();
    }
    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View imageView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView description;

        public SliderAdapterVH(View imageView) {
            super(imageView);
            imageViewBackground = imageView.findViewById(R.id.item_iv_auto_image_slider);
            imageGifContainer = imageView.findViewById(R.id.item_iv_gif_container);
            description = imageView.findViewById(R.id.item_tv_auto_image_slider);
            this.imageView = imageView;
        }
    }
}
