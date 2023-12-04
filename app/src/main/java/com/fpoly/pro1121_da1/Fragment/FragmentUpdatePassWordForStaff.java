package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;


public class FragmentUpdatePassWordForStaff extends Fragment {

    ImageView img_back;
    EditText edt_UserName, edt_NewPassWord, edt_ID_CARD_USER;
    Button btn_ConfirmUpdatePassWord;
    UserDAO userDAO;
    ArrayList<User> userArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_pass_word_for_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = ((MainActivity)requireActivity()).user;
        edt_UserName = view.findViewById(R.id.edt_userName_Update_PassWordForStaff);
        edt_NewPassWord = view.findViewById(R.id.edt_NewPassWord_PassWordForStaff);
        edt_ID_CARD_USER = view.findViewById(R.id.edt_id_Car_PassWordForStaff);

        btn_ConfirmUpdatePassWord = view.findViewById(R.id.btn_confirm_updatePassWordForStaff);
        img_back = view.findViewById(R.id.img_backFramentSetting);

        userDAO = new UserDAO(getContext(),new Dbhelper(getContext()));
        userArrayList = userDAO.getAllUser();

        edt_UserName.setText(user.getUserName());
        edt_NewPassWord.setText(user.getPassWord());
        edt_ID_CARD_USER.setText(user.getUserID());

        btn_ConfirmUpdatePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = edt_UserName.getText().toString().trim();
                String getPassWord = edt_NewPassWord.getText().toString().trim();
                String getIdUser = edt_ID_CARD_USER.getText().toString().trim();
                boolean checkUserName = userDAO.checkUserName(getName);
                boolean checkIdUser = userDAO.checkUpdatePassWordByID(getIdUser);


                if (getName.equals("") || getPassWord.equals("") || getIdUser.equals("")){
                    Toast.makeText(getContext(), "Không được để chống", Toast.LENGTH_SHORT).show();
                } else if (!checkUserName) {
                    Toast.makeText(getContext(), "Tên Đăng Nhập Không Đúng", Toast.LENGTH_SHORT).show();
                } else if (!checkIdUser) {
                    Toast.makeText(getContext(), "Số Id Card không khớp", Toast.LENGTH_SHORT).show();
                }else{
                    userDAO.updatePassWordUser(getIdUser,getPassWord,"Update PassWord Thành Công","Update PassWord Không Thành Công");
                }

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentSettingsStaff());
            }
        });
    }
}