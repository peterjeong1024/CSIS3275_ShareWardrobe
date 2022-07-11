package com.example.sharewardrobeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sharewardrobeapp.interfaces.RetrofitClient;
import com.example.sharewardrobeapp.items.ItemsRecyclerAdapter;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.outfits.OutfitsRecyclerAdapter;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutfitsActivity extends BasementActivity implements OutfitsRecyclerAdapter.OnItemClick {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private OutfitsRecyclerAdapter adapter;

    private ArrayList<OutfitItem> mOutfitItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);

        recyclerView = findViewById(R.id.outfits_recycler_view);

        // call retrofit interface to get fashion item list
        Call<ArrayList<OutfitItem>> getData = RetrofitClient.getApi().getOutfitItemList();
        getData.enqueue(new Callback<ArrayList<OutfitItem>>() {
            @Override
            public void onResponse(Call<ArrayList<OutfitItem>> call, Response<ArrayList<OutfitItem>> response) {
                mOutfitItemList = response.body();
                drawItemList(mOutfitItemList);
            }

            @Override
            public void onFailure(Call<ArrayList<OutfitItem>> call, Throwable t) {
                t.printStackTrace();
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