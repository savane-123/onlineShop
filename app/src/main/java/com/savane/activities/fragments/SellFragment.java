package com.savane.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.savane.R;
import com.savane.activities.category.SubCategory;

public class SellFragment extends Fragment {
       ImageView im_car,im_bike,im_mobile,im_electronic;
       Intent intent,i;
    private static final String sellIteam= "operation";
    private static final String operatrion = "sellItems";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_sell,container,false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       setRetainInstance(true);
        im_car=view.findViewById(R.id.img_car);
        im_bike=view.findViewById(R.id.img_bike);
        im_electronic=view.findViewById(R.id.img_electronic);
        im_mobile=view.findViewById(R.id.img_mobile);
        i = getActivity().getIntent();
        intent=new Intent(getContext(), SubCategory.class);
        intent.putExtra(sellIteam,operatrion);
        String categoryName = "categoryName";
        im_car.setOnClickListener(v -> {
            intent.putExtra(categoryName,"car");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        im_bike.setOnClickListener(v -> {
            intent.putExtra(categoryName,"bike");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        im_electronic.setOnClickListener(v -> {
            intent.putExtra(categoryName,"electronic");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        im_mobile.setOnClickListener(v -> {
            intent.putExtra(categoryName,"mobile");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

  return view;
    }
}
