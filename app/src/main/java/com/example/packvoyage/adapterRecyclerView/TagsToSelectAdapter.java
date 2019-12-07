package com.example.packvoyage.adapterRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagsToSelectAdapter extends RecyclerView.Adapter<TagsToSelectAdapter.TagHolder> {

    List<Tag> tags;

    public static class TagHolder extends RecyclerView.ViewHolder{
        private CheckBox tagButton;

        public TagHolder(View itemView, OnItemSelectedListener listener){
            super(itemView);
            tagButton = itemView.findViewById(R.id.tag_button);
            tagButton.setOnClickListener(e -> {
                int currentPosition = getAdapterPosition();
                listener.onItemSelected(currentPosition);
            });
        }
    }

    public TagsToSelectAdapter.TagHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tags_to_select, parent, false);
        TagHolder holder = new TagHolder(v, position -> {
            Tag tag = tags.get(position);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TagsToSelectAdapter.TagHolder holder, int position) {
        holder.tagButton.setText(tags.get(position).getName());

    }


    @Override
    public int getItemCount(){
        return this.tags == null ? 0 : tags.size();
    }

    public void setTags(List<Tag> tags){
        this.tags = tags;
        notifyDataSetChanged();
    }
}
