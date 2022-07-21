package com.example.sharewardrobeapp.fashionitems;

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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

import java.io.ByteArrayOutputStream;

public class FashionItemDetailActivity extends BasementActivity {

    private FashionItemsViewModel mViewModel;
    private DataRepository repository = DataRepository.getInstance();

    private ImageView mImageView;
    private Button mLoadPicture;
    private Button mTakePicture;

    private EditText mName;
    private EditText mDescription;
    private EditText mCategory;
    private EditText mColor;
    private EditText mFabric;
    private EditText mPrice;
    private EditText mSize;
    private EditText mSeason;
    private EditText mBrand;
    private EditText mLocation;
    private Button mConfirm;

    private boolean isEditMode = false;
    private FashionItem mFashionItem;
    private String selectedItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_item_detail);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getResources().getString(R.string.items_text));

        mImageView = findViewById(R.id.fi_imageview);
        mName = findViewById(R.id.fi_name);
        mDescription = findViewById(R.id.fi_desc);
        mCategory = findViewById(R.id.fi_category);
        mColor = findViewById(R.id.fi_color);
        mFabric = findViewById(R.id.fi_fabric);
        mPrice = findViewById(R.id.fi_price);
        mSize = findViewById(R.id.fi_size);
        mSeason = findViewById(R.id.fi_season);
        mBrand = findViewById(R.id.fi_brand);
        mLocation = findViewById(R.id.fi_location);

        mConfirm = findViewById(R.id.fi_confirm);
        mLoadPicture = findViewById(R.id.fi_load_picture);
        mTakePicture = findViewById(R.id.fi_take_picture);

        mConfirm.setOnClickListener(mButtonCLickListener);
        mLoadPicture.setOnClickListener(mButtonCLickListener);
        mTakePicture.setOnClickListener(mButtonCLickListener);

        // check it is Add mode or Edit mode
        if (getIntent().getStringExtra(ConstantValue.FASHION_ITEM_CLICK_ID) != null) {
            isEditMode = true;
            selectedItemID = getIntent().getStringExtra(ConstantValue.FASHION_ITEM_CLICK_ID);
            mViewModel = new ViewModelProvider(this).get(FashionItemsViewModel.class);
            mViewModel.getFashionItemData(selectedItemID).observe(this, new Observer<FashionItem>() {
                @Override
                public void onChanged(FashionItem fashionItem) {
                    mFashionItem = fashionItem;
                    drawScreenData();
                }
            });
        }
    }

    private void drawScreenData() {
        if (isEditMode) {
            mName.setText(mFashionItem.getItemName());
            mDescription.setText(mFashionItem.getItemDesc());
            mCategory.setText(mFashionItem.getItemCategory());
            mColor.setText(mFashionItem.getItemColor());
            mFabric.setText(mFashionItem.getItemFabric());
            mPrice.setText(String.valueOf(mFashionItem.getItemPrice()));
            mSize.setText(mFashionItem.getItemSize());
            mSeason.setText(mFashionItem.getItemSeason());
            mBrand.setText(mFashionItem.getItemBrand());
            mLocation.setText(mFashionItem.getItemLocation());
            if (!mFashionItem.getItemImg().equals("")) {
                mImageView.setImageBitmap(mFashionItem.getItemImgBitmap());
                mImageView.setVisibility(View.VISIBLE);
            }
        }
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
                case R.id.fi_confirm:
                    if (isEditMode) {
                        mFashionItem.setItemName(mName.getText().toString());
                        mFashionItem.setItemOwner(getUserAccount().getUserID());
                        mFashionItem.setItemDesc(mDescription.getText().toString());
                        mFashionItem.setItemCategory(mCategory.getText().toString());
                        mFashionItem.setItemColor(mColor.getText().toString());
                        mFashionItem.setItemFabric(mFabric.getText().toString());
                        mFashionItem.setItemPrice(Double.parseDouble(mPrice.getText().toString()));
                        mFashionItem.setItemSize(mSize.getText().toString());
                        mFashionItem.setItemSeason(mSeason.getText().toString());
                        mFashionItem.setItemBrand(mBrand.getText().toString());
                        mFashionItem.setItemLocation(mLocation.getText().toString());
                        if (mImageView.getVisibility() == View.VISIBLE) {
                            BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] bb = bos.toByteArray();
                            String imageBase64 = Base64.encodeToString(bb, 0);
                            mFashionItem.setItemImg(imageBase64);
                        }
                        repository.updateFashionItem(mFashionItem);
                        finish();
                    } else {
                        FashionItem f = new FashionItem();
                        if (mName.getText().toString().equals("")) {
                            f.setItemName("None");
                        } else {
                            f.setItemName(mName.getText().toString());
                        }
                        f.setItemOwner(getUserAccount().getUserID());
                        f.setItemDesc(mDescription.getText().toString());
                        f.setItemCategory(mCategory.getText().toString());
                        f.setItemColor(mColor.getText().toString());
                        f.setItemFabric(mFabric.getText().toString());
                        if (mPrice.getText().toString().equals("")) {
                            f.setItemPrice(0);
                        } else {
                            f.setItemPrice(Double.parseDouble(mPrice.getText().toString()));
                        }
                        f.setItemSize(mSize.getText().toString());
                        f.setItemSeason(mSeason.getText().toString());
                        f.setItemBrand(mBrand.getText().toString());
                        if (mLocation.getText().toString().equals("")) {
                            f.setItemLocation("None");
                        } else {
                            f.setItemLocation(mLocation.getText().toString());
                        }
                        if (mImageView.getVisibility() == View.VISIBLE) {
                            BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                            byte[] bb = bos.toByteArray();
                            String imageBase64 = Base64.encodeToString(bb, 0);
                            f.setItemImg(imageBase64);
                        }
                        repository.addFashionItem(f);
                        finish();
                    }
                    break;

                case R.id.fi_load_picture:
                    Intent loadImageIntent = new Intent(Intent.ACTION_PICK);
                    loadImageIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    mLoadPictureForResult.launch(loadImageIntent);
                    break;

                case R.id.fi_take_picture:
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
