package com.example.sharewardrobeapp.consultations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharewardrobeapp.R;
import com.google.android.material.snackbar.Snackbar;

public class ConsultationsRecyclerAdapter extends RecyclerView.Adapter<ConsultationsRecyclerAdapter.ViewHolder> {
    private String[] names = {"Nicole Warne", "Margareth Zhang", "Jane Aldridge"};

    private int[] images = {R.drawable.nicole_warne,
            R.drawable.margareth_zhang,
            R.drawable.jane_aldridge};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.consults_cardview_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.consultImage.setImageResource(images[i]);
        viewHolder.consultName.setText(names[i]);

    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView consultImage;
        TextView consultName;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            consultImage = itemView.findViewById(R.id.consultant_image);
            consultName = itemView.findViewById(R.id.consultant_name);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Click detected on item" + position,
                                    Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }
}
