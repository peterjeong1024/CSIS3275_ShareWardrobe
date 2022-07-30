package com.example.sharewardrobeapp;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.format.DateUtils;

import com.example.sharewardrobeapp.objects.UserPlanData;
import com.example.sharewardrobeapp.userplanner.UserPlannerViewModel;
import com.example.sharewardrobeapp.util.UseLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlannerActivity extends BasementActivity {

    private UserPlannerViewModel mViewModel;
    private ArrayList<UserPlanData> mUserPlanDataItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        // sample code to use ViewModel object
        // create new ViewModel object
        mViewModel = new ViewModelProvider(this).get(UserPlannerViewModel.class);
        // bring data from ViewModel object. data is brought automatically
        mViewModel.getUserPlanDataList().observe(this, new Observer<ArrayList<UserPlanData>>() {
            @Override
            public void onChanged(ArrayList<UserPlanData> items) {
                // data(UserPlanData) is brought here  automatically and asynchronously
                mUserPlanDataItems = items;
                displayScreen(items);
            }
        });
    }

    private void displayScreen(ArrayList<UserPlanData> items) {
        // drawing code is here
        UseLog.d("" + items.get(0).getWornDate());

        long timeValue = Long.parseLong(items.get(0).getWornDate());
        String time = DateUtils.formatDateTime(this, timeValue, DateUtils.FORMAT_SHOW_TIME);
        String date = DateUtils.formatDateTime(this, timeValue, DateUtils.FORMAT_SHOW_DATE);
        UseLog.d("time : " + time);
        UseLog.d("date : " + date);


    }
}