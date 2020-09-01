package com.savane.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.savane.R;
import com.savane.activities.category.SubCategory;
import com.savane.adapters.ItemsDetailsAdapter;
import com.savane.api.RetrofitClient;
import com.savane.data.model.item.ItemInformation;
import com.savane.data.model.item.ItemInformationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ImageView im_car,im_bike,im_mobile,im_electronic;
    private View rootView;
    private Intent intent;
    RecyclerView recyclerView;
    ItemsDetailsAdapter itemsDetailsAdapter;
    private static final String getIteam= "getItem";
    private static final String operatrion = "operation";
    CardView categoryCard;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_home,container,false);
         getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
         setRetainInstance(true);
         im_car=rootView.findViewById(R.id.img_car);
         im_bike=rootView.findViewById(R.id.img_bike);
         im_mobile=rootView.findViewById(R.id.img_mobile);
         im_electronic=rootView.findViewById(R.id.img_electronic);
         categoryCard=rootView.findViewById(R.id.category_card);
         recyclerView=rootView.findViewById(R.id.item_recycle_view);
         recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        loadDataToRecycle();

        intent=new Intent(getContext(), SubCategory.class);
        String categoryName="categoryName";
         im_bike.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 intent.putExtra(operatrion,getIteam);
                 intent.putExtra(categoryName,"Bike");
                 intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }
         });
         im_car.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 intent.putExtra(operatrion,getIteam);
                 intent.putExtra(categoryName,"Car");
                 intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }
         });
         im_electronic.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 intent.putExtra(operatrion,getIteam);
                 intent.putExtra(categoryName,"Electronic");
                 intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }
         });

         im_mobile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 intent.putExtra(operatrion,getIteam);
                 intent.putExtra(categoryName,"Mobile");
                 intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
             }
         });
         return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FragmentManager fragmentManager =  HomeFragment.this.getChildFragmentManager();
        FragmentTransaction fragmentTransaction;
        Fragment fragment=new ImageSlider();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.image_view_frame,fragment);
        fragmentTransaction.commit();
        super.onCreate(savedInstanceState);
    }
    private void loadDataToRecycle(){
        Call<ItemInformationResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getItems();
        call.enqueue(new Callback<ItemInformationResponse>() {
            @Override
            public void onResponse(Call<ItemInformationResponse> call, Response<ItemInformationResponse> response) {
                if (response.isSuccessful()){
                    List<ItemInformation> items= response.body().getItems();
                    itemsDetailsAdapter=new ItemsDetailsAdapter(getContext(),items);
                    recyclerView.setAdapter(itemsDetailsAdapter);
                }else {
                    Toast.makeText(getContext(),"internal srver error"+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ItemInformationResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();


            }
        });
    }

    public void anim(){
        Animation categoryCardAnim = AnimationUtils.loadAnimation(getContext(),R.anim.top_down);
        categoryCard.startAnimation(categoryCardAnim);
//        Animation lineTop = AnimationUtils.loadAnimation(getContext(),R.anim.top_down);
//        topLine.startAnimation(lineTop);
//        Animation bnv = AnimationUtils.loadAnimation(getContext(),R.anim.down_top);
//        .startAnimation(bnv);


//        Animation editText_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.edittext_anim);
//        email.startAnimation(editText_anim);
//        password.startAnimation(editText_anim);
//
//        Animation field_name_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.field_name_anim);
//        email_text.startAnimation(field_name_anim);
//        password_text.startAnimation(field_name_anim);
//        logo.startAnimation(field_name_anim);
//        login_title.startAnimation(field_name_anim);
//
//        Animation center_reveal_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.center_reveal_anim);
//        login_card.startAnimation(center_reveal_anim);
//
//        Animation new_user_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down_top);
//        new_user_layout.startAnimation(new_user_anim);
    }
}
