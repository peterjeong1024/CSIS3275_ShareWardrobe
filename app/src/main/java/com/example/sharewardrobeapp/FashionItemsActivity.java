package com.example.sharewardrobeapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.fashionitems.FashionItemsViewModel;
import com.example.sharewardrobeapp.fashionitems.FashionItemsRecyclerAdapter;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

public class FashionItemsActivity extends BasementActivity implements FashionItemsRecyclerAdapter.OnItemClick {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FashionItemsRecyclerAdapter adapter;
    private FashionItemsViewModel mViewModel;

    private ArrayList<FashionItem> mFashionItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        recyclerView = findViewById(R.id.items_recycler_view);
        mViewModel = new ViewModelProvider(this).get(FashionItemsViewModel.class);

        mViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                Toast.makeText(getApplicationContext(), "Loading user data", Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getFashionItemListLiveData().observe(this, new Observer<ArrayList<FashionItem>>() {
            @Override
            public void onChanged(ArrayList<FashionItem> fashionItems) {
                mViewModel.getIsLoading().postValue(false);
                drawItemList(fashionItems);
            }
        });
    }

    private void drawItemList(ArrayList<FashionItem> fashionItemList) {
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FashionItemsRecyclerAdapter(fashionItemList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {
        UseLog.d("click item : " + mFashionItemList.get(position).getItemName());
    }
}