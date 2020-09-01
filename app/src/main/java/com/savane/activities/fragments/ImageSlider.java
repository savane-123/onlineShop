package com.savane.activities.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.savane.R;
import com.savane.adapters.ImageSliderAdapter;
import com.savane.api.RetrofitClient;
import com.savane.data.model.image.ImageSliderItem;
import com.savane.data.model.image.ImageSliderResponse;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageSlider#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageSlider extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SliderView sliderView ;
    private List<ImageSliderItem> items;

    public ImageSlider() {
        // Required empty public constructor
    }
    public ImageSlider(List<ImageSliderItem> items){
        this.items=items;
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageSlider.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageSlider newInstance(String param1, String param2) {
        ImageSlider fragment = new ImageSlider();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
         sliderView = v.findViewById(R.id.imageSlider);

        loadImageSlider();


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Toast.makeText(getContext(),"onIndicatorClicked: " + sliderView.getCurrentPagePosition(),Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
    private void loadImageSlider(){
        Call<ImageSliderResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllImages();
        call.enqueue(new Callback<ImageSliderResponse>() {
            @Override
            public void onResponse(Call<ImageSliderResponse> call, Response<ImageSliderResponse> response) {
                if (response.isSuccessful()){
                List<ImageSliderItem> items=response.body().getImageSliderItem();
                ImageSliderAdapter adapter = new ImageSliderAdapter(getContext(),items);
                sliderView.setSliderAdapter(adapter);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                sliderView.setIndicatorSelectedColor(Color.WHITE);
                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                sliderView.startAutoCycle();
                }else {
                    Toast.makeText(getContext(),"internal error",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageSliderResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}