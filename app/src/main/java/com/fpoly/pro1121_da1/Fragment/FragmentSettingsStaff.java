package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.User;

public class FragmentSettingsStaff extends Fragment {
    ImageView imgChangePass, imgChangeInforMation, imgShowHistoryExport, imgShowMyWork;
    TextView tvNameStaff;
    Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgChangePass = view.findViewById(R.id.img_changePassWord_fragmentSettings);
        imgChangeInforMation = view.findViewById(R.id.img_changeInfor);
        imgShowHistoryExport = view.findViewById(R.id.img_showHistory_export);
        imgShowMyWork = view.findViewById(R.id.img_gotoMywork_fragmentSettingStaff);
        btnLogout = view.findViewById(R.id.btnLogoutFragmentSettingsStaff);
        tvNameStaff = view.findViewById(R.id.tv_nameOfStaff_setting);
        User user = ((MainActivity) requireActivity()).user;
        tvNameStaff.setText(user.getFullName());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).finish();
            }
        });


    }
}