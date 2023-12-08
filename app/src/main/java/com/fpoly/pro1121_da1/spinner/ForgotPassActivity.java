package com.fpoly.pro1121_da1.spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Fragment.FragmentTable;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.Booking;
import com.fpoly.pro1121_da1.model.User;

import java.util.Map;

public class ForgotPassActivity extends AppCompatActivity {

    //edt_userNameForgotPass
    //edt_cardIDForgotPass
    //btn_confirmForgot
    EditText edtUserName, edtCard;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        UserDAO userDAO = new UserDAO(getApplicationContext(), new Dbhelper(getApplicationContext()));
        edtUserName = findViewById(R.id.edt_userNameForgotPass);
        edtCard = findViewById(R.id.edt_cardIDForgotPass);
        btnConfirm = findViewById(R.id.btn_confirmForgot);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUserID = edtCard.getText().toString().trim();
                String getUserName = edtUserName.getText().toString().trim();
                if (getUserID.length() == 0) {
                    Toast.makeText(ForgotPassActivity.this, "Không được để trống CCCD", Toast.LENGTH_SHORT).show();
                } else if (getUserName.length() == 0) {
                    Toast.makeText(ForgotPassActivity.this, "Không được để trống tên đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    if (userDAO.checkAccountForgot(getUserID, getUserName)) {
                        showChangePass(getUserID);
                    }
                }
            }
        });
    }

    EditText edtNewPass;
    Button btnConfirmChange;


    public void showChangePass(String userID) {
        final Dialog view1 = new Dialog(ForgotPassActivity.this);
        view1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view1.setContentView(R.layout.alertdialog_forgotpass);
        edtNewPass = view1.findViewById(R.id.edtPassNew);
        btnConfirmChange = view1.findViewById(R.id.btnconfirmchangepass);
        UserDAO userDAO = new UserDAO(getApplicationContext(), new Dbhelper(getApplicationContext()));
        btnConfirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNewPass = edtNewPass.getText().toString().trim();
                if (getNewPass.length() == 0) {
                    Toast.makeText(ForgotPassActivity.this, "Không được để trống mật khẩu mới !", Toast.LENGTH_SHORT).show();
                } else {
                    if (userDAO.updatePassWordUser(userID, getNewPass, "Lấy lại mật khẩu thành công", "Lấy lại mật khẩu thất bại")) {
                        finish();
                    }
                }
            }
        });
        view1.show();
        view1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view1.getWindow().getAttributes().windowAnimations = R.style.Style_AlertDialog_Corner;
        view1.getWindow().setGravity(Gravity.BOTTOM);

    }
}