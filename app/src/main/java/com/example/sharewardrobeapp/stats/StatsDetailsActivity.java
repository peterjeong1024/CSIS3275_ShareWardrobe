package com.example.sharewardrobeapp.stats;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;

public class StatsDetailsActivity extends BasementActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_details);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        menu.clear();

        return true;
    }
}