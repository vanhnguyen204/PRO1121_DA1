package com.fpoly.pro1121_da1.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fpoly.pro1121_da1.Adapter.BookingHistoryAdapter;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.BookingDAO;
import com.fpoly.pro1121_da1.model.Booking;

import java.util.ArrayList;

public class FragmentBookingActivity extends Fragment {
    BookingDAO bookingDAO;
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
        recyclerView = view.findViewById(R.id.recyclerView_bookingHistory);
        imgBack = view.findViewById(R.id.img_back_fragmentBookingHistory);
        bookingDAO = new BookingDAO(getActivity());
        list = bookingDAO.getBooking("SELECT * FROM Booking");
        bookingHistoryAdapter = new BookingHistoryAdapter(list, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bookingHistoryAdapter);

    }
}