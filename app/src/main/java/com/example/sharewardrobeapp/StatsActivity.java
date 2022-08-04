package com.example.sharewardrobeapp;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.sharewardrobeapp.fashionitems.FashionItemDetailActivity;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.stats.Brand_Stats;
import com.example.sharewardrobeapp.stats.StatsDetailsActivity;
import com.example.sharewardrobeapp.stats.StatsViewModel;

import java.util.ArrayList;

public class StatsActivity extends BasementActivity {

    private StatsViewModel mViewModel;
    private ArrayList<FashionItem> mFashionItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // sample code to use ViewModel object
        // create new ViewModel object
        mViewModel = new ViewModelProvider(this).get(StatsViewModel.class);
        // bring data from ViewModel object. data is brought automatically
        mViewModel.getFashionItemListLiveData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<FashionItem>>() {
            @Override
            public void onChanged(ArrayList<FashionItem> fashionItems) {
                // data(fashionItems) is brought here  automatically and asynchronously
                mFashionItems = fashionItems;
                displayScreen(fashionItems);
            }
        });

        Button btnColorStats = findViewById(R.id.btn_ColorStats);

        btnColorStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(v.getContext(), StatsDetailsActivity.class)));
            }
        });

        Button btnBrandStats = findViewById(R.id.btn_BrandStats);

        btnBrandStats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity((new Intent(v.getContext(), Brand_Stats.class)));
            }
        });
    }

    private void displayScreen(ArrayList<FashionItem> fashionItems) {
        // drawing code is here

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        menu.clear();

        return true;
    }



}