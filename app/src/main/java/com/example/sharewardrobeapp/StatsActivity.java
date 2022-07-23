package com.example.sharewardrobeapp;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.sharewardrobeapp.objects.FashionItem;
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
    }

    private void displayScreen(ArrayList<FashionItem> fashionItems) {
        // drawing code is here

    }



}