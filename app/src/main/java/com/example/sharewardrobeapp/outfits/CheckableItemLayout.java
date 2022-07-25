package com.example.sharewardrobeapp.outfits;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.sharewardrobeapp.R;

public class CheckableItemLayout extends ConstraintLayout implements Checkable {

    private boolean mIsChecked;

    public CheckableItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mIsChecked = false;
    }

    @Override
    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.of_detail_listview_item_cb);

        return cb.isChecked();
        // return mIsChecked ;
    }

    @Override
    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.of_detail_listview_item_cb);

        setChecked(cb.isChecked() ? false : true);
        setChecked(mIsChecked ? false : true);
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = (CheckBox) findViewById(R.id.of_detail_listview_item_cb);

        if (cb.isChecked() != checked) {
            cb.setChecked(checked);
        }
    }
}
