package com.example.sharewardrobeapp.userplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.fashionitems.FashionItemsViewModel;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.objects.UserPlanData;
import com.example.sharewardrobeapp.outfits.OutfitsViewModel;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PlannerDayActivity extends BasementActivity implements PlannerDayRecyclerAdapter.OnItemClick {

    private Button lastDayButton;
    private Button nextDayButton;
    private Button todayDayButton;
    private TextView planDayText;
    private RecyclerView plannerDayRecyclerView;
    private Button addPlanButton;

    private Calendar planDay;
    private PlannerDayRecyclerAdapter adapter;
    private UserPlannerViewModel mPlannerViewModel;
    private OutfitsViewModel mOutfitsViewModel;
    private FashionItemsViewModel mFashionItemsViewModel;

    private ArrayList<UserPlanData> mUserPlanDataItems;
    private ArrayList<UserPlanData> dayPlanItems;
    private Map<String, Bitmap> dayBitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_day);

        setTitle(getResources().getString(R.string.planner_text));

        lastDayButton = findViewById(R.id.last_day_button);
        lastDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planDay.add(Calendar.DATE, -1);
                updateScreen();
            }
        });
        nextDayButton = findViewById(R.id.next_day_button);
        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planDay.add(Calendar.DATE, 1);
                updateScreen();
            }
        });
        todayDayButton = findViewById(R.id.today_day_button);
        todayDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planDay.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
                updateScreen();
            }
        });
        planDayText = findViewById(R.id.plan_day_text);
        plannerDayRecyclerView = findViewById(R.id.planner_day_recycler_view);
        plannerDayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addPlanButton = findViewById(R.id.add_plan_button);
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add a new plan
            }
        });

        String yyyymmdd = getIntent().getStringExtra(ConstantValue.PLANNER_CLICK_DAY);
        planDay = Calendar.getInstance();
        planDay.set(Integer.parseInt(yyyymmdd.substring(0, 4)), Integer.parseInt(yyyymmdd.substring(4, 6)), Integer.parseInt(yyyymmdd.substring(6)));

        // create new ViewModel object
        mPlannerViewModel = new ViewModelProvider(this).get(UserPlannerViewModel.class);
        mOutfitsViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);
        mFashionItemsViewModel = new ViewModelProvider(this).get(FashionItemsViewModel.class);

        dayPlanItems = new ArrayList<>();
        dayBitmaps = new HashMap<String, Bitmap>();
        adapter = new PlannerDayRecyclerAdapter(dayPlanItems, dayBitmaps);
        adapter.setOnItemClickListener(this);
        plannerDayRecyclerView.setAdapter(adapter);

        // bring data from ViewModel object. data is brought automatically
        mPlannerViewModel.getUserPlanDataList(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<UserPlanData>>() {
            @Override
            public void onChanged(ArrayList<UserPlanData> items) {
                // data(UserPlanData) is brought here  automatically and asynchronously
                mUserPlanDataItems = items;
                // displayScreen(items);
                updateScreen();
            }
        });
    }

    @Override
    public void onClickItem(int position) {
        UseLog.d("Plan clicked");

        // Toast.makeText(getBaseContext(), "day clicked", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(this, PlannerDayActivity.class);
//        i.putExtra(ConstantValue.PLANNER_CLICK_DAY, getYyyy() + getMm() + String.format("%02d", position - adapter.getOffset() + 1));
//        startActivity(i);
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        String returnDayString = (new SimpleDateFormat("yyyyMMdd")).format(planDay.getTimeInMillis());
        data.putExtra(ConstantValue.PLANNER_CLICK_DAY, returnDayString);
        setResult(RESULT_OK, data);

        super.finish();
    }

    void updateScreen() {
        SimpleDateFormat dateFormater = new SimpleDateFormat("d MMM yyyy");
        String selectedDayString = dateFormater.format(planDay.getTimeInMillis());
        planDayText.setText(selectedDayString);

        int position = 0;
        for (int i = 0; i < mUserPlanDataItems.size(); i++) {
            UserPlanData plan = mUserPlanDataItems.get(i);
            Calendar wornDate = Calendar.getInstance();
            wornDate.setTimeInMillis(Long.parseLong(plan.getWornDate()));

            // check if wornDate is the same as current selected date
            if (dateFormater.format(wornDate.getTimeInMillis()).compareTo(selectedDayString) == 0) {
                // ie, worn date is the selected date
                if (position < dayPlanItems.size()) {
                    // replace existing one
                    dayPlanItems.set(position, plan);
                } else {
                    // add a new one
                    dayPlanItems.add(plan);
                }
                // position in dayPlanItems + 1
                position += 1;

                String bitmapKey = plan.get_id();
                if (dayBitmaps.get(plan.get_id()) == null) {
                    // identify corresponding outfits if available
                    String[] outfitIDs = plan.getOutFitsSerialize().split("\\|");
                    if (outfitIDs.length > 0) {
                        // get image from 1st outfit
                        mOutfitsViewModel.getOutfitItemData(outfitIDs[0]).observe(this, new Observer<OutfitItem>() {
                            @Override
                            public void onChanged(OutfitItem outfitItem) {
                                // update this instead of because position cannot access from inner class like this
                                int position = dayPlanItems.indexOf(plan);

                                if (dayBitmaps.get(bitmapKey) == null) {
                                    dayBitmaps.put(bitmapKey, outfitItem.getOutfitImgBitmap());
                                    adapter.notifyItemChanged(position);
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
                                // update this instead of because position cannot access from inner class like this
                                int position = dayPlanItems.indexOf(plan);

                                if (dayBitmaps.get(bitmapKey) != null) {
                                    dayBitmaps.put(bitmapKey, fashionItem.getItemImgBitmap());
                                    adapter.notifyItemChanged(position);
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
        // remove excessive element in dayPlanItems
        while (dayPlanItems.size() > position) {
            dayPlanItems.remove(position);
        }
//        // show/hide add plan button
//        addPlanButton.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
        // notify adapter
        adapter.notifyDataSetChanged();
    }
}