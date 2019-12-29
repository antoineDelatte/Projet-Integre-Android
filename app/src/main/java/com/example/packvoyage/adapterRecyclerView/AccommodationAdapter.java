package com.example.packvoyage.adapterRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.packvoyage.R;
import com.example.packvoyage.model.Accommodation;

import java.util.ArrayList;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.AccommodationHolder> {

    private ArrayList<Accommodation>accommodations;
    private Context context;

    public AccommodationAdapter(Context context, ArrayList<Accommodation>accommodations){
        this.accommodations = accommodations;
        this.context = context;
    }

    @Override
    public AccommodationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.accommodation_recyclerview, parent, false);
        AccommodationHolder holder = new AccommodationHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationHolder holder, int position) {
        Accommodation accommodation = accommodations.get(position);
        holder.accommodation_name.setText(accommodation.getName());
        String locality = holder.itemView.getContext().getResources().getString(R.string.locality);
        holder.accommodation_description.setText(locality + " : " + accommodation.getLocality().toString());
        Glide.with(this.context).load(accommodation.getImage_uri()).into(holder.accommodation_image);
    }

    @Override
    public int getItemCount() {
        return accommodations == null ? 0 : accommodations.size();
    }

    public static class AccommodationHolder extends RecyclerView.ViewHolder{

        private ImageView accommodation_image;
        private TextView accommodation_name;
        private TextView accommodation_description;

        public AccommodationHolder(@NonNull View itemView) {
            super(itemView);
            accommodation_image = itemView.findViewById(R.id.accommodation_image);
            accommodation_name = itemView.findViewById(R.id.accommodation_name);
            accommodation_description = itemView.findViewById(R.id.accommodation_description);
        }
    }
}
