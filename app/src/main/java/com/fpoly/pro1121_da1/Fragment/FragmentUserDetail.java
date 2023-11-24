package com.fpoly.pro1121_da1.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fpoly.pro1121_da1.Adapter.UserAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.User;
import com.fpoly.pro1121_da1.spinner.SpinnerRole;

import java.util.ArrayList;


public class FragmentUserDetail extends Fragment {
    private String getID;
    User user;
    UserDAO userDAO;
    ArrayList<User> listUser;
    UserAdapter userAdapter;
    Button btn_delete_user_detail, btn_update_user_detail;
    TextView tv_fullName, tv_dayOfBirth, tv_addRess, tv_role;
    ImageView img_back;
    Spinner spnRole;

    String getRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDAO = new UserDAO(getContext(), new Dbhelper(getContext()));
        listUser = userDAO.getAllUser();
        userAdapter = new UserAdapter(getActivity(), listUser);


        btn_delete_user_detail = view.findViewById(R.id.btn_delete_uer_showDetail);
        btn_update_user_detail = view.findViewById(R.id.btn_update_uer_showDetail);

        img_back = view.findViewById(R.id.img_back_fragmentUserDetail);
        tv_fullName = view.findViewById(R.id.tv_fullName_showDetail);
        tv_dayOfBirth = view.findViewById(R.id.tv_dayOfBirth_showDetail);
        tv_addRess = view.findViewById(R.id.tv_addRess_showDetail);
        tv_role = view.findViewById(R.id.tv_role_showDetail);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentHome());
            }
        });
        getParentFragmentManager().setFragmentResultListener("KEY_USER", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                getID = result.getString("KEY_USER_ID");
                user = userDAO.getUserByID(getID);

                tv_fullName.setText("Họ và tên:" + user.getFullName());
                tv_dayOfBirth.setText("Ngày sinh:" + user.getDateOfBirth());
                tv_addRess.setText("Địa chỉ:" + user.getAddress());
                tv_role.setText("Chức vụ:" + user.getRole());

                btn_delete_user_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Bạn có muốn xóa người dùng không ?");
                        builder.setTitle("Cảnh Báo");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userDAO.deleteUser(user, "Xóa user thành công", "Xóa user không thành công");
                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.show();

                    }
                });
                btn_update_user_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogUpdateUser(user);

                    }
                });
            }

        });

    }

    EditText edtFullName, edtAddress, edtDayOfBrith, edtPhoneNumber;

    public void showDialogUpdateUser(User user) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_user, null, false);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        edtFullName = view.findViewById(R.id.edt_NameUser_update_dialog);
        edtAddress = view.findViewById(R.id.edt_AddRess_update_dialog);
        edtDayOfBrith = view.findViewById(R.id.edt_dayOfBirth_update_dialog);

        edtPhoneNumber = view.findViewById(R.id.edt_phone_update_dialog);
        Button edt_update = view.findViewById(R.id.btn_update_uer_dialog);


        edtFullName.setText(user.getFullName());
        edtAddress.setText(user.getAddress());
        edtDayOfBrith.setText(user.getDateOfBirth());
        edtPhoneNumber.setText(user.getPhoneNumber());


        edt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtFullName.getText().toString().trim();
                String Address = edtAddress.getText().toString().trim();
                String dayOfBirth = edtDayOfBrith.getText().toString().trim();
                String getPhoneNumber = edtPhoneNumber.getText().toString().trim();
                if (userDAO.updateInForMation(user.getUserID(), name, dayOfBirth, Address, getPhoneNumber)) {
                    tv_fullName.setText("Họ và Tên:" + name);
                    tv_addRess.setText("Địa chỉ:" + Address);
                    tv_dayOfBirth.setText("Ngày sinh:" + dayOfBirth);
                    tv_role.setText("Số điện thoại:" + getPhoneNumber);
                }

                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

    public void setDefaulRole(String arrRole[], String role) {
        for (int i = 0; i < arrRole.length; i++) {
            if (role.equals(arrRole[i])) {
                spnRole.setSelection(i);
            }
        }
    }
}