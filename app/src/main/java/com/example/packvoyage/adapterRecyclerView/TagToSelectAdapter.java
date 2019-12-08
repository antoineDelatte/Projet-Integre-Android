//package com.example.packvoyage.adapterRecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.packvoyage.R;
//import com.example.packvoyage.model.Tag;
//
//import java.util.List;
//
//public class TagToSelectAdapter extends RecyclerView.Adapter<TagToSelectAdapter.PackHolder> {
//
//    List<Tag> tags;
//
//    public static class PackHolder extends RecyclerView.ViewHolder{
//        private CheckBox tagButton;
//
//        public PackHolder(View itemView){
//            super(itemView);
//            tagButton = itemView.findViewById(R.id.tag_button);
//
//        }
//    }
//
//    public PackHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.tags_to_select_recyclerview, parent, false);
//        PackHolder holder = new PackHolder(v, position -> {
//            Tag tag = tags.get(position);
//        });
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(PackHolder holder, int position) {
//        holder.tagButton.setText(tags.get(position).getName());
//
//    }
//
//
//    @Override
//    public int getItemCount(){
//        return this.tags == null ? 0 : tags.size();
//    }
//
//    public void setTags(List<Tag> tags){
//        this.tags = tags;
//        notifyDataSetChanged();
//    }
//}
