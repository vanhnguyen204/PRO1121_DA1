package com.fpoly.pro1121_da1.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fpoly.pro1121_da1.Adapter.CalenderAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.model.CalenderWork;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class FragmentCalendar extends Fragment {

    CalenderAdapter calenderAdapter;
    ArrayList<CalenderWork> calendarArrayList;
    CalenderDAO calenderDAO;
    Spinner spinnerShiftWork;
    RecyclerView rcViewCalendar;
    TextView tv_dayNow, tv_daytoWork;
    ImageView img_calendar, img_back_fragmentSetting;
    Button btn_addWordCalender;
    String[] shiftWork;

    Calendar calendar;
    String getShift;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img_calendar = view.findViewById(R.id.img_calendar);
        img_back_fragmentSetting = view.findViewById(R.id.img_back_fragmentSetting);
        tv_dayNow = view.findViewById(R.id.tv_dayNow_calender);
        tv_daytoWork = view.findViewById(R.id.tv_daytoWord_calender);
        btn_addWordCalender = view.findViewById(R.id.btn_add_CalendarWord);
        rcViewCalendar = view.findViewById(R.id.rcv_calenderWork);
        spinnerShiftWork = view.findViewById(R.id.spn_shiftWork);

        calenderDAO = new CalenderDAO(getActivity(),new Dbhelper(getActivity()));
        calendarArrayList = calenderDAO.getAllCalendar();
        calenderAdapter = new CalenderAdapter(getActivity(),calendarArrayList);
        rcViewCalendar.setAdapter(calenderAdapter);

        Animation animation = new TranslateAnimation(0,0,0,50);
        animation.setDuration(2000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        img_calendar.setAnimation(animation);

        setTimeNow(tv_dayNow);

         shiftWork = new String[]{
                "Ca sáng: 8h -> 13h",
                "Ca chiều: 13h -> 18h",
                "Ca tối: 18h -> 23h"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                shiftWork

        );
        spinnerShiftWork.setAdapter(adapter);

        spinnerShiftWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getShift = shiftWork[position];
//                Toast.makeText(getContext(), shiftWork[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img_back_fragmentSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).reloadFragment(new FragmentSettings());
            }
        });

        img_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tv_daytoWork);
            }
        });



        btn_addWordCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getDayofWork = tv_daytoWork.getText().toString().trim();

                CalenderWork calenderWork = new CalenderWork(getDayofWork,getShift);
                calenderDAO.insertCalender(calenderWork,"Thêm lịch làm việc thành công","Thêm lịch làm việc không thành công");
                calendarArrayList.add(calenderWork);
                calenderAdapter.notifyItemInserted(calendarArrayList.size());
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getLayoutPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cảnh Báo!");
                builder.setTitle("Bạn có muốn xóa lịch làm việc này không ?");

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        calenderAdapter.notifyItemChanged(position);
                    }
                });
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        calendarArrayList.remove(position);
                        calenderDAO.deleteCalender(calendarArrayList.get(position).getId_calender(),"Xóa lịch làm việc thành công","Xóa lịch làm việc không thành công");
                        calenderAdapter.notifyItemRemoved(position);

                    }
                });


                builder.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcViewCalendar);

    }

    public void showDatePicker(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month < 9) {
                            textView.setText(year + "-" + "0" + (month + 1) + "-0"+ dayOfMonth);
                        } else if (dayOfMonth < 10) {
                            textView.setText(year + "-" + (month + 1) + "-0"+ dayOfMonth);
                        } else {
                            textView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        }
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
    public void  setTimeNow(TextView textView){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM" +
                "-dd");
        Calendar calendar1 = Calendar.getInstance();
        textView.setText(dateFormat.format(calendar1.getTime()));
    }

}