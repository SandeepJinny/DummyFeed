package com.example.fgfdemo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fgfdemo.R;
import com.example.fgfdemo.adapter.FeedAdapter;
import com.example.fgfdemo.model.Post;
import com.example.fgfdemo.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class SocialFeed extends AppCompatActivity {
    private RecyclerView feedRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FeedAdapter feedAdapter;
    private List<Post> posts;
    private boolean isLoading = false;
    private int currentPage = 1;
    private final int ITEMS_PER_PAGE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_feed);

        // Initialize views
        feedRecyclerView = findViewById(R.id.feedRecyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        // Set up feed
        setupFeed();
    }


    private void setupFeed() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        feedRecyclerView.setLayoutManager(layoutManager);
        posts = getDummyPosts(currentPage, ITEMS_PER_PAGE); // Replace with actual data fetching
        feedAdapter = new FeedAdapter(posts);
        feedRecyclerView.setAdapter(feedAdapter);
        // Set up swipe-to-refresh
        swipeRefreshLayout.setOnRefreshListener(this::refreshFeed);
        // Set up pagination
        feedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == posts.size() - 1) {
                    loadMorePosts();// Right now dummy static data in list
                }
            }
        });
        // Set up swipe-to-dismiss
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                posts.remove(position);
                feedAdapter.notifyItemRemoved(position);
                Toast.makeText(SocialFeed.this, "Post Removed", Toast.LENGTH_SHORT).show();
            }
        };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(feedRecyclerView);

        // Load initial posts
        getDummyPosts(currentPage, ITEMS_PER_PAGE);

    }

    private void refreshFeed() {
        currentPage = 1;
        posts.clear();
        feedAdapter.notifyDataSetChanged();
        loadMorePosts();
    }

    private void loadMorePosts() {
        if (!NetworkUtil.isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        isLoading = true;
        // Simulating network delay
        new Handler().postDelayed(() -> {
            List<Post> newPosts = getDummyPosts(currentPage, ITEMS_PER_PAGE);
            int startInsertPosition = posts.size();
            posts.addAll(newPosts);
            feedAdapter.notifyItemRangeInserted(startInsertPosition, newPosts.size());
            isLoading = false;
            swipeRefreshLayout.setRefreshing(false);
            currentPage++;
        }, 1500);
    }

    private List<Post> getDummyPosts(int page, int itemsPerPage) {
        List<Post> posts = new ArrayList<>();

        posts.add(new Post("Jane Smith", "Excited to announce my new book is now available! 📚", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&h=400&fit=crop"));

        posts.add(new Post("Mike Johnson", "Just adopted this cute little guy. Meet Max! 🐶", "https://images.unsplash.com/photo-1599566150163-29194dcaad36?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=600&h=400&fit=crop"));

        posts.add(new Post("Emily Brown", "Cooked a delicious pasta dish tonight. Recipe in comments! 🍝", "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=600&h=400&fit=crop"));

        posts.add(new Post("David Wilson", "Just ran my first marathon! Feeling accomplished. 🏃‍♂️🏅", "https://images.unsplash.com/photo-1568602471122-7832951cc4c5?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1552674605-db6ffd4facb5?w=600&h=400&fit=crop"));

        posts.add(new Post("Sarah Taylor", "Beautiful sunset at the beach today. Nature is amazing! 🌅", "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=600&h=400&fit=crop"));

        // Calculate start and end indices for the requested page
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, posts.size());

        // Return a sublist of posts for the requested page
        if (startIndex < posts.size()) {
            return posts.subList(startIndex, endIndex);
        } else {
            return new ArrayList<>(); // Return an empty list if we're past the end
        }
    }
}



