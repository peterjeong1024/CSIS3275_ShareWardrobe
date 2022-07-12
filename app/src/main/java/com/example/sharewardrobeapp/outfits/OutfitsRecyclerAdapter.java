package com.example.sharewardrobeapp.outfits;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

public class OutfitsRecyclerAdapter extends RecyclerView.Adapter<OutfitsRecyclerAdapter.ViewHolder> {

    private OnItemClick mItemClickListener;
    private ArrayList<OutfitItem> mOutfitsItemList;

    public OutfitsRecyclerAdapter(ArrayList<OutfitItem> outfitsItemList) {
        mOutfitsItemList = outfitsItemList;
    }

    public void setOnItemClickListener(OnItemClick itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.outfits_cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(mOutfitsItemList.get(position).getOutfitCateName());
        holder.itemImage.setImageBitmap(mOutfitsItemList.get(position).getOutfitImgBitmap());
    }

    @Override
    public int getItemCount() {
        return mOutfitsItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.outfititem_img);
            itemTitle = itemView.findViewById(R.id.outfititem_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mItemClickListener!= null) {
                        mItemClickListener.onClickItem(position);
                    }
                }
            });
        }
    }

    public interface OnItemClick {
        void onClickItem(int position);
    }
}
