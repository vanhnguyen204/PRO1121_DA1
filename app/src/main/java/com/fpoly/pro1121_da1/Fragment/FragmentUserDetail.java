package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.fpoly.pro1121_da1.Adapter.UserAdapter;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;


public class FragmentUserDetail extends Fragment {

    EditText edt_search_User;
    RecyclerView rcv;
    UserDAO userDAO;
    ArrayList<User> list;
    UserAdapter userAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_search_User = view.findViewById(R.id.edt_search_User);
        rcv = view.findViewById(R.id.rcv_user);

        list = userDAO.getAllUser();
        userAdapter = new UserAdapter(getActivity(),list);
        rcv.setAdapter(userAdapter);

    }
}