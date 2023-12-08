package com.fpoly.pro1121_da1.Fragment;

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
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.CalenderAdapter;
import com.fpoly.pro1121_da1.Adapter.DrinkAdapter;
import com.fpoly.pro1121_da1.MainActivity;
import com.fpoly.pro1121_da1.R;
import com.fpoly.pro1121_da1.database.CalenderDAO;
import com.fpoly.pro1121_da1.database.Dbhelper;
import com.fpoly.pro1121_da1.database.UserDAO;
import com.fpoly.pro1121_da1.model.CalenderWork;
import com.fpoly.pro1121_da1.model.User;

import java.util.ArrayList;

public class FragmentMyWork extends Fragment {

    RecyclerView recyclerView;
    EditText edtSearch;
    ImageView imgBack;
    CalenderAdapter calenderAdapter;
    ArrayList<CalenderWork> calenderWorks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_work, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtSearch = view.findViewById(R.id.edt_search_myWork);
        recyclerView = view.findViewById(R.id.recyclerView_myWork);
        imgBack = view.findViewById(R.id.img_backMyWork);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).reloadFragment(new FragmentSettingsStaff());
            }
        });
        CalenderDAO calenderDAO = new CalenderDAO(getContext(), new Dbhelper(getContext()));
        User user = ((MainActivity) getActivity()).user;

        calenderWorks = calenderDAO.getAllCanlendarOfUser(user.getUserID());

        if (calenderWorks.size() == 0) {
            Toast.makeText(getContext(), "Chưa có lịch làm việc", Toast.LENGTH_SHORT).show();
        } else {
            calenderAdapter = new CalenderAdapter(getActivity(), calenderWorks);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(calenderAdapter);
        }
        ArrayList<CalenderWork> listClone = calenderDAO.getAllCanlendarOfUser(user.getUserID());
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calenderWorks.clear();

                String getTextFromEdt = charSequence.toString().toLowerCase().trim();
                if (getTextFromEdt.length() == 0) {
                    recyclerView.setAdapter(new CalenderAdapter(getActivity(), listClone));
                } else {
                    for (int index = 0; index < listClone.size(); index++) {
                        if (listClone.get(index).getDayofWork().toLowerCase().contains(getTextFromEdt)) {
                            calenderWorks.add(listClone.get(index));
                        }
                    }
                    recyclerView.setAdapter(new CalenderAdapter(getActivity(), calenderWorks));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}