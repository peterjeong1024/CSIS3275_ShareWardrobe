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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class PlannerMonthRecyclerAdapter extends RecyclerView.Adapter<PlannerMonthRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private PlannerMonthRecyclerAdapter.OnItemClick mItemClickListener;
    private Calendar date0;
    private int offset;
    private int total;

    Map<String, String> dayBitmapKey;
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

        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String bitmapKey = formater.format(currDate.getTimeInMillis());

        boolean isSelectedMonth = ((12 + currDate.get(Calendar.MONTH) - date0.get(Calendar.MONTH)) % 12 == (offset == 0 ? 0 : 1));
        boolean isToday = (formater.format(Calendar.getInstance().getTimeInMillis()).compareTo(bitmapKey) == 0);
        boolean shouldHaveImage = dayBitmapKey.containsKey(bitmapKey);

        // set day
        holder.dayText.setText(Integer.toString(currDate.get(Calendar.DATE)));

        // set background light gray if not current month
        // set background light cyan for today
        holder.dayCard.setCardElevation(isSelectedMonth ? 5 : 0);
        holder.dayCard.setCardBackgroundColor(isSelectedMonth ? (isToday ? Color.rgb(225, 255, 255) : Color.rgb(255, 255, 225)) : Color.LTGRAY);
        holder.dayCard.setRadius(isSelectedMonth ? 12 : 0);

        // set day image if bitmap available
        Bitmap bitmap = null;
        if (shouldHaveImage) {
            bitmap = dayBitmap.get(dayBitmapKey.get(bitmapKey));
        }
        if (shouldHaveImage && bitmap == null) {
            holder.dayImage.setImageResource(R.drawable.ic_empty_image);
        } else {
            holder.dayImage.setImageResource(0);
            holder.dayImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return total;
    }

    public PlannerMonthRecyclerAdapter(Map<String, String> dayBitmapKey, Map<String, Bitmap> dayBitmap) {
        this.dayBitmapKey = dayBitmapKey;
        this.dayBitmap = dayBitmap;

        // 1st day of month
        date0 = Calendar.getInstance();
        // get current year and month index
        int yyyy = date0.get(Calendar.YEAR);
        int mmIdx = date0.get(Calendar.MONTH);
        // clear date0 content to make its time always 0:00:00.000
        date0.setTimeInMillis(0);

        updateDate(yyyy, mmIdx + 1);
    }

    public int getOffset() {
        return offset;
    }

    public void setOnItemClickListener(PlannerMonthRecyclerAdapter.OnItemClick itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void updateDate(int yyyy, int mm) {
        date0.set(yyyy, mm - 1, 1);
        // if already Sunday, -0 day. if Monday, -1 day, etc.
        offset = date0.get(Calendar.DAY_OF_WEEK) - 1;
        Calendar dateLast = (Calendar) date0.clone();
        dateLast.add(Calendar.MONTH, 1);
        dateLast.add(Calendar.DATE, -1);
        total = 7 * (int)Math.ceil(((double)offset + dateLast.get(Calendar.DATE))/7);
        date0.add(Calendar.DAY_OF_MONTH, - offset);
    }
    public void notifyMonthChange(int yyyy, int mm) {
        updateDate(yyyy, mm);
        notifyDataSetChanged();
    }
    public void notifyImageChange(String bitmapKey) {
        for (Map.Entry<String, String> entry : dayBitmapKey.entrySet()) {
            if (entry.getValue().compareTo(bitmapKey) == 0) {
                String dayKey = entry.getKey();
                Calendar date1 = (Calendar) date0.clone();
                date1.add(Calendar.DATE, offset);
                if (dayKey.substring(0, 6).compareTo((new SimpleDateFormat("yyyyMM")).format(date1.getTimeInMillis())) == 0) {
                    int position = Integer.parseInt(dayKey.substring(6)) + offset - 1;
                    notifyItemChanged(position);
                }
            }
        }
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
