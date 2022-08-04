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
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

public class OutfitDetailActivity extends BasementActivity {

    private OutfitsViewModel mViewModel;
    private OutfitDetailItemListViewAdapter mCurrentItemListViewAdapter;
    private OutfitDetailItemListViewAdapter mAddItemListViewAdapter;

    private ImageView mImageView;
    private Button mLoadPicture;
    private Button mTakePicture;

    private EditText mCategory;
    private ListView mCurrentItemListView;
    private ListView mAddItemListView;
    private Button mAddItems;
    private Button mDeleteItems;
    private Button mConfirm;

    private boolean isEditMode = false;
    private OutfitItem mOutfitItem;
    private ArrayList<FashionItem> mUserFashionItemList;
    private ArrayList<FashionItem> mCurrentItemList;
    private ArrayList<FashionItem> mAddItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_item_detail);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getResources().getString(R.string.items_text));

        mImageView = findViewById(R.id.of_imageview);
        mCategory = findViewById(R.id.of_category);
        mCurrentItemListView = findViewById(R.id.of_listview_current);
        mAddItemListView = findViewById(R.id.of_listview_add);

        mConfirm = findViewById(R.id.of_confirm);
        mLoadPicture = findViewById(R.id.of_load_picture);
        mTakePicture = findViewById(R.id.of_take_picture);
        mAddItems = findViewById(R.id.of_add_item);
        mDeleteItems = findViewById(R.id.of_delete_item);

        mConfirm.setOnClickListener(mButtonCLickListener);
        mLoadPicture.setOnClickListener(mButtonCLickListener);
        mTakePicture.setOnClickListener(mButtonCLickListener);
        mAddItems.setOnClickListener(mButtonCLickListener);
        mDeleteItems.setOnClickListener(mButtonCLickListener);

        mViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);

        if (getIntent().getParcelableExtra(ConstantValue.OUTFIT_ITEM_CLICK_OBJECT) != null) {
            isEditMode = true;
            mOutfitItem = getIntent().getParcelableExtra(ConstantValue.OUTFIT_ITEM_CLICK_OBJECT);
            UseLog.d(mOutfitItem.toString());
        }
        drawScreenData();
    }

    private void drawScreenData() {
        if (isEditMode) {
            if (!mOutfitItem.getOutfitImg().equals("")) {
                GlideApp.with(getApplicationContext())
                        .load(mOutfitItem.getOutfitImgBitmap())
                        .into(mImageView);
                mImageView.setVisibility(View.VISIBLE);
            }
            mCategory.setText(mOutfitItem.getOutfitCateName());
        }

        mViewModel.getOutfitDetailItemListData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<FashionItem>>() {
            @Override
            public void onChanged(ArrayList<FashionItem> fashionItems) {
                mUserFashionItemList = fashionItems;
                drawItemListData(fashionItems);
            }
        });
    }

    private void drawItemListData(ArrayList<FashionItem> fashionItems) {
        mCurrentItemList = new ArrayList<>();
        mAddItemList = new ArrayList<>();
        if (mOutfitItem != null) {
            ArrayList<String> list = new ArrayList<String>();
            String[] itemListArray = mOutfitItem.getFItemsSerialize().split("\\|");
            Collections.addAll(list, itemListArray);

            for (FashionItem f : fashionItems) {
                if (list.contains(f.get_id())) {
                    mCurrentItemList.add(f);
                } else {
                    mAddItemList.add(f);
                }
            }
        } else {
            mAddItemList.addAll(mUserFashionItemList);
        }

        mCurrentItemListViewAdapter = new OutfitDetailItemListViewAdapter(this, mCurrentItemList);
        mCurrentItemListView.setAdapter(mCurrentItemListViewAdapter);
        mAddItemListViewAdapter = new OutfitDetailItemListViewAdapter(this, mAddItemList);
        mAddItemListView.setAdapter(mAddItemListViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        return true;
    }

    View.OnClickListener mButtonCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int btnId = view.getId();
            switch (btnId) {
                case R.id.of_confirm:
                    if (mCategory.getText().length() == 0 || mImageView.getVisibility() == View.GONE) {
                        Toast.makeText(getApplicationContext(), getText(R.string.insert_data_correctly), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isEditMode) {
                        mOutfitItem.setOutfitOwnerID(getUserAccount().getUserID());
                        mOutfitItem.setOutfitCateName(mCategory.getText().toString());

                        UseLog.d(mCurrentItemList.size() + " : mCurrentItemList.size()");
                        StringBuilder itemListSerialized = new StringBuilder();
                        for (FashionItem f : mCurrentItemList) {
                            itemListSerialized.append(f.get_id());
                            itemListSerialized.append("|");
                        }
                        itemListSerialized.deleteCharAt(itemListSerialized.lastIndexOf("|"));
                        UseLog.d(itemListSerialized.toString());
                        mOutfitItem.setFItemsSerialize(itemListSerialized.toString());

                        if (mImageView.getVisibility() == View.VISIBLE) {
                            BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] bb = bos.toByteArray();
                            String imageBase64 = Base64.encodeToString(bb, 0);
                            mOutfitItem.setOutfitImg(imageBase64);
                            UseLog.d(imageBase64.length() + " : imageBase64.length()");
                        }
                        mViewModel.updateOutfitItem(mOutfitItem);
                    } else {
                        OutfitItem outfitItem = new OutfitItem();
                        outfitItem.setOutfitOwnerID(getUserAccount().getUserID());
                        outfitItem.setOutfitCateName(mCategory.getText().toString());

                        UseLog.d(mCurrentItemList.size() + " : mCurrentItemList.size()");
                        StringBuilder itemListSerialized = new StringBuilder();
                        for (FashionItem f : mCurrentItemList) {
                            itemListSerialized.append(f.get_id());
                            itemListSerialized.append("|");
                        }
                        itemListSerialized.deleteCharAt(itemListSerialized.lastIndexOf("|"));
                        UseLog.d(itemListSerialized.toString());
                        outfitItem.setFItemsSerialize(itemListSerialized.toString());

                        if (mImageView.getVisibility() == View.VISIBLE) {
                            BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] bb = bos.toByteArray();
                            String imageBase64 = Base64.encodeToString(bb, 0);
                            outfitItem.setOutfitImg(imageBase64);
                            UseLog.d(imageBase64.length() + " : imageBase64.length()");
                        }

                        mViewModel.addOutfitItem(outfitItem);
                    }
                    finish();
                    break;

                case R.id.of_load_picture:
                    Intent loadImageIntent = new Intent(Intent.ACTION_PICK);
                    loadImageIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    mLoadPictureForResult.launch(loadImageIntent);
                    break;

                case R.id.of_take_picture:
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mTakePictureForResult.launch(cameraIntent);
                    break;
                case R.id.of_add_item:
                    for (int i = mAddItemListView.getChildCount() - 1; i >= 0; i--) {
                        if (((CheckableItemLayout) mAddItemListView.getChildAt(i)).isChecked()) {
                            FashionItem removeItem = mAddItemList.remove(i);
                            mCurrentItemList.add(removeItem);
                        }
                    }
                    mCurrentItemListViewAdapter.reDrawList(mCurrentItemList);
                    mAddItemListViewAdapter.reDrawList(mAddItemList);
                    break;
                case R.id.of_delete_item:
                    for (int i = mCurrentItemListView.getChildCount() - 1; i >= 0; i--) {
                        if (((CheckableItemLayout) mCurrentItemListView.getChildAt(i)).isChecked()) {
                            FashionItem removeItem = mCurrentItemList.remove(i);
                            mAddItemList.add(removeItem);
                        }
                    }
                    mCurrentItemListViewAdapter.reDrawList(mCurrentItemList);
                    mAddItemListViewAdapter.reDrawList(mAddItemList);
                    break;
            }
        }
    };

    ActivityResultLauncher<Intent> mLoadPictureForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        UseLog.i("Activity.RESULT_OK");
                        Uri uri = result.getData().getData();
                        GlideApp.with(getApplicationContext())
                                .load(uri)
                                .into(mImageView);
                        mImageView.setVisibility(View.VISIBLE);
                        break;
                    case Activity.RESULT_CANCELED:
                        UseLog.i("Activity.RESULT_CANCELED");
                        break;
                    default:
                        break;
                }
            }
    );

    ActivityResultLauncher<Intent> mTakePictureForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        UseLog.i("Activity.RESULT_OK");
                        Bundle bundle = result.getData().getExtras();
                        GlideApp.with(getApplicationContext())
                                .load((Bitmap) bundle.get("data"))
                                .into(mImageView);
                        mImageView.setVisibility(View.VISIBLE);
                        break;
                    case Activity.RESULT_CANCELED:
                        UseLog.i("Activity.RESULT_CANCELED");
                        break;
                    default:
                        break;
                }
            }
    );

    @Override
    protected void onDestroy() {
        UseLog.i("Activity.RESULT_OK");
        mOutfitItem = null;
        mUserFashionItemList = mCurrentItemList = mAddItemList = new ArrayList<>();
        mAddItemListViewAdapter.reDrawList(mAddItemList);
        mCurrentItemListViewAdapter.reDrawList(mCurrentItemList);
        mCurrentItemList.clear();
        mAddItemList.clear();
        mImageView.setImageDrawable(null);
        mImageView.setVisibility(View.GONE);
        super.onDestroy();
    }
}
