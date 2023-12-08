package com.fpoly.pro1121_da1.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fpoly.pro1121_da1.LoginActivity;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.model.User;

public class FragmentSettings extends Fragment {
    ImageView imgGotoAddSale, imgGotoCalendar, imbLogout, imgGotoHistoryInvoice, imgGotoChangePass;
    ImageView imgGotoBooking, imgGotoBookingHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgGotoBookingHistory = view.findViewById(R.id.img_gotoActivity_booking);
        imgGotoAddSale = view.findViewById(R.id.arrow3_setting);
        imgGotoCalendar = view.findViewById(R.id.arrow4_setting);
        imbLogout = view.findViewById(R.id.arrow5_setting);
        imgGotoHistoryInvoice = view.findViewById(R.id.img_showHistory);
        imgGotoBooking = view.findViewById(R.id.img_gotoBooking);
        imgGotoChangePass = view.findViewById(R.id.img_gotoChangePass);
        ((MainActivity) requireActivity()).chipNavigationBar.setVisibility(View.VISIBLE);
        imbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc muốn đăng xuất không !");
                builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });

        imgGotoAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentSales());
            }
        });
        imgGotoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentCalendar());

            }
        });
        imgGotoHistoryInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentHistoryInvoice());
            }
        });

        imgGotoBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).reloadFragment(new FragmentBooking());
            }
        });

        imgGotoBookingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentBookingActivity());
            }
        });
        imgGotoChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).reloadFragment(new FragmentUpdatePassWord());
            }
        });
    }
}