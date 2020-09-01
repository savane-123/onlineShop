package com.savane.activities.user;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.savane.R;
import com.savane.activities.map.UpdateMapActivity;
import com.savane.data.model.User;
import com.savane.share.Constant;
import com.savane.storage.SharedPrefManager;

public class UserProfile extends Fragment {
    Button editProfile,ediAddress,editPassword;
    RoundedImageView profileImage;
    TextView userName,email;
    View rootView;
    LinearLayout linearLayout,updateLinear;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_profile,container,false);
        profileImage=rootView.findViewById(R.id.profile_image);
        userName=rootView.findViewById(R.id.full_name);
        email=rootView.findViewById(R.id.email);
        editProfile=rootView.findViewById(R.id.edit_profile);
        ediAddress=rootView.findViewById(R.id.edit_address);
        editPassword=rootView.findViewById(R.id.edit_password);
        linearLayout=rootView.findViewById(R.id.ad_linear);
        updateLinear=rootView.findViewById(R.id.update_linear);
        User user= SharedPrefManager.getInstance(getContext()).getUser();
        String fullName = user.getFirstName().toUpperCase() + " " + user.getLastName().toUpperCase();
        userName.setText(fullName);
        email.setText(user.getEmail());
          editPassword.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i= new Intent(getContext(), UpdatePassword.class);
                  startActivity(i);
              }
          });
            ediAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(getContext(), UpdateMapActivity.class);
                    startActivity(i);
                }
            });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editProfile.getText().equals("Edit Profile")){
                    FragmentManager fragmentManager =  UserProfile.this.getChildFragmentManager();
                    FragmentTransaction fragmentTransaction;
                    Fragment fragment=new UpdateUser();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_contenair,fragment);
                    fragmentTransaction.commit();
                    String text = "My AD";
                    editProfile.setText(text);
                    Drawable img = getContext().getResources().getDrawable(R.drawable.phonemob);
                    img.setBounds(0, 0, 60, 60);
                    editProfile.setCompoundDrawables(null, null, img, null);
                }else {
                    FragmentManager fragmentManager =  UserProfile.this.getChildFragmentManager();
                    FragmentTransaction fragmentTransaction;
                    Fragment fragment=new UserItem();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_contenair,fragment);
                    fragmentTransaction.commit();
                    String text = "Edit Profile";
                    editProfile.setText(text);
                    Drawable img = getContext().getResources().getDrawable(R.drawable.edit_icon);
                    img.setBounds(0, 0, 60, 60);
                    editProfile.setCompoundDrawables(null, null, img, null);
                }
            }
        });

        Toast.makeText(getContext(), user.getProfileImage(), Toast.LENGTH_SHORT).show();
                Glide.with(getContext())
                .load(Constant.UPLOAD_FOLDER+user.getProfileImage())
                .fitCenter()
                .into(profileImage);
        profileImage.setOnClickListener(v -> {
                Intent intent=new Intent(rootView.getContext(),UploadImage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("from","profile");
            startActivity(intent);
        });
        return  rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager =  UserProfile.this.getChildFragmentManager();
        FragmentTransaction fragmentTransaction;
        Fragment fragment=new UserItem();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_contenair,fragment);
        fragmentTransaction.commit();
    }
     private void loadUserInfo(){

     }
}