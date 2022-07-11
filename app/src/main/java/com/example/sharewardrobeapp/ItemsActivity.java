package com.example.sharewardrobeapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.interfaces.RetrofitClient;
import com.example.sharewardrobeapp.items.ItemsRecyclerAdapter;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsActivity extends BasementActivity implements ItemsRecyclerAdapter.OnItemClick {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ItemsRecyclerAdapter adapter;

    private ArrayList<FashionItem> mFashionItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        recyclerView = findViewById(R.id.items_recycler_view);

        // call retrofit interface to get fashion item list
        Call<ArrayList<FashionItem>> getData = RetrofitClient.getApi().getFashionItemList();
        getData.enqueue(new Callback<ArrayList<FashionItem>>() {
            @Override
            public void onResponse(Call<ArrayList<FashionItem>> call, Response<ArrayList<FashionItem>> response) {
                mFashionItemList = response.body();
                drawItemList(mFashionItemList);
            }

            @Override
            public void onFailure(Call<ArrayList<FashionItem>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void drawItemList(ArrayList<FashionItem> fashionItemList) {
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemsRecyclerAdapter(fashionItemList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
        UseLog.d("click item : " + mFashionItemList.get(position).getItemName());
    }
}