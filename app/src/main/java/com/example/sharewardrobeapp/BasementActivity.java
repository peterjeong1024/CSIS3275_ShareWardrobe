package com.example.sharewardrobeapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharewardrobeapp.util.UseLog;

public class BasementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);


        UseLog.d("Activity Name : " + getLocalClassName());

        switch (getLocalClassName()) {
            case "FashionItemsActivity":
                setTitle(getResources().getString(R.string.items_text));
                break;
            case "OutfitsActivity" :
                setTitle(getResources().getString(R.string.outfits_text));
                break;
            case "StatsActivity" :
                setTitle(getResources().getString(R.string.wardrobe_stats_text));
                break;
            case "PlannerActivity" :
                setTitle(getResources().getString(R.string.planner_text));
                break;
            case "ConsultActivity" :
                setTitle(getResources().getString(R.string.consultations_text));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar ab = getSupportActionBar();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItemMenu:
                UseLog.v("addItemMenu");
                return true;
            case R.id.settingMenu:
                UseLog.v("settingMenu");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}