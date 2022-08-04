package com.example.sharewardrobeapp.outfits;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.interfaces.GlideApp;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class OutfitRecommendDetailActivity extends BasementActivity {

    private OutfitsViewModel mViewModel;
    private OutfitDetailItemListViewAdapter mCurrentItemListViewAdapter;

    private ImageView mImageView;

    private TextView mCategory;
    private ListView mCurrentItemListView;

    private OutfitItem mOutfitItem;
    private ArrayList<FashionItem> mCurrentItemList;
    private String selectedItemID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_recommend_item_detail);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getResources().getString(R.string.items_text));

        mImageView = findViewById(R.id.of_imageview);
        mCategory = findViewById(R.id.of_category);
        mCurrentItemListView = findViewById(R.id.of_listview_current);

        mCurrentItemList = new ArrayList<>();
        mCurrentItemListViewAdapter = new OutfitDetailItemListViewAdapter(this, mCurrentItemList);
        mCurrentItemListView.setAdapter(mCurrentItemListViewAdapter);

        mViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);

        if (getIntent().getStringExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID) != null) {
            selectedItemID = getIntent().getStringExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID);
            mViewModel.getOutfitItemData(selectedItemID).observe(this, new Observer<OutfitItem>() {
                @Override
                public void onChanged(OutfitItem outfitItem) {
                    mOutfitItem = outfitItem;
                    drawScreenData();
                }
            });
        }
    }

    private void drawScreenData() {
        if (!mOutfitItem.getOutfitImg().equals("")) {
            GlideApp.with(getApplicationContext()).load(mOutfitItem.getOutfitImgBitmap()).into(mImageView);
            mImageView.setVisibility(View.VISIBLE);
        }

        mCategory.setText(mOutfitItem.getOutfitCateName());

        ArrayList<String> list = new ArrayList<String>();
        String[] itemListArray = mOutfitItem.getFItemsSerialize().split("\\|");
        Collections.addAll(list, itemListArray);

        mViewModel.getOutfitDetailItemListData(mOutfitItem.getOutfitOwnerID()).observe(this, new Observer<ArrayList<FashionItem>>() {
            @Override
            public void onChanged(ArrayList<FashionItem> fashionItems) {
                mCurrentItemList.clear();
                for (FashionItem f : fashionItems) {
                    if (list.contains(f.get_id())) {
                        mCurrentItemList.add(f);
                    }
                }
                drawListview();
            }
        });

    }

    private void drawListview() {
        mCurrentItemListViewAdapter.reDrawList(mCurrentItemList);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UseLog.i("onDestroy");
        mOutfitItem = null;
        mCurrentItemList = new ArrayList<>();
        mCurrentItemListViewAdapter.reDrawList(mCurrentItemList);
        mCurrentItemList.clear();
        mViewModel.onCleared();
    }
}
