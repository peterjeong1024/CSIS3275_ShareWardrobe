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
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.sharewardrobeapp.FashionItemsActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.interfaces.GlideApp;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;
import java.util.List;

public class OutfitDetailItemListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<FashionItem> mList;

    public OutfitDetailItemListViewAdapter(Context c, ArrayList<FashionItem> list) {
        mContext = c;
        mList = list;
    }

    public void reDrawList(ArrayList<FashionItem> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.outfit_detail_fashionitem_layout, parent, false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.of_detail_listview_item_iv);
        TextView titleTextView = convertView.findViewById(R.id.of_detail_listview_item_tv);
        CheckBox itemCheckBox = convertView.findViewById(R.id.of_detail_listview_item_cb);

        GlideApp.with(mContext).load(mList.get(position).getItemImgBitmap()).into(iconImageView);
        titleTextView.setText(mList.get(position).getItemName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCheckBox.setChecked(!itemCheckBox.isChecked());
//                mOnClickItemInListInterface.OnClickItem(position);
            }
        });
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


    public ArrayList<FashionItem> getCheckedList() {
        ArrayList<FashionItem> checkedList = new ArrayList<>();

        return checkedList;
    }
}