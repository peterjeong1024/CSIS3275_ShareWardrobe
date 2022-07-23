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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class OutfitDetailActivity extends BasementActivity {

    private OutfitsViewModel mViewModel;
    private DataRepository repository = DataRepository.getInstance();
    private OutfitDetailItemListViewAdapter mOutfitDetailItemListViewAdapter;

    private ImageView mImageView;
    private Button mLoadPicture;
    private Button mTakePicture;

    private EditText mCategory;
    private ListView mListview;
    private Button mConfirm;

    private boolean isEditMode = false;
    private OutfitItem mOutfitItem;
    private ArrayList<FashionItem> mOutfitItemlist;
    private String selectedItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_item_detail);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getResources().getString(R.string.items_text));

        mImageView = findViewById(R.id.of_imageview);
        mCategory = findViewById(R.id.of_category);
        mListview = findViewById(R.id.of_listview);

        mConfirm = findViewById(R.id.of_confirm);
        mLoadPicture = findViewById(R.id.of_load_picture);
        mTakePicture = findViewById(R.id.of_take_picture);

        mConfirm.setOnClickListener(mButtonCLickListener);
        mLoadPicture.setOnClickListener(mButtonCLickListener);
        mTakePicture.setOnClickListener(mButtonCLickListener);

        // check it is Add mode or Edit mode
        if (getIntent().getStringExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID) != null) {
            isEditMode = true;
            selectedItemID = getIntent().getStringExtra(ConstantValue.OUTFIT_ITEM_CLICK_ID);
            mViewModel = new ViewModelProvider(this).get(OutfitsViewModel.class);
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
        if (isEditMode) {
            mCategory.setText(mOutfitItem.getOutfitCateName());
            if (!mOutfitItem.getOutfitImg().equals("")) {
                mImageView.setImageBitmap(mOutfitItem.getOutfitImgBitmap());
                mImageView.setVisibility(View.VISIBLE);
            }

            mViewModel.getOutfitDetailItemListData(getUserAccount().getUserID()).observe(this, new Observer<ArrayList<FashionItem>>() {
                @Override
                public void onChanged(ArrayList<FashionItem> fashionItems) {
                    mOutfitItemlist = fashionItems;
                    drawItemListData(fashionItems);
                }
            });
        }
    }

    private void drawItemListData(ArrayList<FashionItem> fashionItems) {
        ArrayList<FashionItem> displayList = new ArrayList<>();
        String[] itemListArray = mOutfitItem.getFItemsSerialize().split("\\|");
        ArrayList<String> list = new ArrayList<String>();
        Collections.addAll(list, itemListArray);

        for (FashionItem f : fashionItems) {
            if (list.contains(f.get_id())) {
                displayList.add(f);
            }
        }
        UseLog.d("" + displayList.size());
        mOutfitDetailItemListViewAdapter = new OutfitDetailItemListViewAdapter(this, displayList);
        mListview.setAdapter(mOutfitDetailItemListViewAdapter);
        mOutfitDetailItemListViewAdapter.notifyDataSetChanged();
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
                    if (isEditMode) {
                        mOutfitItem.setOutfitOwnerID(getUserAccount().getUserID());
                        mOutfitItem.setOutfitCateName(mCategory.getText().toString());
                        if (mImageView.getVisibility() == View.VISIBLE) {
                            BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] bb = bos.toByteArray();
                            String imageBase64 = Base64.encodeToString(bb, 0);
                            mOutfitItem.setOutfitImg(imageBase64);
                        }
                        repository.updateOutfitItem(mOutfitItem);
                    } else {
                        OutfitItem f = new OutfitItem();
                        f.setOutfitOwnerID(getUserAccount().getUserID());
                        f.setOutfitCateName(mCategory.getText().toString());
                        if (mImageView.getVisibility() == View.VISIBLE) {
                            BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] bb = bos.toByteArray();
                            String imageBase64 = Base64.encodeToString(bb, 0);
                            f.setOutfitImg(imageBase64);
                        }
                        repository.addOutfitItem(f);
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
            }


        }
    };

    ActivityResultLauncher<Intent> mLoadPictureForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        UseLog.i("Activity.RESULT_OK");
                        Uri uri = result.getData().getData();
                        mImageView.setImageURI(uri);
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
                        mImageView.setImageBitmap((Bitmap) bundle.get("data"));
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
}
