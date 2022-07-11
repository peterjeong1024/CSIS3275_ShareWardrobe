package com.example.sharewardrobeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.example.sharewardrobeapp.interfaces.RetrofitClient;
import com.example.sharewardrobeapp.main.MainRecyclerAdapter;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BasementActivity implements MainRecyclerAdapter.OnMenuClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MainRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainRecyclerAdapter();
        adapter.setOnMenuClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClickMenuItem(int position) {
        switch(position) {
            case 0: // Items
                startActivity(new Intent(this, ItemsActivity.class));
                break;
            case 1: // Outfit
                startActivity(new Intent(this, OutfitsActivity.class));
                break;
            case 2: // Wardrobe Stats
                startActivity(new Intent(this, StatsActivity.class));
                break;
            case 3: // Planners
                startActivity(new Intent(this, PlannerActivity.class));
                break;
            case 4: // Consultations
                startActivity(new Intent(this, ConsultActivity.class));
                break;
            default:
                break;
        }
    }


}