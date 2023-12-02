package com.fpoly.pro1121_da1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fpoly.pro1121_da1.Adapter.CalenderAdapter;
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

        recyclerView = view.findViewById(R.id.recyclerView_myWork);
        UserDAO userDAO = new UserDAO(getContext(), new Dbhelper(getContext()));
        CalenderDAO calenderDAO = new CalenderDAO(getContext(), new Dbhelper(getContext()));
        User user = ((MainActivity) getActivity()).user;
        Toast.makeText(getContext(), ""+user.getUserID()+"-----"+user.getFullName(), Toast.LENGTH_SHORT).show();
        calenderWorks = calenderDAO.getAllCanlendarOfUser(user.getUserID());
        calenderAdapter = new CalenderAdapter(getActivity(), calenderWorks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(calenderAdapter);
    }
}