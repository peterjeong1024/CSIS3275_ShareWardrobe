package com.example.sharewardrobeapp.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainmenu_cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(menuTitles[position]);
        holder.itemImage.setImageResource(images[position]);
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
