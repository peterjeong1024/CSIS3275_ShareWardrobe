package com.example.sharewardrobeapp.userplanner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.objects.FashionItem;

import java.util.ArrayList;
import java.util.Map;

public class PlannerDetailFashionItemsRecyclerAdapter extends RecyclerView.Adapter<PlannerDetailFashionItemsRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private PlannerDetailFashionItemsRecyclerAdapter.OnItemClick mItemClickListener;
    private ArrayList<String> fashionItemIds;
    private Map<String, FashionItem> fashionItems;
    private boolean isEditable;

    public PlannerDetailFashionItemsRecyclerAdapter(ArrayList<String> fashionItemIds, Map<String, FashionItem> fashionItems, boolean isEditable) {
        this.fashionItemIds = fashionItemIds;
        this.fashionItems = fashionItems;
        this.isEditable = isEditable;
    }
    public PlannerDetailFashionItemsRecyclerAdapter(ArrayList<String> fashionItemIds, Map<String, FashionItem> fashionItems) {
        this(fashionItemIds, fashionItems, false);
    }

    public void setOnItemClickListener(PlannerDetailFashionItemsRecyclerAdapter.OnItemClick itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void notifyUpdateEditable(boolean isEditable) {
        this.isEditable = isEditable;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.planner_detail_fashionitem_cardview_layout, parent, false);
        PlannerDetailFashionItemsRecyclerAdapter.ViewHolder viewHolder = new PlannerDetailFashionItemsRecyclerAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FashionItem fashionItem = fashionItems.get(fashionItemIds.get(position));
        if (fashionItem != null) {
            holder.fashionItemImage.setImageBitmap(fashionItem.getItemImgBitmap());
            holder.fashionItemText.setText(fashionItem.getItemDesc());
            holder.fashionItemRemoveButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return ((fashionItemIds == null) ? 0 : fashionItemIds.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fashionItemImage;
        private TextView fashionItemText;
        private Button fashionItemRemoveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fashionItemImage = itemView.findViewById(R.id.plan_fashion_item_image);
            fashionItemText = itemView.findViewById(R.id.plan_fashion_item_text);
            fashionItemRemoveButton = itemView.findViewById(R.id.fashion_item_card_remove_button);
            fashionItemRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    fashionItemIds.remove(position);
                    PlannerDetailFashionItemsRecyclerAdapter.this.notifyItemRemoved(position);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mItemClickListener!= null) {
                        mItemClickListener.onClickFashionItem(position);
                    }
                }
            });
        }
    }




    public interface OnItemClick {
        void onClickFashionItem(int position);
    }
}
