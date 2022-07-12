package com.example.sharewardrobeapp.fashionitems;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.objects.FashionItem;

import java.util.ArrayList;

public class FashionItemsRecyclerAdapter extends RecyclerView.Adapter<FashionItemsRecyclerAdapter.ViewHolder> {

    private OnItemClick mItemClickListener;
    private ArrayList<FashionItem> mFashionItemList;

    public FashionItemsRecyclerAdapter(ArrayList<FashionItem> fashionItemList) {
        mFashionItemList = fashionItemList;
    }

    public void setOnItemClickListener(OnItemClick itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fashionitem_cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTitle.setText(mFashionItemList.get(position).getItemName());
        holder.itemImage.setImageBitmap(mFashionItemList.get(position).getItemImgBitmap());
    }

    @Override
    public int getItemCount() {
        return mFashionItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.fashionitem_img);
            itemTitle = itemView.findViewById(R.id.fashionitem_text);
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
