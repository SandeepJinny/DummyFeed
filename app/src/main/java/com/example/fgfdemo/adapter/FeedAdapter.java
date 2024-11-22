package com.example.fgfdemo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fgfdemo.R;
import com.example.fgfdemo.model.Post;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private final List<Post> posts;

    public FeedAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_post, parent, false);
            return new PostViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {
        Log.e("LOG_E", "showLoadingView: " );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PostViewHolder) {
            bindPostViewHolder((PostViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }

    }

    private void updateLikeButton(PostViewHolder holder, Post post) {
        holder.likeButton.setText(post.isLiked() ? "Unlike" : "Like");
    }

    private void updateComments(PostViewHolder holder, Post post) {
        StringBuilder commentsText = new StringBuilder();
        for (String comment : post.getComments()) {
            commentsText.append(comment).append("\n");
        }
        holder.commentsTextView.setText(commentsText.toString());
    }

    @Override
    public int getItemViewType(int position) {
        return posts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void bindPostViewHolder(PostViewHolder postViewHolder, int position) {
        Post post = posts.get(position);
        postViewHolder.userName.setText(post.getUserName());
        postViewHolder.postContent.setText(post.getContent());
        Glide.with(postViewHolder.itemView.getContext()).load(post.getUserProfileImageUrl()).into(postViewHolder.userProfileImage);
        Glide.with(postViewHolder.itemView.getContext()).load(post.getPostImageUrl()).into(postViewHolder.postImage);

        // Set up click listeners for like and comment buttons
        updateLikeButton(postViewHolder, post);

        // Set up click listeners
        postViewHolder.likeButton.setOnClickListener(v -> {
            post.toggleLike();
            updateLikeButton(postViewHolder, post);
        });

        postViewHolder.commentButton.setOnClickListener(v -> {
            // Toggle comment section visibility
            int visibility = postViewHolder.commentSection.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            postViewHolder.commentSection.setVisibility(visibility);
            updateComments(postViewHolder, post);
        });

        postViewHolder.postCommentButton.setOnClickListener(v -> {
            String commentText = postViewHolder.commentEditText.getText().toString().trim();
            if (!commentText.isEmpty()) {
                post.addComment(commentText);
                postViewHolder.commentEditText.setText("");
                updateComments(postViewHolder, post);
            }
        });
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView userProfileImage;
        TextView userName;
        TextView postContent;
        ImageView postImage;
        TextView likeButton;
        TextView commentButton;
        LinearLayout commentSection;
        TextView commentsTextView;
        EditText commentEditText;
        Button postCommentButton;

        PostViewHolder(View view) {
            super(view);
            userProfileImage = view.findViewById(R.id.userProfileImage);
            userName = view.findViewById(R.id.userName);
            postContent = view.findViewById(R.id.postContent);
            postImage = view.findViewById(R.id.postImage);
            likeButton = view.findViewById(R.id.likeButton);
            commentButton = view.findViewById(R.id.commentButton);
            commentSection = view.findViewById(R.id.commentSection);
            commentsTextView = view.findViewById(R.id.commentsTextView);
            commentEditText = view.findViewById(R.id.commentEditText);
            postCommentButton = view.findViewById(R.id.postCommentButton);

        }
    }
}
