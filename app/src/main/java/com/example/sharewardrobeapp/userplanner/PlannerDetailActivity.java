package com.example.sharewardrobeapp.userplanner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.fashionitems.FashionItemDetailActivity;
import com.example.sharewardrobeapp.fashionitems.FashionItemsViewModel;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.objects.UserPlanData;
import com.example.sharewardrobeapp.outfits.OutfitDetailActivity;
import com.example.sharewardrobeapp.outfits.OutfitsViewModel;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PlannerDetailActivity extends BasementActivity implements PlannerDetailOutfitsRecyclerAdapter.OnItemClick, PlannerDetailFashionItemsRecyclerAdapter.OnItemClick {

    private Button editButton;
    private Button deleteButton;
    private Button confirmButton;
    private Button cancelButton;
    private EditText itemDescriptionInput;
    private EditText wornDateInput;
    private RecyclerView outfitsRecyclerView;
    private ImageButton addOutfitButton;
    private RecyclerView fashionItemsRecyclerView;
    private ImageButton addFashionItemButton;

    private String plan_id;
    private Boolean isEditable;
    private UserPlanData plan;
    private Calendar wornDate;

    private PlannerDetailOutfitsRecyclerAdapter outfitsAdapter;
    private ArrayList<String> planOutfitItemIds;
    private Map<String, OutfitItem> planOutfitItems;
    private ArrayList<OutfitItem> allOutfitItems;

    private PlannerDetailFashionItemsRecyclerAdapter fashionItemsAdapter;
    private ArrayList<String> planFashionItemIds;
    private Map<String, FashionItem> planFashionItems;
    private ArrayList<FashionItem> allFashionItems;

    private UserPlannerViewModel mPlannerViewModel;
    private OutfitsViewModel mOutfitsViewModel;
    private FashionItemsViewModel mFashionItemsViewModel;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (isEditable) {
                            Intent data = result.getData();
                            String outfitItemID = data.getExtras().getString(ConstantValue.OUTFIT_ITEM_CLICK_ID);
                            if (outfitItemID != null && outfitItemID.length() > 0) {
                                planOutfitItemIds.add(outfitItemID);
                                for (OutfitItem outfitItem : allOutfitItems) {
                                    if (outfitItem.get_id().compareTo(outfitItemID) == 0) {
                                        planOutfitItems.put(outfitItem.get_id(), outfitItem);
                                    }
                                }
                                outfitsAdapter.notifyItemInserted(planOutfitItemIds.size());
                            }
                            String fashionItemID = data.getExtras().getString(ConstantValue.FASHION_ITEM_CLICK_ID);
                            if (fashionItemID != null && fashionItemID.length() > 0) {
                                planFashionItemIds.add(fashionItemID);
                                for (FashionItem fashionItem : allFashionItems) {
                                    if (fashionItem.get_id().compareTo(fashionItemID) == 0) {
                                        planFashionItems.put(fashionItem.get_id(), fashionItem);
                                    }
                                }
                                fashionItemsAdapter.notifyItemInserted(planFashionItemIds.size());
                            }
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_detail);

        setTitle(getResources().getString(R.string.planner_text));

        editButton = findViewById(R.id.event_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEdit(true);
            }
        });
        deleteButton = findViewById(R.id.event_delete_button);
        confirmButton = findViewById(R.id.event_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdd = (plan == null);
                if (isEditable) {
                    if (!isInputValid()) {
                        // not allow to let it be confirmed
                        return;
                    }
                    if (isAdd) {
                        // create new plan
                        plan = new UserPlanData();
                        plan.setUserID(getUserAccount().getUserID());
                    }
                    plan.setItemDescription(itemDescriptionInput.getText().toString());
                    plan.setWornDate(String.valueOf(wornDate.getTimeInMillis()));
                    plan.setOutFitsSerialize(String.join("|", planOutfitItemIds));
                    plan.setFItemsSerialize(String.join("|", planFashionItemIds));
                    if (isAdd) {
                        // add to repository
                        mPlannerViewModel.addUserPlanDataItem(plan);
                    } else {
                        // modify repository
                        mPlannerViewModel.updateUserPlanDataItem(plan);
                    }
                }
                enableEdit(false);
            }
        });
        cancelButton = findViewById(R.id.event_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdd = (plan == null);
                if (isAdd) {
                    // ie, cancel add
                    finish();
                }
                if (planOutfitItemIds.size() != 0) {
                    planOutfitItemIds.clear();
                }
                planOutfitItemIds.addAll(new ArrayList<String>(Arrays.asList(plan.getOutFitsSerialize().split("\\|"))));
                updatePlanOutfitItems();

                if (planFashionItemIds.size() != 0) {
                    planFashionItemIds.clear();
                }
                planFashionItemIds.addAll(new ArrayList<String>(Arrays.asList(plan.getFItemsSerialize().split("\\|"))));
                updatePlanFashionItems();

                enableEdit(false);
                updateContent();
            }
        });

        itemDescriptionInput = findViewById(R.id.event_description_input);
        wornDateInput = findViewById(R.id.event_date_input);
        outfitsRecyclerView = findViewById(R.id.event_outfits_recycler_view);
        addOutfitButton = findViewById(R.id.add_plan_outfit_button);
        addOutfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditable) return;
                Intent i = new Intent(PlannerDetailActivity.this, PlannerSelectItemActivity.class);
                i.putExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID, String.join("|", planOutfitItemIds));
                startForResult.launch(i);
            }
        });
        fashionItemsRecyclerView = findViewById(R.id.event_fashion_items_recycler_view);
        addFashionItemButton = findViewById(R.id.add_plan_fashion_item_button);
        addFashionItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditable) return;
                Intent i = new Intent(PlannerDetailActivity.this, PlannerSelectItemActivity.class);
                i.putExtra(ConstantValue.FASHION_ITEM_CLICK_ID, String.join("|", planFashionItemIds));
                startForResult.launch(i);
            }
        });

        mPlannerViewModel = new ViewModelProvider(this).get(UserPlannerViewModel.class);
        mOutfitsViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);
        mFashionItemsViewModel = new ViewModelProvider(this).get(FashionItemsViewModel.class);

        planOutfitItemIds = new ArrayList<>();
        planOutfitItems = new HashMap<>();
        outfitsAdapter = new PlannerDetailOutfitsRecyclerAdapter(planOutfitItemIds, planOutfitItems);
        outfitsAdapter.setOnItemClickListener(this);
        outfitsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        outfitsRecyclerView.setAdapter(outfitsAdapter);

        planFashionItemIds = new ArrayList<>();
        planFashionItems = new HashMap<>();
        fashionItemsAdapter = new PlannerDetailFashionItemsRecyclerAdapter(planFashionItemIds, planFashionItems);
        fashionItemsAdapter.setOnItemClickListener(this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        fashionItemsRecyclerView.setLayoutManager(horizontalLayoutManager);
        fashionItemsRecyclerView.setAdapter(fashionItemsAdapter);

        Intent i = getIntent();
        plan_id = i.getStringExtra(ConstantValue.PLANNER_DETAIL_ID);
        enableEdit(i.getStringExtra(ConstantValue.PLANNER_DETAIL_MODE).compareTo(ConstantValue.STATE_EDITABLE) == 0);

        mOutfitsViewModel.getOutfitItemListLiveData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<OutfitItem>>() {
            @Override
            public void onChanged(ArrayList<OutfitItem> outfitItems) {
                allOutfitItems = outfitItems;
                updatePlanOutfitItems();
            }
        });

        mFashionItemsViewModel.getFashionItemListLiveData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<FashionItem>>() {
            @Override
            public void onChanged(ArrayList<FashionItem> fashionItems) {
                allFashionItems = fashionItems;
                updatePlanFashionItems();
            }
        });

        mPlannerViewModel.getUserPlanData(plan_id).observe(this, new Observer<UserPlanData>() {
            @Override
            public void onChanged(UserPlanData userPlanData) {
                if (planOutfitItemIds.size() != 0) {
                    planOutfitItemIds.clear();
                }
                planOutfitItemIds.addAll(new ArrayList<String>(Arrays.asList(userPlanData.getOutFitsSerialize().split("\\|"))));

                if (planFashionItemIds.size() != 0) {
                    planFashionItemIds.clear();
                }
                planFashionItemIds.addAll(new ArrayList<String>(Arrays.asList(userPlanData.getFItemsSerialize().split("\\|"))));
                plan = userPlanData;
                updatePlanOutfitItems();
                updatePlanFashionItems();

                updateContent();
            }
        });
    }

    @Override
    public void onClickOutfitItem(int position) {
        UseLog.d("Outfit clicked");

        // Toast.makeText(getBaseContext(), "day clicked", Toast.LENGTH_SHORT).show();
        if (isEditable) {
            // delete item?
        }
        else {
            // view outfit detail
            if (position < planOutfitItemIds.size()) {
                Intent i = new Intent(this, OutfitDetailActivity.class);
                i.putExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID, planOutfitItemIds.get(position));
                startActivity(i);
            }
        }
    }

    @Override
    public void onClickFashionItem(int position) {
        UseLog.d("Fashion clicked");

        // Toast.makeText(getBaseContext(), "day clicked", Toast.LENGTH_SHORT).show();
        if (isEditable) {
            // delete item?
        }
        else {
            if (position < planFashionItemIds.size()) {
                Intent i = new Intent(this, FashionItemDetailActivity.class);
                i.putExtra(ConstantValue.FASHION_ITEM_CLICK_ID, planFashionItemIds.get(position));
                startActivity(i);
            }
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        if (wornDate != null) {
            String returnDayString = (new SimpleDateFormat("yyyyMMdd")).format(wornDate.getTimeInMillis());
            data.putExtra(ConstantValue.PLANNER_CLICK_DAY, returnDayString);
        }
        setResult(RESULT_OK, data);

        super.finish();
    }

    private void enableEdit(boolean isEditable) {
        this.isEditable = isEditable;

        confirmButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);
        cancelButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);

        editButton.setVisibility(isEditable ? View.INVISIBLE : View.VISIBLE);
        deleteButton.setVisibility(isEditable ? View.INVISIBLE : View.VISIBLE);

        itemDescriptionInput.setInputType(isEditable ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_NULL);
        wornDateInput.setInputType(isEditable ? InputType.TYPE_DATETIME_VARIATION_DATE : InputType.TYPE_NULL);

        addOutfitButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);
        addFashionItemButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);

        outfitsAdapter.notifyUpdateEditable(isEditable);
        fashionItemsAdapter.notifyUpdateEditable(isEditable);
    }

    private void updatePlanOutfitItems() {
        if (plan != null && allOutfitItems != null) {
            for (OutfitItem outfitItem : allOutfitItems) {
                int idx = planOutfitItemIds.indexOf(outfitItem.get_id());
                if (idx >= 0) {
                    planOutfitItems.put(outfitItem.get_id(), outfitItem);
                }
            }
            outfitsAdapter.notifyDataSetChanged();
        }
    }

    private void updatePlanFashionItems() {
        if (plan != null && allFashionItems != null) {
            for (FashionItem fashionItem : allFashionItems) {
                int idx = planFashionItemIds.indexOf(fashionItem.get_id());
                if (idx >= 0) {
                    planFashionItems.put(fashionItem.get_id(), fashionItem);
                }
            }
            fashionItemsAdapter.notifyDataSetChanged();
        }
    }

    private boolean isInputValid() {
        View viewInFocus = this.getCurrentFocus();
        if (viewInFocus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewInFocus.getWindowToken(), 0);
        }

        String wornDateString = wornDateInput.getText().toString();

        try {
            if (wornDateString.length() != 10) throw new Exception("Date format should be yyyy-mm-dd!");

            String[] datePart = wornDateString.split("-");
            if (datePart.length != 3) throw new Exception("Date format should be yyyy-mm-dd!");

            if (wornDate == null) {
                wornDate = Calendar.getInstance();
                wornDate.setTimeInMillis(0); // just to ensure time is 00:00:00.000
            }

            int yyyy = Integer.parseInt(datePart[0]);
            int currYear = Calendar.getInstance().get(Calendar.YEAR);
            if (yyyy < 1900 || yyyy > currYear + 5) throw new Exception("Year should be between 1900 and " + (currYear + 5) + "!");

            int mm = Integer.parseInt(datePart[1]);
            if (mm < 1 || mm > 12) throw new Exception("Month should be between 1 and 12!");

            int dd = Integer.parseInt(datePart[2]);
            if (dd < 1) throw new Exception("Day of month should be greater than 1!");

            wornDate.set(yyyy, mm - 1, dd);
            if (wornDate.get(Calendar.DATE) != dd) throw new Exception("Day of month is too large!");
        }
        catch (NumberFormatException e) {
            Snackbar warning = Snackbar.make(wornDateInput, "Date format should be yyyy-mm-dd!", Snackbar.LENGTH_LONG);
            warning.getView().setBackgroundColor(Color.RED);
            warning.show();

            return false;
        }
        catch (Exception e) {
            Snackbar warning = Snackbar.make(wornDateInput, e.getMessage(), Snackbar.LENGTH_LONG);
            warning.getView().setBackgroundColor(Color.RED);
            warning.show();

            return false;
        }

        if (planOutfitItemIds.size() == 0 && planFashionItemIds.size() == 0) {
            String msg = "Either outfit or fashion item has to be selected!";
            Snackbar warning = Snackbar.make(wornDateInput, msg, Snackbar.LENGTH_LONG);
            warning.getView().setBackgroundColor(Color.RED);
            warning.show();

            return false;
        }

        return true;
    }

    private void updateContent() {
        itemDescriptionInput.setText(plan.getItemDescription());
        if (wornDate == null) {
            wornDate = Calendar.getInstance();
        }
        wornDate.setTimeInMillis(Long.parseLong(plan.getWornDate()));
        wornDateInput.setText((new SimpleDateFormat("yyyy-MM-dd")).format(Long.parseLong(plan.getWornDate())));
    }
}