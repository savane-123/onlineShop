package com.savane.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.savane.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FaceBookLogin extends AppCompatActivity {

    private LoginButton loginButton;
    TextView email,userName;
    ImageView profileImage;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_book_login);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        email = findViewById(R.id.email);
        userName = findViewById(R.id.userName);
        profileImage =  findViewById(R.id.profile_image);
        loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

//                Profile  profile = Profile.getCurrentProfile();
//                Toast.makeText(FaceBookLogin.this, profile.getId(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FaceBookLogin.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken==null){
                email.setText("");
                userName.setText("");
                profileImage.setImageResource(0);
            }else {
                loadUserProfile(currentAccessToken);
            }

        }
    };

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                String firstName = object.getString("first_name");
                String lastName = object.getString("last_name");
                String stEmail = object.getString("email");
                String userId = object.getString("id");
                String imageUrl = "http://graph.facebook.com/"+ userId + "/picture?type=normal";
                email.setText(stEmail);
                userName.setText(firstName+ " "+lastName);
                Glide.with(FaceBookLogin.this)
                        .load(imageUrl)
                        .into(profileImage);
                RequestOptions requestOptions=new RequestOptions();
                requestOptions.dontAnimate();
            }catch (JSONException e){
                Log.e("Exception",e.getMessage());
            }
            }
        }) ;
        Bundle bundle = new Bundle();
        bundle.putString("fields","first_name,last_name,email,id");
        request.setParameters(bundle);
        request.executeAsync();
    }

}