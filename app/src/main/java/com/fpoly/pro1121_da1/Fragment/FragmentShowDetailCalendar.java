package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.CalenderAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.model.CalenderWork;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FragmentShowDetailCalendar extends Fragment {

    TextView tv_shiftNow;
    RecyclerView rcv_calendarShowDetail;
    Button btn_deleteStaff_showDetailCalendar;
    ImageView imgBackFragmentCalenDarWork;
    FloatingActionButton btn_addStaff_showDetailCalendar;

    ArrayList<CalenderWork> listCalendarWord;
    int receiveCalenDarID;
    CalenderAdapter calenderAdapter;
    CalenderWork calenderWork;
    CalenderDAO calenderDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__show_detail__calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgBackFragmentCalenDarWork = view.findViewById(R.id.img_back_frameCalenDarWork);
        tv_shiftNow = view.findViewById(R.id.tv_shift_work);
        rcv_calendarShowDetail = view.findViewById(R.id.rcv_showDetailCalenderWorkForShiff);
        btn_addStaff_showDetailCalendar = view.findViewById(R.id.btn_add_staffForShowDetail_CalendarWork);
        btn_deleteStaff_showDetailCalendar = view.findViewById(R.id.btn_delete_staffForShowDetail_CalendarWork);
        calenderDAO = new CalenderDAO(getActivity(),new Dbhelper(getActivity()));


        Bundle bundle = this.getArguments();
        if (bundle != null){
            receiveCalenDarID = bundle.getInt("CALENDER_ID");


//            tv_shiftNow.setText(""+calenderWork.getShiftWork());
            Toast.makeText(getActivity(), receiveCalenDarID+"", Toast.LENGTH_SHORT).show();


        }else {
            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
        }

        imgBackFragmentCalenDarWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).reloadFragment(new FragmentCalendar());
            }
        });
    }
}