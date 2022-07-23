package com.example.sharewardrobeapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sharewardrobeapp.fashionitems.FashionItemDetailActivity;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.outfits.OutfitDetailActivity;
import com.example.sharewardrobeapp.outfits.OutfitsRecyclerAdapter;
import com.example.sharewardrobeapp.outfits.OutfitsViewModel;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

public class OutfitsActivity extends BasementActivity implements OutfitsRecyclerAdapter.OnItemClick {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private OutfitsRecyclerAdapter adapter;
    private OutfitsViewModel mViewModel;

    private ArrayList<OutfitItem> mOutfitItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);

        recyclerView = findViewById(R.id.outfits_recycler_view);
        mViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);

        mViewModel.getOutfitItemListLiveData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<OutfitItem>>() {
            @Override
            public void onChanged(ArrayList<OutfitItem> outfitItems) {
                mOutfitItemList = outfitItems;
                drawItemList(outfitItems);
            }
        });
    }

    private void drawItemList(ArrayList<OutfitItem> outfitItemList) {
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OutfitsRecyclerAdapter(outfitItemList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
        Intent i = new Intent(this, OutfitDetailActivity.class);
        i.putExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID, mOutfitItemList.get(position).get_id());
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