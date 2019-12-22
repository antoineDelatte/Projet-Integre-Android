package com.example.packvoyage.adapterRecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.packvoyage.R;
import com.example.packvoyage.model.ActivityTag;

import java.util.ArrayList;
import java.util.Map;

public class ActivityTagsAdapter extends RecyclerView.Adapter<ActivityTagsAdapter.ActivityTagHolder> {

    private ArrayList<ActivityTag> activityTags;
    private OnActivityTagCheckboxClick listener;
    private Map<String, ?> savedActivityTags;
    private ArrayList<CheckBox>checkBoxes = new ArrayList<>();
    private boolean maxSavedTagsAtStart;

    public ActivityTagsAdapter(ArrayList<ActivityTag> activityTags, OnActivityTagCheckboxClick listener, Map<String, ?> savedActivityTags, boolean maxSavedTagsAtStart) {
        this.activityTags = activityTags;
        this.listener = listener;
        this.savedActivityTags = savedActivityTags;
        this.maxSavedTagsAtStart = maxSavedTagsAtStart;
    }

    public void disableUncheckedCheckboxes(){
        Log.i("Trip4", Integer.toString(checkBoxes.size()));
        for(CheckBox checkBox : checkBoxes){
            if(!checkBox.isChecked()){
                checkBox.setEnabled(false);
                Log.i("Trip4", "disabling");
            }
        }
    }

    public void reactivateCheckboxes(){
        for(CheckBox checkBox : checkBoxes){
            if(!checkBox.isEnabled())
                checkBox.setEnabled(true);
        }
    }

    @NonNull
    @Override
    public ActivityTagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_tag_recyclerview, parent, false);
        ActivityTagHolder holder = new ActivityTagHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityTagHolder holder, int position) {
        ActivityTag tag = activityTags.get(position);
        holder.activity_type_name.setText(tag.getName());
        if(savedActivityTags.containsKey(tag.getName()))
            holder.activity_tag_checkbox.setChecked(true);
        checkBoxes.add(holder.activity_tag_checkbox);
        if(maxSavedTagsAtStart){
            if(!savedActivityTags.containsKey(tag.getName()))
                holder.activity_tag_checkbox.setEnabled(false);
        }
        holder.activity_tag_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onActivityTagCheckboxClick(tag.getId(), tag.getName(), holder.activity_tag_checkbox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityTags == null ? 0 : activityTags.size();
    }

    public interface OnActivityTagCheckboxClick{
        void onActivityTagCheckboxClick(int tagId, String tagName, boolean isSelected);
    }

    public static class ActivityTagHolder extends RecyclerView.ViewHolder{

        private TextView activity_type_name;
        private CheckBox activity_tag_checkbox;

        public ActivityTagHolder(@NonNull View itemView) {
            super(itemView);
            activity_type_name = itemView.findViewById(R.id.activity_type_name);
            activity_tag_checkbox = itemView.findViewById(R.id.activity_tag_checkbox);
        }
    }
}
