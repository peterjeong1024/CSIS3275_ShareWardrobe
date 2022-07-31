package com.example.sharewardrobeapp;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sharewardrobeapp.fashionitems.FashionItemsViewModel;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.objects.UserPlanData;
import com.example.sharewardrobeapp.outfits.OutfitsViewModel;
import com.example.sharewardrobeapp.userplanner.UserPlannerViewModel;
import com.example.sharewardrobeapp.userplanner.UserPlansRecyclerAdapter;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlannerActivity extends BasementActivity implements UserPlansRecyclerAdapter.OnItemClick {

    final static private int START_YEAR_LIST_YEAR = 1900;

    private Button last_month_button;
    private Button next_month_button;
    private Button today_month_button;
    private Spinner month_spinner;
    private Spinner year_spinner;
    private RecyclerView planner_recycler_view;

    private RecyclerView.LayoutManager layoutManager;
    private UserPlansRecyclerAdapter adapter;
    private UserPlannerViewModel mPlannerViewModel;
    private ArrayList<UserPlanData> mUserPlanDataItems;
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
        month_spinner = findViewById(R.id.month_spinner);
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCalendar(view.getContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        year_spinner = findViewById(R.id.year_spinner);
        ArrayList<String> years = new ArrayList<>();
        for (int i = Calendar.getInstance().get(Calendar.YEAR) + 5; i >= START_YEAR_LIST_YEAR; i--) {
            years.add(0, String.valueOf(i));
        }
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(yearsAdapter);
        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateCalendar(view.getContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        last_month_button = findViewById(R.id.last_month_button);
        last_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newMonthPosition = (month_spinner.getSelectedItemPosition() + 11) % 12;
                if (newMonthPosition == 11) {
                    if (year_spinner.getSelectedItemPosition() == 0) {
                        return;
                    }
                    year_spinner.setSelection(year_spinner.getSelectedItemPosition() - 1);
                }
                month_spinner.setSelection(newMonthPosition);
                updateCalendar(v.getContext());
            }
        });
        next_month_button = findViewById(R.id.next_month_button);
        next_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newMonthPosition = (month_spinner.getSelectedItemPosition() + 1) % 12;
                if (newMonthPosition == 0) {
                    if (year_spinner.getSelectedItemPosition() == year_spinner.getAdapter().getCount() - 1) {
                        return;
                    }
                    year_spinner.setSelection(year_spinner.getSelectedItemPosition() + 1);
                }
                month_spinner.setSelection(newMonthPosition);
                updateCalendar(v.getContext());
            }
        });
        today_month_button = findViewById(R.id.today_month_button);
        today_month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar today = Calendar.getInstance();
                int currYear = today.get(Calendar.YEAR);
                int currMonth = today.get(Calendar.MONTH);

                month_spinner.setSelection(currMonth);
                year_spinner.setSelection(currYear - START_YEAR_LIST_YEAR);
                updateCalendar(v.getContext());
            }
        });

        planner_recycler_view = findViewById(R.id.planner_recycler_view);

        dayBitmap = new HashMap<String, Bitmap>();

        // sample code to use ViewModel object
        // create new ViewModel object
        mPlannerViewModel = new ViewModelProvider(this).get(UserPlannerViewModel.class);
        mOutfitsViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);
        mFashionItemsViewModel = new ViewModelProvider(this).get(FashionItemsViewModel.class);

        // mOutfitItems = new ArrayList<>();
        // mFashionItems = new ArrayList<>();

        today_month_button.callOnClick();

        // bring data from ViewModel object. data is brought automatically
        mPlannerViewModel.getUserPlanDataList(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<UserPlanData>>() {
            @Override
            public void onChanged(ArrayList<UserPlanData> items) {
                // data(UserPlanData) is brought here  automatically and asynchronously
                mUserPlanDataItems = items;
                // displayScreen(items);
                updateCalendar(getApplicationContext());
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

        Toast.makeText(getBaseContext(), "day clicked", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(this, FashionItemDetailActivity.class);
//        i.putExtra(ConstantValue.FASHION_ITEM_CLICK_ID, mFashionItemList.get(position).get_id());
//        startActivity(i);
    }

    private String getYyyy() {
        return year_spinner.getSelectedItem().toString();
    }

    private String getMm() {
        return String.format("%02d", month_spinner.getSelectedItemPosition());
    }

    private void updateCalendar(Context context) {
        layoutManager = new GridLayoutManager(this, 7);
        planner_recycler_view.setLayoutManager(layoutManager);
        dayBitmap.clear();
        adapter = new UserPlansRecyclerAdapter(getYyyy(), getMm(), dayBitmap);
        adapter.setOnItemClickListener(this);
        planner_recycler_view.setAdapter(adapter);

        // finish if plans are not loaded yet
        if (mUserPlanDataItems == null) {
            return;
        }

        // update calendar according to mUserPlanDataList
        int mm = month_spinner.getSelectedItemPosition();
        int yyyy = Integer.parseInt(year_spinner.getSelectedItem().toString());
        for (int i = 0; i < mUserPlanDataItems.size(); i++) {
            Calendar wornDate = Calendar.getInstance();
            wornDate.setTime(new Date(Long.parseLong(mUserPlanDataItems.get(i).getWornDate())));

            // check next if wornDate out of monthStart and monthEnd
            if (wornDate.get(Calendar.MONTH) != mm || wornDate.get(Calendar.YEAR) != yyyy) {
                continue;
            }

            // no need to have more than 1 image for that tiny day cell in the calendar
            String bitmapKey = String.format("%04d%02d%02d", yyyy, mm, wornDate.get(Calendar.DATE));
            if (dayBitmap.get(bitmapKey) != null) {
                continue;
            }

            int viewHolderPosition = wornDate.get(Calendar.DATE) + adapter.getOffset() - 1;

            // get the UserPlanData
            UserPlanData plan = mUserPlanDataItems.get(i);

            // identify corresponding outfits if available
            String[] outfitIDs = plan.getOutFitsSerialize().split("\\|");
            if (outfitIDs.length > 0) {
                // get image from 1st outfit
                mOutfitsViewModel.getOutfitItemData(outfitIDs[0]).observe(this, new Observer<OutfitItem>() {
                    @Override
                    public void onChanged(OutfitItem outfitItem) {
                        // mOutfitItems.add(outfitItem);
                        if (dayBitmap.get(bitmapKey) == null) {
                            dayBitmap.put(bitmapKey, outfitItem.getOutfitImgBitmap());
                            adapter.notifyItemChanged(viewHolderPosition);
                        }
                    }
                });
                continue;
                // move to next plan
            }

            // identify corresponding fashion items if available
            String[] fashionItemIDs = plan.getFItemsSerialize().split("\\|");
            if (fashionItemIDs.length > 0) {
                // get image from 1st item
                mFashionItemsViewModel.getFashionItemData(fashionItemIDs[0]).observe(this, new Observer<FashionItem>() {
                    @Override
                    public void onChanged(FashionItem fashionItem) {
                        // mFashionItems.add(fashionItem);
                        if (dayBitmap.get(bitmapKey) != null) {
                            dayBitmap.put(bitmapKey, fashionItem.getItemImgBitmap());
                            adapter.notifyItemChanged(viewHolderPosition);
                        }
                    }
                });
                continue;
            }

            // no outfit nor fashion item for this plan!
            UseLog.e("No outfit nor fashion item for plan " + plan.get_id());
        }
    }

}