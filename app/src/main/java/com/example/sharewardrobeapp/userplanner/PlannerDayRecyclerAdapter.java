package com.example.sharewardrobeapp.userplanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.objects.UserPlanData;

import java.util.ArrayList;
import java.util.Map;

public class PlannerDayRecyclerAdapter extends RecyclerView.Adapter<PlannerDayRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private PlannerDayRecyclerAdapter.OnItemClick mItemClickListener;
    ArrayList<UserPlanData> dayPlanItems;
    Map<String, Bitmap> dayBitmaps;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.planner_day_cardview_layout, parent, false);
        PlannerDayRecyclerAdapter.ViewHolder viewHolder = new PlannerDayRecyclerAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserPlanData plan = dayPlanItems.get(position);
        Bitmap planBitmap = dayBitmaps.get(plan.get_id());

        int outfitCount = plan.getOutFitsSerialize().split("\\|").length;
        int fashionItemCount = plan.getFItemsSerialize().split("\\|").length;
        StringBuilder planContent = new StringBuilder();
        String conjunction = "";
        if (outfitCount > 0) {
            planContent.append(outfitCount + " outfit" + ((outfitCount > 1) ? "s" : ""));
            conjunction = " and ";
        }
        if (fashionItemCount > 0) {
            planContent.append(conjunction + fashionItemCount + " item" + ((fashionItemCount > 1) ? "s" : ""));
        }

        if (planBitmap == null) {
            holder.planImage.setImageResource(R.drawable.ic_empty_image);
        }
        else {
            holder.planImage.setImageBitmap(planBitmap);
        }
        holder.planDescriptionText.setText(plan.getItemDescription());
        holder.planContentText.setText(planContent);
    }

    @Override
    public int getItemCount() {
        return dayPlanItems.size();
    }

    public PlannerDayRecyclerAdapter(ArrayList<UserPlanData> dayPlanItems, Map<String, Bitmap> dayBitmaps) {
        this.dayPlanItems = dayPlanItems;
        this.dayBitmaps = dayBitmaps;
    }

    public void setOnItemClickListener(PlannerDayRecyclerAdapter.OnItemClick itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView planImage;
        private TextView planDescriptionText;
        private TextView planContentText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            planImage = itemView.findViewById(R.id.plan_image);
            planDescriptionText = itemView.findViewById(R.id.plan_description_text);
            planContentText = itemView.findViewById(R.id.plan_content_text);
        }
    }




    public interface OnItemClick {
        void onClickItem(int position);
    }
}
