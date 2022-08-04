package com.example.sharewardrobeapp.userplanner;

import android.content.Context;
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
import com.example.sharewardrobeapp.objects.OutfitItem;

import java.util.ArrayList;
import java.util.Map;

public class PlannerDetailOutfitsRecyclerAdapter extends RecyclerView.Adapter<PlannerDetailOutfitsRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private PlannerDetailOutfitsRecyclerAdapter.OnItemClick mItemClickListener;
    private ArrayList<String> outfitItemIds;
    private Map<String, OutfitItem> outfitItems;
    private boolean isEditable;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.planner_day_cardview_layout, parent, false);
        PlannerDetailOutfitsRecyclerAdapter.ViewHolder viewHolder = new PlannerDetailOutfitsRecyclerAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OutfitItem outfit = outfitItems.get(outfitItemIds.get(position));
        if (outfit != null) {
            int fashionItemCount = outfit.getFItemsSerialize().split("\\|").length;

            holder.outfitImage.setImageBitmap(outfit.getOutfitImgBitmap());
            holder.outfitCategoryText.setText(outfit.getOutfitCateName());
            holder.outfitContentText.setText("Composed of " + fashionItemCount + " fashion item" + ((fashionItemCount > 1) ? "s" : ""));

            holder.outfitRemoveButton.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return ((outfitItemIds == null) ? 0 : outfitItemIds.size());
    }

    public PlannerDetailOutfitsRecyclerAdapter(ArrayList<String> outfitItemIds, Map<String, OutfitItem> outfitItems, boolean isEditable) {
        this.outfitItemIds = outfitItemIds;
        this.outfitItems = outfitItems;
        this.isEditable = isEditable;
    }
    public PlannerDetailOutfitsRecyclerAdapter(ArrayList<String> outfitItemIds, Map<String, OutfitItem> outfitItems) {
        this(outfitItemIds, outfitItems, false);
    }

    public void setOnItemClickListener(PlannerDetailOutfitsRecyclerAdapter.OnItemClick itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void notifyUpdateEditable(boolean isEditable) {
        this.isEditable = isEditable;
        notifyDataSetChanged();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView outfitImage;
        private TextView outfitCategoryText;
        private TextView outfitContentText;
        private Button outfitRemoveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            outfitImage = itemView.findViewById(R.id.plan_image);
            outfitCategoryText = itemView.findViewById(R.id.plan_description_text);
            outfitContentText = itemView.findViewById(R.id.plan_content_text);
            outfitRemoveButton = itemView.findViewById(R.id.outfit_card_remove_button);
            outfitRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    outfitItemIds.remove(position);
                    PlannerDetailOutfitsRecyclerAdapter.this.notifyItemRemoved(position);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mItemClickListener!= null) {
                        mItemClickListener.onClickOutfitItem(position);
                    }
                }
            });
        }
    }




    public interface OnItemClick {
        void onClickOutfitItem(int position);
    }
}
