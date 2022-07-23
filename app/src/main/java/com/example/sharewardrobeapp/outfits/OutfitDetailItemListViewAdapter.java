package com.example.sharewardrobeapp.outfits;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sharewardrobeapp.FashionItemsActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.objects.FashionItem;

import java.util.ArrayList;
import java.util.List;

public class OutfitDetailItemListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<FashionItem> mList;

    public OutfitDetailItemListViewAdapter(Context c, ArrayList<FashionItem> list) {
        mContext = c;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.outfit_detail_fashionitem_layout, parent, false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.of_detail_listview_item_iv);
        CheckedTextView titleTextView = convertView.findViewById(R.id.of_detail_listview_item_tv);

        iconImageView.setImageBitmap(mList.get(position).getItemImgBitmap());
        titleTextView.setText(mList.get(position).getItemName());

        return convertView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public FashionItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}