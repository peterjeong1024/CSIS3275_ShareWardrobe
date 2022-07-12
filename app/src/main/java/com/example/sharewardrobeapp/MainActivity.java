package com.example.sharewardrobeapp;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.sharewardrobeapp.main.MainRecyclerAdapter;

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
                startActivity(new Intent(this, FashionItemsActivity.class));
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