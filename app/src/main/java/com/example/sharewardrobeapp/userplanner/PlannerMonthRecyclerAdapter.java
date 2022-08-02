package com.example.sharewardrobeapp.userplanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;

import java.util.Calendar;
import java.util.Map;

public class PlannerMonthRecyclerAdapter extends RecyclerView.Adapter<PlannerMonthRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private PlannerMonthRecyclerAdapter.OnItemClick mItemClickListener;
    private Calendar date0;
    private int offset;
    private int total;
    Map<String, Bitmap> dayBitmap;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.planner_month_cardview_layout, parent, false);
        PlannerMonthRecyclerAdapter.ViewHolder viewHolder = new PlannerMonthRecyclerAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // week number: Math.floor(position / 7) + 1
        // weekday: position % 7
        //       *: 0 - Sunday, 1 - Monday, etc
        Calendar currDate = (Calendar) date0.clone();
        currDate.add(Calendar.DATE, position);

        // set day
        holder.dayText.setText(Integer.toString(currDate.get(Calendar.DATE)));

        // set day image if bitmap available
        String bitmapKey = String.format("%04d%02d%02d", currDate.get(Calendar.YEAR), currDate.get(Calendar.MONTH), currDate.get(Calendar.DATE));
        Bitmap bitmap = dayBitmap.get(bitmapKey);

        holder.dayImage.setImageBitmap(bitmap);

        // set background light gray if not current month
        if ((12 + currDate.get(Calendar.MONTH) - date0.get(Calendar.MONTH)) % 12 != (offset == 0 ? 0 : 1)) {
            holder.dayText.getRootView().setBackgroundColor(Color.LTGRAY);
            holder.dayCard.setCardElevation(0);
        }

        // set background light green for today
        Calendar today = Calendar.getInstance();
        long msDiff = today.getTimeInMillis() - currDate.getTimeInMillis();
        if (msDiff > 0 && msDiff < (long)1*24*60*60*1000) {
            holder.dayCard.setCardBackgroundColor(Color.rgb(225, 255, 255));
        }
    }

    @Override
    public int getItemCount() {
        return total;
    }

    public PlannerMonthRecyclerAdapter(String yyyy, String mm, Map<String, Bitmap> dayBitmap) {
        // 1st day of month
        date0 = Calendar.getInstance();
        date0.set(Integer.parseInt(yyyy), Integer.parseInt(mm), 1);
        // if already Sunday, -0 day. if Monday, -1 day, etc.
        offset = date0.get(Calendar.DAY_OF_WEEK) - 1;
        Calendar dateLast = (Calendar) date0.clone();
        dateLast.add(Calendar.MONTH, 1);
        dateLast.add(Calendar.DATE, -1);
        total = 7 * (int)Math.ceil(((double)offset + dateLast.get(Calendar.DATE))/7);
        date0.add(Calendar.DAY_OF_MONTH, - offset);
        this.dayBitmap = dayBitmap;
    }

    public int getOffset() {
        return offset;
    }

    public void setOnItemClickListener(PlannerMonthRecyclerAdapter.OnItemClick itemClickListener) {
        mItemClickListener = itemClickListener;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dayText;
        private ImageView dayImage;
        private CardView dayCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.day_text);
            dayImage = itemView.findViewById(R.id.day_image);
            dayCard = itemView.findViewById(R.id.day_card);

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
