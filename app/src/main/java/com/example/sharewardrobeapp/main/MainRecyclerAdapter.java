package com.example.sharewardrobeapp.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.interfaces.GlideApp;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private OnMenuClickListener mOnMenuClickListener;

    private int[] menuTitles = {
            R.string.items_text,
            R.string.outfits_text,
            R.string.wardrobe_stats_text,
            R.string.planner_text,
            R.string.consultations_text
    };

    private int[] images = {
            R.drawable.icons_plus,
            R.drawable.icons_plus,
            R.drawable.icons_plus,
            R.drawable.icons_plus,
            R.drawable.icons_plus
    };

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        mOnMenuClickListener = onMenuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.mainmenu_cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(menuTitles[position]);
        GlideApp.with(mContext).load(images[position]).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return menuTitles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.mainmenu_item_img);
            itemTitle = itemView.findViewById(R.id.mainmenu_item_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mOnMenuClickListener!= null) {
                        mOnMenuClickListener.onClickMenuItem(position);
                    }
                }
            });
        }
    }

    public interface OnMenuClickListener {
        void onClickMenuItem(int position);
    }
}
