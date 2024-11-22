package com.example.fgfdemo.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FeedViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postsLiveData = new MutableLiveData<>();
    private int currentPage = 1;
    private final int ITEMS_PER_PAGE = 5;

    public LiveData<List<Post>> getPosts() {
        if (postsLiveData.getValue() == null) {
            posts();
        }
        return postsLiveData;
    }

    private List<Post> posts() {
        List<Post> posts = new ArrayList<>();

        posts.add(new Post("Jane Smith", "Excited to announce my new book is now available! 📚", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&h=400&fit=crop"));

        posts.add(new Post("Mike Johnson", "Just adopted this cute little guy. Meet Max! 🐶", "https://images.unsplash.com/photo-1599566150163-29194dcaad36?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=600&h=400&fit=crop"));

        posts.add(new Post("Emily Brown", "Cooked a delicious pasta dish tonight. Recipe in comments! 🍝", "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=600&h=400&fit=crop"));

        posts.add(new Post("David Wilson", "Just ran my first marathon! Feeling accomplished. 🏃‍♂️🏅", "https://images.unsplash.com/photo-1568602471122-7832951cc4c5?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1552674605-db6ffd4facb5?w=600&h=400&fit=crop"));

        posts.add(new Post("Sarah Taylor", "Beautiful sunset at the beach today. Nature is amazing! 🌅", "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=150&h=150&fit=crop", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=600&h=400&fit=crop"));
return posts;

    }

}