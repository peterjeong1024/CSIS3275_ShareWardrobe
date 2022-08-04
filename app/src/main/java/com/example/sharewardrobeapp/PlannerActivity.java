package com.example.sharewardrobeapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sharewardrobeapp.fashionitems.FashionItemsViewModel;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.objects.UserPlanData;
import com.example.sharewardrobeapp.outfits.OutfitDetailActivity;
import com.example.sharewardrobeapp.outfits.OutfitsViewModel;
import com.example.sharewardrobeapp.userplanner.PlannerDayActivity;
import com.example.sharewardrobeapp.userplanner.PlannerDetailActivity;
import com.example.sharewardrobeapp.userplanner.UserPlannerViewModel;
import com.example.sharewardrobeapp.userplanner.PlannerMonthRecyclerAdapter;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlannerActivity extends BasementActivity implements PlannerMonthRecyclerAdapter.OnItemClick {

    final static private int START_YEAR_LIST_YEAR = 1900;

    private Button lastMonthButton;
    private Button nextMonthButton;
    private Button todayMonthButton;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private RecyclerView plannerMonthRecyclerView;

    private RecyclerView.LayoutManager layoutManager;
    private PlannerMonthRecyclerAdapter adapter;
    private UserPlannerViewModel mPlannerViewModel;
    private ArrayList<UserPlanData> mUserPlanDataItems;
    private Map<String, String> dayBitmapKey;
    private Map<String, Bitmap> dayBitmap;

    private OutfitsViewModel mOutfitsViewModel;
    // private ArrayList<OutfitItem> mOutfitItems;
    private FashionItemsViewModel mFashionItemsViewModel;
    // private ArrayList<FashionItem> mFashionItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        // identify controls and set up their listeners
        monthSpinner = findViewById(R.id.month_spinner);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCalendar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearSpinner = findViewById(R.id.year_spinner);
        ArrayList<String> years = new ArrayList<>();
        for (int i = Calendar.getInstance().get(Calendar.YEAR) + 5; i >= START_YEAR_LIST_YEAR; i--) {
            years.add(0, String.valueOf(i));
        }
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearsAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCalendar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lastMonthButton = findViewById(R.id.last_month_button);
        lastMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newMonthPosition = (monthSpinner.getSelectedItemPosition() + 11) % 12;
                if (newMonthPosition == 11) {
                    if (yearSpinner.getSelectedItemPosition() == 0) {
                        return;
                    }
                    yearSpinner.setSelection(yearSpinner.getSelectedItemPosition() - 1);
                }
                monthSpinner.setSelection(newMonthPosition);
//                updateCalendar();
            }
        });
        nextMonthButton = findViewById(R.id.next_month_button);
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newMonthPosition = (monthSpinner.getSelectedItemPosition() + 1) % 12;
                if (newMonthPosition == 0) {
                    if (yearSpinner.getSelectedItemPosition() == yearSpinner.getAdapter().getCount() - 1) {
                        return;
                    }
                    yearSpinner.setSelection(yearSpinner.getSelectedItemPosition() + 1);
                }
                monthSpinner.setSelection(newMonthPosition);
//                updateCalendar();
            }
        });
        todayMonthButton = findViewById(R.id.today_month_button);
        todayMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar today = Calendar.getInstance();
                int currYear = today.get(Calendar.YEAR);
                int currMonthIdx = today.get(Calendar.MONTH);

                monthSpinner.setSelection(currMonthIdx);
                yearSpinner.setSelection(currYear - START_YEAR_LIST_YEAR);
