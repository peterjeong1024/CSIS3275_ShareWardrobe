package com.example.sharewardrobeapp.consultations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.objects.Consultations;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ConsultationsRecyclerAdapter extends RecyclerView.Adapter<ConsultationsRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private String[] mNames;
    private int[] mImages;
    private OnItemClick pItemClickListener;

    public ConsultationsRecyclerAdapter(Context c, String[] names, int[] images) {
        mContext = c;
        mNames = names;
        mImages = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.consults_cardview_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    public void setOnItemClickListener (OnItemClick itemClickListener) {
        pItemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.consultImage.setImageResource(mImages[i]);
        viewHolder.consultName.setText(mNames[i]);
        viewHolder.consultArrows.setImageResource(R.drawable.arrow_chevron);
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView consultImage;
        TextView consultName;
        ImageView consultArrows;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            consultImage = itemView.findViewById(R.id.consultant_image);
            consultName = itemView.findViewById(R.id.consultant_name);
            consultArrows = itemView.findViewById(R.id.consultant_right_arrow);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    pItemClickListener.onClickItem(position);
                    Snackbar.make(v, "Click detected on item" + position,
                                    Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    public interface OnItemClick {
        void onClickItem(int position);
    }
}
