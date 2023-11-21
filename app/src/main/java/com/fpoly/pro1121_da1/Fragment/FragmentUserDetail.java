package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.pro1121_da1.Adapter.UserAdapter;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;


public class FragmentUserDetail extends Fragment {
    private String getID;
    User user;
    UserDAO userDAO;
    ArrayList<User> listUser;
    UserAdapter userAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDAO = new UserDAO(getContext(), new Dbhelper(getContext()));

        getParentFragmentManager().setFragmentResultListener("KEY_USER", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                getID = result.getString("KEY_ING_ID");
                user = userDAO.getUserByID(getID);
                userDAO.deleteUser(user,"Xóa user thành công","Xóa user không thành công");

            }
        });
    }
}