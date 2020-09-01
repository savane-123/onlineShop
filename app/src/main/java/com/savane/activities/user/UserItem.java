package com.savane.activities.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.savane.R;
import com.savane.adapters.UserItemAdapter;
import com.savane.api.RetrofitClient;
import com.savane.data.model.User;
import com.savane.data.model.user.UserAdverisementItems;
import com.savane.data.model.user.UserItemResponse;
import com.savane.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserItem extends Fragment {
    private View rootView;
    RecyclerView recyclerView;
    UserItemAdapter userItemAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserItem.
     */
    // TODO: Rename and change types and number of parameters
    public static UserItem newInstance(String param1, String param2) {
        UserItem fragment = new UserItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_user_item, container, false);
         recyclerView=rootView.findViewById(R.id.user_item_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        loadData();
        return rootView;
    }
    private void loadData(){
        User user= SharedPrefManager.getInstance(getContext()).getUser();
        Call<UserItemResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .getItemByUserId(user.getId());
        call.enqueue(new Callback<UserItemResponse>() {
            @Override
            public void onResponse(Call<UserItemResponse> call, Response<UserItemResponse> response) {
                if (response.isSuccessful()){
                    List<UserAdverisementItems> userItems =new ArrayList<>();
                    userItems=response.body().getUserAd();
                    userItemAdapter=new UserItemAdapter(getContext(),userItems);
                    recyclerView.setAdapter(userItemAdapter);
                }else {
                    Toast.makeText(rootView.getContext(),"internal srver error"+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserItemResponse> call, Throwable t) {
                Toast.makeText(rootView.getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}