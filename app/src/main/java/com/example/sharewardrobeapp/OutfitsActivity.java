 package com.example.sharewardrobeapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.outfits.OutfitDetailActivity;
import com.example.sharewardrobeapp.outfits.OutfitRecommendDetailActivity;
import com.example.sharewardrobeapp.outfits.OutfitsRecyclerAdapter;
import com.example.sharewardrobeapp.outfits.OutfitsViewModel;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

public class OutfitsActivity extends BasementActivity implements OutfitsRecyclerAdapter.OnItemClick {

    private RecyclerView mUserItemsRecyclerView;
    private RecyclerView mRecommendItemsRecyclerView;
    private RecyclerView.LayoutManager mUserItemLayoutManager;
    private RecyclerView.LayoutManager mRecommendLayoutManager;
    private OutfitsRecyclerAdapter mUserItemAdapter;
    private OutfitsRecyclerAdapter mRecommendItemAdapter;
    private OutfitsViewModel mViewModel;

    private ArrayList<OutfitItem> mUserOutfitItemList;
    private ArrayList<OutfitItem> mRecommendOutfitItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);

        mUserItemsRecyclerView = findViewById(R.id.outfits_recycler_view);
        mRecommendItemsRecyclerView = findViewById(R.id.recommend_recycler_view);
        mViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);

        mViewModel.getOutfitAllListLiveData().observe(this, new Observer<ArrayList<OutfitItem>>() {
            @Override
            public void onChanged(ArrayList<OutfitItem> outfitItems) {
                mUserOutfitItemList = new ArrayList<>();
                mRecommendOutfitItemList = new ArrayList<>();
                String userID = getUserAccount().getUserID();
                for(OutfitItem oi : outfitItems) {
                    if (oi.getOutfitOwnerID().equals(userID)) {
                        mUserOutfitItemList.add(oi);
                    } else {
                        mRecommendOutfitItemList.add(oi);
                    }
                }

                drawItemList();
            }
        });
    }

    private void drawItemList() {
        mUserItemLayoutManager = new GridLayoutManager(this, 3);
        mUserItemsRecyclerView.setLayoutManager(mUserItemLayoutManager);
        mUserItemAdapter = new OutfitsRecyclerAdapter(mUserOutfitItemList);
        mUserItemAdapter.setOnItemClickListener(this);
        mUserItemsRecyclerView.setAdapter(mUserItemAdapter);

        mRecommendLayoutManager = new GridLayoutManager(this, 3);
        mRecommendItemsRecyclerView.setLayoutManager(mRecommendLayoutManager);
        mRecommendItemAdapter = new OutfitsRecyclerAdapter(mRecommendOutfitItemList);
        mRecommendItemAdapter.setOnItemClickListener(this);
        mRecommendItemsRecyclerView.setAdapter(mRecommendItemAdapter);

        findViewById(R.id.outfit_list_divide_textview).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickItem(int position, String id) {
        String userID = getUserAccount().getUserID();
        Intent i;
        if (id.equals(userID)) {
            i = new Intent(this, OutfitDetailActivity.class);
            i.putExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID, mUserOutfitItemList.get(position).get_id());
        } else {
            i = new Intent(this, OutfitRecommendDetailActivity.class);
            i.putExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID, mRecommendOutfitItemList.get(position).get_id());
        }
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItemMenu:
                startActivity(new Intent(this, OutfitDetailActivity.class));
                return true;
            case R.id.settingMenu:
                UseLog.v("settingMenu");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}