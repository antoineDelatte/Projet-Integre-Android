package com.example.packvoyage.adapterRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.packvoyage.R;
import com.example.packvoyage.model.Pack;

import java.util.ArrayList;

public class PackListAdapter extends RecyclerView.Adapter<PackListAdapter.PackHolder> {

    private ArrayList<Pack> packList;
    private Context context;
    private final OnItemClickListener listener;

    public static class PackHolder extends RecyclerView.ViewHolder {

        private ImageButton packPicture;
        private TextView packName;


        public PackHolder(View itemView) {
            super(itemView);
            packPicture = itemView.findViewById(R.id.pack_image);
            packName = itemView.findViewById(R.id.pack_display_rv);
        }

        public void bind(Integer id, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onClick(id);
                }
            });
        }
    }

    public PackListAdapter(ArrayList<Pack> packs, Context context, PackListAdapter.OnItemClickListener listener){
        this.packList = packs;
        this.context = context;
        this.listener = listener;
    }

    public PackListAdapter.PackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_pack_recyclerview, parent, false);
        v.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "coucou", Toast.LENGTH_SHORT).show();
                //todo
            }
        });
        PackListAdapter.PackHolder holder = new PackListAdapter.PackHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PackListAdapter.PackHolder holder, int position) {
        holder.bind(packList.get(position).getId(), listener);
        Glide.with(this.context).load(this.packList.get(position).getImage_url()).into(holder.packPicture);
        holder.packName.setText(packList.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return this.packList == null ? 0 : packList.size();
    }

    public interface OnItemClickListener {
        void onClick(int offerId);
    }
}