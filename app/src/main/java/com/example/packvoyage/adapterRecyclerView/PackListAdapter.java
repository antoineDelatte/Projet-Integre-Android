package com.example.packvoyage.adapterRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.packvoyage.R;
import com.example.packvoyage.model.Pack;

import java.util.ArrayList;

public class PackListAdapter extends RecyclerView.Adapter<PackListAdapter.PackHolder> {

    public ArrayList<Pack> packList;
    private Context context;
    private OnPackListener onPackListener;

    public PackListAdapter(ArrayList<Pack> packs, Context context, OnPackListener onPackListener){
        this.packList = packs;
        this.context = context;
        this.onPackListener = onPackListener;
    }

    public PackListAdapter.PackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_pack_recyclerview, parent, false);
        PackListAdapter.PackHolder holder = new PackListAdapter.PackHolder(v, onPackListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(PackListAdapter.PackHolder holder, int position) {
        holder.bind(packList.get(position).getId(), packList.get(position).getName(), onPackListener);
        Glide.with(this.context).load(this.packList.get(position).getImage_url()).into(holder.packPicture);
        holder.packName.setText(packList.get(position).getName());
        holder.packPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPackListener.onPackClick(packList.get(position).getId(), packList.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.packList == null ? 0 : packList.size();
    }

    public interface OnPackListener {
        void onPackClick(int packId, String packName);
    }

    public static class PackHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnPackListener onPackListener;
        private ImageButton packPicture;
        private TextView packName;
        private String packTitle;
        private int packId;


        public PackHolder(View itemView, OnPackListener onPackListener) {
            super(itemView);
            packPicture = itemView.findViewById(R.id.pack_image);
            packName = itemView.findViewById(R.id.pack_name);
            this.onPackListener = onPackListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Integer id, String name, OnPackListener listener){
            packTitle = name;
            packId = id;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPackClick(id, name);
                }
            });
        }

        @Override
        public void onClick(View v) {
            onPackListener.onPackClick(packId, packTitle);
        }
    }
}