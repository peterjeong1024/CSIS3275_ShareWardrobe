package com.example.sharewardrobeapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.sharewardrobeapp.R;

public class MainViewPagerAdapter extends PagerAdapter {

    private Context mContext = null;


    public MainViewPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;

        if (mContext != null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (position) {
                case 0:
                default:
                    view = inflater.inflate(R.layout.main_tab_list_layout_1, container, false);
                    break;
                case 1:
                    view = inflater.inflate(R.layout.main_tab_list_layout_2, container, false);
                    break;
                case 2:
                    view = inflater.inflate(R.layout.main_tab_list_layout_3, container, false);
                    break;
            }
        }

        container.addView(view);

        return view;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String returnString = "";
        switch (position) {
            case 0:
            default:
                returnString = mContext.getResources().getString(R.string.tab_menu_1);
                break;
            case 1:
                returnString = mContext.getResources().getString(R.string.tab_menu_2);
                break;
            case 2:
                returnString = mContext.getResources().getString(R.string.tab_menu_3);
                break;
        }

        return returnString;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
