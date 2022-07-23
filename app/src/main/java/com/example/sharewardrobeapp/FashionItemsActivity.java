package com.example.sharewardrobeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.fashionitems.FashionItemDetailActivity;
import com.example.sharewardrobeapp.fashionitems.FashionItemsViewModel;
import com.example.sharewardrobeapp.fashionitems.FashionItemsRecyclerAdapter;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.util.ConstantValue;
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

        mViewModel.getFashionItemListLiveData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<FashionItem>>() {
            @Override
            public void onChanged(ArrayList<FashionItem> fashionItems) {
                mFashionItemList = fashionItems;
                drawItemList(mFashionItemList);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItemMenu:
                startActivity(new Intent(this, FashionItemDetailActivity.class));
                return true;
            case R.id.settingMenu:
                UseLog.v("settingMenu");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickItem(int position) {
        Intent i = new Intent(this, FashionItemDetailActivity.class);
        i.putExtra(ConstantValue.FASHION_ITEM_CLICK_ID, mFashionItemList.get(position).get_id());
        startActivity(i);
    }
}