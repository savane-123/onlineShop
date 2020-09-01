package com.savane.activities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.savane.R;
import com.savane.activities.user.UpdateUser;
import com.savane.activities.user.UploadImage;
import com.savane.activities.user.UserProfile;


public class settingFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_setting,container,false);
        Button updatebtn =v.findViewById(R.id.updateuser);
        Button uploadImagebtn=v.findViewById(R.id.uploadImage);
        Button  details=v.findViewById(R.id.details);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), UpdateUser.class);
                startActivity(i);
            }
        });
        uploadImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), UploadImage.class);
                startActivity(in);
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte =new Intent(getActivity(), UserProfile.class);
                startActivity(inte);
            }
        });
       return v;
    }
}
