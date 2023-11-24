package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.User;
import com.fpoly.pro1121_da1.spinner.SpinnerRole;


public class FragmentAddUser extends Fragment {
    EditText edtCCCD, edtCalendarID, edtUserName, edtPassWord, edtFullName, edtDateOfBirth, edtAddress;
    Spinner spinner;
    UserDAO userDAO;
    Button btnConfirmAddUser;
    ImageView imgBack;
    String getCCCD, getCalendarID, getUserName, getPassWord, getFullName, getDateOfBirth, getAddress, getRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDAO = new UserDAO(getContext(), new Dbhelper(getContext()));
        edtCCCD = view.findViewById(R.id.edt_addCCCD_fragmentAddUser);
        edtCalendarID = view.findViewById(R.id.edt_addCalendarID_fragmentAddUser);
        edtUserName = view.findViewById(R.id.edt_addUserName_fragmentAddUser);
        edtPassWord = view.findViewById(R.id.edt_addPassWord_fragmentAddUser);
        edtFullName = view.findViewById(R.id.edt_addFullName_fragmentAddUser);
        edtDateOfBirth = view.findViewById(R.id.edt_addDateOfBirth_fragmentAddUser);
        edtAddress = view.findViewById(R.id.edt_addAddress_fragmentAddUser);
        spinner = view.findViewById(R.id.spinner_addRole_fragmentAddUser);
        btnConfirmAddUser = view.findViewById(R.id.btnConfirmAddUser_fragmentAddUser);
        imgBack = view.findViewById(R.id.img_back_fragmentAddUser);
        String role[] = new String[]{"Quản lý", "Nhân viên"};
        SpinnerRole spinnerRole = new SpinnerRole(role, getActivity());
        spinner.setAdapter(spinnerRole);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getRole = role[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnConfirmAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCCCD = edtCCCD.getText().toString().trim();
                getCalendarID = edtCalendarID.getText().toString().trim();
                getUserName = edtUserName.getText().toString().trim();
                getPassWord = edtPassWord.getText().toString().trim();
                getFullName = edtFullName.getText().toString().trim();
                getDateOfBirth = edtDateOfBirth.getText().toString().trim();
                getAddress = edtAddress.getText().toString().trim();

                if (getCCCD.length() == 0) {

                } else if (getCalendarID.length() == 0) {

                } else if (getUserName.length() == 0) {

                } else if (getPassWord.length() == 0) {

                } else if (getFullName.length() == 0) {

                } else if (getDateOfBirth.length() == 0) {

                } else if (getAddress.length() == 0) {

                } else {
                    User user = new User(getCCCD, Integer.parseInt(getCalendarID), getUserName, getPassWord, getFullName, getDateOfBirth, getAddress, getRole,0);
                    if (userDAO.insertUser(user, "Tạo người dùng thành công", "Tạo người dùng thất bại")) {

                        ((MainActivity) getActivity()).reloadFragment(new FragmentAddUser());


                    }
                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity) getActivity()).reloadFragment(new FragmentSettings());
            }
        });
    }


}