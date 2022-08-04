package com.example.sharewardrobeapp.userplanner;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;

import java.util.ArrayList;

public class PlannerItemSelectAdapter extends RecyclerView.Adapter<PlannerItemSelectAdapter.ViewHolder> {

    private ArrayList<String> ids;
    private ArrayList<String> descriptions;
    private ArrayList<Bitmap> bitmaps;
    private PlannerItemSelectAdapter.OnItemClick mItemClickListener;

    public PlannerItemSelectAdapter(ArrayList<String> ids, ArrayList<String> descriptions, ArrayList<Bitmap> bitmaps) {
        this.ids = ids;
        this.descriptions = descriptions;
        this.bitmaps = bitmaps;
    }

    public void setOnItemClickListener(PlannerItemSelectAdapter.OnItemClick itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.planner_select_item_cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemImage.setImageBitmap(bitmaps.get(position));
        holder.itemText.setText(descriptions.get(position));
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.select_item_image);
            itemText = itemView.findViewById(R.id.select_item_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mItemClickListener!= null) {
                        mItemClickListener.onClickItem(ids.get(position));
                    }
                }
            });
        }
    }




    public interface OnItemClick {
        void onClickItem(String id);
    }
}
