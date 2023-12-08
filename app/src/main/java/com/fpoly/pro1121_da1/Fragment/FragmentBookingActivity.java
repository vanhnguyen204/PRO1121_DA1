package com.fpoly.pro1121_da1.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.fpoly.pro1121_da1.Adapter.BookingHistoryAdapter;
import com.fpoly.pro1121_da1.Adapter.DrinkAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.BookingDAO;
import com.fpoly.pro1121_da1.model.Booking;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class FragmentBookingActivity extends Fragment {
    BookingDAO bookingDAO;
    EditText edtSearch;
    ArrayList<Booking> list;
    RecyclerView recyclerView;
    BookingHistoryAdapter bookingHistoryAdapter;
    ImageView imgBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking_activity, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtSearch = view.findViewById(R.id.edt_searchBooking);
        recyclerView = view.findViewById(R.id.recyclerView_bookingHistory);
        imgBack = view.findViewById(R.id.img_back_fragmentBookingHistory);
        bookingDAO = new BookingDAO(getActivity());
        list = bookingDAO.getBooking("SELECT * FROM Booking");
        bookingHistoryAdapter = new BookingHistoryAdapter(list, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bookingHistoryAdapter);
        User user = ((MainActivity)requireActivity()).user;
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (user.getRole().equals("admin")){
                   ((MainActivity)requireActivity()).reloadFragment(new FragmentSettings());
               }else {
                   ((MainActivity)requireActivity()).reloadFragment(new FragmentSettingsStaff());

               }
            }
        });
        ArrayList<Booking> listClone = bookingDAO.getBooking("SELECT * FROM Booking");
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.clear();

                String getTextFromEdt = charSequence.toString().toLowerCase().trim();
                if (getTextFromEdt.length() == 0) {
                    recyclerView.setAdapter(new BookingHistoryAdapter(listClone, getActivity()));
                } else {
                    for (int index = 0; index < listClone.size(); index++) {
                        if (listClone.get(index).getDayBooking().toLowerCase().contains(getTextFromEdt)) {
                            list.add(listClone.get(index));
                        }
                    }
                    recyclerView.setAdapter(new BookingHistoryAdapter(list, getActivity()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}