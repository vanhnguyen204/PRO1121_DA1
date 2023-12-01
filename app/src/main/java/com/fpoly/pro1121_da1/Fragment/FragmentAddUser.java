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
    EditText edtCCCD, edtUserName, edtPassWord, edtFullName, edtDateOfBirth, edtAddress, edtPhoneNumber;
    Spinner spinner;
    UserDAO userDAO;
    Button btnConfirmAddUser;
    ImageView imgBack;
    String getCCCD, getCalendarID, getUserName, getPassWord, getFullName, getDateOfBirth, getAddress, getRole, getPhoneNumber;

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

        edtUserName = view.findViewById(R.id.edt_addUserName_fragmentAddUser);
        edtPassWord = view.findViewById(R.id.edt_addPassWord_fragmentAddUser);
        edtFullName = view.findViewById(R.id.edt_addFullName_fragmentAddUser);
        edtDateOfBirth = view.findViewById(R.id.edt_addDateOfBirth_fragmentAddUser);
        edtAddress = view.findViewById(R.id.edt_addAddress_fragmentAddUser);
        edtPhoneNumber = view.findViewById(R.id.edt_phoneNumber_fragmentAddUser);
        btnConfirmAddUser = view.findViewById(R.id.btnConfirmAddUser_fragmentAddUser);
        imgBack = view.findViewById(R.id.img_back_fragmentAddUser);

        btnConfirmAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCCCD = edtCCCD.getText().toString().trim();

                getUserName = edtUserName.getText().toString().trim();
                getPassWord = edtPassWord.getText().toString().trim();
                getFullName = edtFullName.getText().toString().trim();
                getDateOfBirth = edtDateOfBirth.getText().toString().trim();
                getAddress = edtAddress.getText().toString().trim();
                getPhoneNumber = edtPhoneNumber.getText().toString().trim();
                if (checkCCCD(edtCCCD)) {

                } else if (checkUserName(edtUserName)) {

                } else if (checkPassWord(edtPassWord)) {

                } else if (getFullName.length() == 0) {
                    Toast.makeText(getContext(), "Không được bỏ trống họ tên", Toast.LENGTH_SHORT).show();
                } else if (getDateOfBirth.length() == 0) {
                    Toast.makeText(getContext(), "Không được bỏ trống ngày sinh", Toast.LENGTH_SHORT).show();
                } else if (getAddress.length() == 0) {
                    Toast.makeText(getContext(), "Không được bỏ trống địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (checkPhoneNumber(edtPhoneNumber)) {
                    
                } else {
                    User user = new User(getCCCD, getUserName, getPassWord, getFullName, getDateOfBirth, getAddress, "staff", 0, getPhoneNumber);
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

    public boolean checkCCCD(EditText editText) {
        String getCCCD = editText.getText().toString().trim();
        if (getCCCD.length() == 0) {
            Toast.makeText(getContext(), "Không được bỏ trống căn cước công dân", Toast.LENGTH_SHORT).show();
            return true;
        } else if (getCCCD.length() != 12) {
            Toast.makeText(getContext(), "Căn cước công dân phải có 12 số", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserName(EditText editText) {
        String getUserName = editText.getText().toString().trim();
        if (getUserName.length() == 0){
            Toast.makeText(getContext(), "Không được để trống tên đăng nhập", Toast.LENGTH_SHORT).show();
            return true;
        } else if (getUserName.length() > 20) {
            Toast.makeText(getContext(), "Tên đăng nhập không được quá 20 ký tự", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }
    }

    public boolean checkPassWord(EditText editText){
        String getPass = editText.getText().toString().trim();
        if (getPass.length() == 0){
            Toast.makeText(getContext(), "Không được để trống mật khẩu", Toast.LENGTH_SHORT).show();
            return true;
        }else if(getPass.length() < 8 || getPass.length() > 16){
            Toast.makeText(getContext(), "Mật khẩu chỉ được 8 - 16 ký tự", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }

    }

    
    public boolean checkPhoneNumber(EditText editText){
        String getPhone = editText.getText().toString().trim();
        if (getPhone.length() == 0){
            Toast.makeText(getContext(), "Không được để trống số điện thoại", Toast.LENGTH_SHORT).show();
            return true;
        }else if (getPhone.length() != 10){
            Toast.makeText(getContext(), "Số điện thoại phải là 10 số", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return false;
        }
    }
}