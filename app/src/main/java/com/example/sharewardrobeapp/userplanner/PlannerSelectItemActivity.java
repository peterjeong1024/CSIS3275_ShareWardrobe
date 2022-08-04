package com.example.sharewardrobeapp.userplanner;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.fashionitems.FashionItemsViewModel;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.outfits.OutfitsViewModel;
import com.example.sharewardrobeapp.util.ConstantValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class PlannerSelectItemActivity extends BasementActivity implements PlannerItemSelectAdapter.OnItemClick {

    String ignoreIdString;
    RecyclerView itemRecyclerView;
    PlannerItemSelectAdapter itemAdapter;
    ArrayList<String> ignoreIds;
    ArrayList<String> ids;
    ArrayList<String> descriptions;
    ArrayList<Bitmap> bitmaps;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_select_item);

        ignoreIds = new ArrayList<>();
        ids = new ArrayList<>();
        descriptions = new ArrayList<>();
        bitmaps = new ArrayList<>();

        itemRecyclerView = findViewById(R.id.item_recycler_view);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        itemAdapter = new PlannerItemSelectAdapter(ids, descriptions, bitmaps);
        itemAdapter.setOnItemClickListener(this);
        itemRecyclerView.setAdapter(itemAdapter);

        Intent i = getIntent();
        String outfitItemIds = i.getStringExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID);
        String fashionItemIds = i.getStringExtra(ConstantValue.FASHION_ITEM_CLICK_ID);
        if (outfitItemIds != null) {
            type = ConstantValue.OUTFIT_ITEM_CLICK_ID;
            setTitle("Choose one outfit");
            ignoreIdString = outfitItemIds;
            ignoreIds.addAll(Arrays.asList(ignoreIdString.split("\\|")));
            OutfitsViewModel vm = new ViewModelProvider(this).get(OutfitsViewModel.class);
            vm.getOutfitItemListLiveData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<OutfitItem>>() {
                @Override
                public void onChanged(ArrayList<OutfitItem> outfitItems) {
                    if (ids.size() == 0) {
                        for (OutfitItem item : outfitItems) {
                            if (ignoreIds.indexOf(item.get_id()) == -1) {
                                ids.add(item.get_id());
                                descriptions.add(item.getOutfitCateName());
                                bitmaps.add(item.getOutfitImgBitmap());
                            }
                        }
                        itemAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else if (fashionItemIds != null) {
            type = ConstantValue.FASHION_ITEM_CLICK_ID;
            setTitle("Choose one fashion item");
            ignoreIdString = fashionItemIds;
            ignoreIds.addAll(Arrays.asList(ignoreIdString.split("\\|")));
            FashionItemsViewModel vm = new ViewModelProvider(this).get(FashionItemsViewModel.class);
            vm.getFashionItemListLiveData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<FashionItem>>() {
                @Override
                public void onChanged(ArrayList<FashionItem> fashionItems) {
                    if (ids.size() == 0) {
                        for (FashionItem item : fashionItems) {
                            if (ignoreIds.indexOf(item.get_id()) == -1) {
                                ids.add(item.get_id());
                                descriptions.add(item.getItemName());
                                bitmaps.add(item.getItemImgBitmap());
                            }
                        }
                        itemAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onClickItem(String id) {
        Intent data = new Intent();
        data.putExtra(type, id);
        setResult(RESULT_OK, data);

        finish();
    }
}