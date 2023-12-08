package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fpoly.pro1121_da1.Adapter.DrinkAdapter;
import com.fpoly.pro1121_da1.Adapter.UserAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FragmentUser extends Fragment {

    EditText edt_search_User;
    FloatingActionButton floatingActionButton;
    RecyclerView rcv;
    UserDAO userDAO;
    ArrayList<User> list;
    UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDAO = new UserDAO(getContext(), new Dbhelper(getContext()));
        edt_search_User = view.findViewById(R.id.edt_search_User);
        rcv = view.findViewById(R.id.rcv_user);
        floatingActionButton = view.findViewById(R.id.btnAddUser);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentAddUser());
            }
        });
        list = userDAO.getUser("SELECT * FROM User WHERE role <> 'admin' AND status <> 1");

        userAdapter = new UserAdapter(getActivity(), list);
        rcv.setAdapter(userAdapter);

        ArrayList<User> listClone = userDAO.getAllUser();
        edt_search_User.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();

                String getTextFromEdt = charSequence.toString().trim();
                if (getTextFromEdt.length() == 0) {
                    for (int j = 0; j < listClone.size(); j++) {
                        if (listClone.get(i).getRole().equals("admin") || list.get(i).getStatus() == 1) {
                            listClone.remove(i);
                        }
                    }
                    rcv.setAdapter(new UserAdapter(getActivity(), listClone));
                } else {
                    for (int index = 0; index < listClone.size(); index++) {
                        if (listClone.get(index).getFullName().contains(getTextFromEdt)) {

                            list.add(listClone.get(index));
                        }
                    }
                    rcv.setAdapter(new UserAdapter(getActivity(), list));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}