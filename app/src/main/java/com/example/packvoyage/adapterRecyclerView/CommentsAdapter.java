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
import com.bumptech.glide.request.RequestOptions;
import com.example.packvoyage.R;
import com.example.packvoyage.model.Comment;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder>{

    private ArrayList<Comment>comments;
    private Context context;
    private OnCommentClick listener;

    public CommentsAdapter(ArrayList<Comment> comments, Context context, OnCommentClick listener){
        this.comments = comments;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_recyclerview, parent, false);
        CommentHolder holder = new CommentHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment = comments.get(position);
        Glide.with(context).load(comment.getUser().getProfile_pic_uri()).apply(RequestOptions.circleCropTransform()).into(holder.userProfilePic);
        holder.userName.setText(comment.getUser().getName());
        holder.commentText.setText(comment.getMessage());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onCommentClick(comment.getUser().getId(), position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments == null ? 0 : comments.size();
    }

    public interface OnCommentClick{
        void onCommentClick(int commentOwnerId, int selectedCommentPosition);
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {

        private ImageView userProfilePic;
        private TextView userName;
        private TextView commentText;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            userProfilePic = itemView.findViewById(R.id.comment_user_profile_pic);
            userName = itemView.findViewById(R.id.comment_username);
            commentText = itemView.findViewById(R.id.comment_text);
        }
    }
}