//                updateCalendar();
            }
        });

        plannerMonthRecyclerView = findViewById(R.id.planner_month_recycler_view);

        dayBitmapKey = new HashMap<String, String>();
        dayBitmap = new HashMap<String, Bitmap>();
        layoutManager = new GridLayoutManager(this, 7);
        plannerMonthRecyclerView.setLayoutManager(layoutManager);
        adapter = new PlannerMonthRecyclerAdapter(dayBitmapKey, dayBitmap);
        adapter.setOnItemClickListener(this);
        plannerMonthRecyclerView.setAdapter(adapter);

        // sample code to use ViewModel object
        // create new ViewModel object
        mPlannerViewModel = new ViewModelProvider(this).get(UserPlannerViewModel.class);
        mOutfitsViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);
        mFashionItemsViewModel = new ViewModelProvider(this).get(FashionItemsViewModel.class);

        todayMonthButton.callOnClick();

        // bring data from ViewModel object. data is brought automatically
        mPlannerViewModel.getUserPlanDataList(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<UserPlanData>>() {
            @Override
            public void onChanged(ArrayList<UserPlanData> items) {
                // data(UserPlanData) is brought here  automatically and asynchronously
                mUserPlanDataItems = items;
                // displayScreen(items);
                updateCalendar();
            }
        });
    }

    /*
    private void displayScreen(ArrayList<UserPlanData> items) {
        // drawing code is here
        UseLog.d("" + items.get(0).getWornDate());

        long timeValue = Long.parseLong(items.get(0).getWornDate());
        String time = DateUtils.formatDateTime(this, timeValue, DateUtils.FORMAT_SHOW_TIME);
        String date = DateUtils.formatDateTime(this, timeValue, DateUtils.FORMAT_SHOW_DATE);
        UseLog.d("time : " + time);
        UseLog.d("date : " + date);


    }
     */

    @Override
    public void onClickItem(int position) {
        UseLog.d("Date clicked");

        // Toast.makeText(getBaseContext(), "day clicked", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, PlannerDayActivity.class);
        i.putExtra(ConstantValue.PLANNER_CLICK_DAY, getYyyy() + getMm() + String.format("%02d", position - adapter.getOffset() + 1));
        startForResult.launch(i);
    }

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String returnString = data.getExtras().getString(ConstantValue.PLANNER_CLICK_DAY);
                        monthSpinner.setSelection(Integer.parseInt(returnString.substring(4, 6)) - 1);
                        yearSpinner.setSelection(Integer.parseInt(returnString.substring(0, 4)) - START_YEAR_LIST_YEAR);
                        updateCalendar();
                    }
                }
            }
    );


    private String getYyyy() {
        return yearSpinner.getSelectedItem().toString();
    }

    private String getMm() {
        return String.format("%02d", monthSpinner.getSelectedItemPosition() + 1);
    }

    private void updateCalendar() {
        dayBitmapKey.clear();

        int mmIdx = monthSpinner.getSelectedItemPosition();
        int yyyy = Integer.parseInt(yearSpinner.getSelectedItem().toString());
        adapter.notifyMonthChange(yyyy, mmIdx + 1);

        // finish if plans are not loaded yet
        if (mUserPlanDataItems == null) {
            return;
        }

        // update calendar according to mUserPlanDataList
        for (int i = 0; i < mUserPlanDataItems.size(); i++) {
            Calendar wornDate = Calendar.getInstance();
            wornDate.setTime(new Date(Long.parseLong(mUserPlanDataItems.get(i).getWornDate())));

            // check next if wornDate out of monthStart and monthEnd
            if (wornDate.get(Calendar.MONTH) != mmIdx || wornDate.get(Calendar.YEAR) != yyyy) {
                continue;
            }

            // no need to have more than 1 image for that tiny day cell in the calendar
            String dayKey = String.format("%04d%02d%02d", yyyy, (mmIdx + 1), wornDate.get(Calendar.DATE));
            if (dayBitmapKey.get(dayKey) != null) {
                continue;
            }
            if (!dayBitmapKey.containsKey(dayKey)) {
                dayBitmapKey.put(dayKey, null);
            }

            // int viewHolderPosition = wornDate.get(Calendar.DATE) + adapter.getOffset() - 1;

            // get the UserPlanData
            UserPlanData plan = mUserPlanDataItems.get(i);

            // identify corresponding outfits if available
            if (plan.getOutFitsSerialize().length() > 0) {
                // get image from 1st outfit
                String[] outfitIDs = plan.getOutFitsSerialize().split("\\|");
                dayBitmapKey.put(dayKey, "outfit_" + outfitIDs[0]);
                mOutfitsViewModel.getOutfitItemData(outfitIDs[0]).observe(this, new Observer<OutfitItem>() {
                    @Override
                    public void onChanged(OutfitItem outfitItem) {
                        // mOutfitItems.add(outfitItem);
                        String bitmapKey = "outfit_" + outfitItem.get_id();
                        if (dayBitmap.get(bitmapKey) == null && outfitItem != null) {
                            dayBitmap.put(bitmapKey, outfitItem.getOutfitImgBitmap());
                            adapter.notifyImageChange(bitmapKey);
                        }
                        if (dayBitmap.get(bitmapKey) != null && outfitItem == null) {
                            dayBitmap.remove(bitmapKey);
                            adapter.notifyImageChange(bitmapKey);
                        }
                    }
                });
                continue;
                // move to next plan
            }

            // identify corresponding fashion items if available
            if (plan.getFItemsSerialize().length() > 0) {
                String[] fashionItemIDs = plan.getFItemsSerialize().split("\\|");
                // get image from 1st item
                dayBitmapKey.put(dayKey, "item_" + fashionItemIDs[0]);
                mFashionItemsViewModel.getFashionItemData(fashionItemIDs[0]).observe(this, new Observer<FashionItem>() {
                    @Override
                    public void onChanged(FashionItem fashionItem) {
                        // mFashionItems.add(fashionItem);
                        String bitmapKey = "item_" + fashionItem.get_id();
                        if (dayBitmap.get(bitmapKey) == null && fashionItem != null) {
                            dayBitmap.put(bitmapKey, fashionItem.getItemImgBitmap());
                            adapter.notifyImageChange(bitmapKey);
                        }
                        if (dayBitmap.get(bitmapKey) != null && fashionItem == null) {
                            dayBitmap.remove(bitmapKey);
                            adapter.notifyImageChange(bitmapKey);
                        }
                    }
                });
                continue;
            }

            // no outfit nor fashion item for this plan!
            UseLog.e("No outfit nor fashion item for plan " + plan.get_id());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItemMenu:
                startForResult.launch(new Intent(this, PlannerDetailActivity.class));
                return true;
            case R.id.settingMenu:
                UseLog.v("settingMenu");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}