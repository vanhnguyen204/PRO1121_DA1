package com.fpoly.pro1121_da1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.User;
import com.fpoly.pro1121_da1.spinner.ForgotPassActivity;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser, edtPassWord;
    TextView tvForgotPass;
    Button btnConfirmLogin;


    public void findID() {
        edtUser = findViewById(R.id.edt_user_login);
        edtPassWord = findViewById(R.id.edt_password_login);
        tvForgotPass = findViewById(R.id.tv_forgotpass_login);
        btnConfirmLogin = findViewById(R.id.btn_confirm_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findID();
        UserDAO userDAO = new UserDAO(getApplicationContext(), new Dbhelper(LoginActivity.this));
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });
        btnConfirmLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUserName = edtUser.getText().toString().trim();
                String getPassWord = edtPassWord.getText().toString().trim();
                if (getUserName.length() == 0){
                    Toast.makeText(LoginActivity.this, "Không được để trống tên đăng nhập !", Toast.LENGTH_SHORT).show();
                }else if (getPassWord.length() == 0){
                    Toast.makeText(LoginActivity.this, "Không được để trống mật khẩu !", Toast.LENGTH_SHORT).show();
                }else if (userDAO.checkUserLogin(getUserName, getPassWord)){
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                }else {
                   User user = userDAO.getUserUserName(getUserName);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("KEY_USER_NAME", user);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                    edtUser.setText("");
                    edtPassWord.setText("");
                    finish();
                }

            }
        });
    }
}