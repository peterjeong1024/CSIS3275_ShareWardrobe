package com.example.sharewardrobeapp;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.outfits.OutfitsRecyclerAdapter;
import com.example.sharewardrobeapp.outfits.OutfitsViewModel;
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

        mViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                Toast.makeText(getApplicationContext(), "Loading user data", Toast.LENGTH_SHORT).show();
            }
        });

        UseLog.d(getUserAccount().getUserID());

        mViewModel.getOutfitItemListLiveData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<OutfitItem>>() {
            @Override
            public void onChanged(ArrayList<OutfitItem> outfitItems) {
                mViewModel.getIsLoading().postValue(false);
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
        UseLog.d("click item : " + mOutfitItemList.get(position).getOutfitCateName());
    }
}