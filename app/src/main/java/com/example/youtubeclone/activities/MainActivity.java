package com.example.youtubeclone.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.youtubeclone.R;
import com.example.youtubeclone.adapters.VideoAdapter;
import com.example.youtubeclone.api.YoutubeService;
import com.example.youtubeclone.listener.RecyclerItemClickListener;
import com.example.youtubeclone.models.Item;
import com.example.youtubeclone.models.Result;
import com.example.youtubeclone.models.Video;
import com.example.youtubeclone.utils.RetrofitConfig;
import com.example.youtubeclone.utils.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener;
    private YouTubePlayer.PlaybackEventListener playbackEventListener;

    private RecyclerView recyclerVideos;
    private MaterialSearchView searchView;
    private List<Item> videos = new ArrayList<>();
    private Result result;
    private VideoAdapter videoAdapter;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube");
        setSupportActionBar(toolbar);
        retrofit = RetrofitConfig.getRetrofit();
        searchView = findViewById(R.id.searchView);
        recyclerVideos = findViewById(R.id.recyclerVideos);
        recoverVideos("");

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recoverVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recoverVideos("");
            }
        });
    }
    
    private void recoverVideos(String search) {
        String q = search.replaceAll(" ", "+");

        YoutubeService youtubeService = retrofit.create(YoutubeService.class);
        youtubeService.recoverVideos("snippet", "date", "20", YoutubeConfig.YOUTUBE_API_KEY, YoutubeConfig.CHANNEL_ID, q
        ).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()) {
                    result = response.body();
                    videos = result.items;
                    configureRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void configureRecyclerView() {
        videoAdapter = new VideoAdapter(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(videoAdapter);

        recyclerVideos.addOnItemTouchListener(new RecyclerItemClickListener(
                this, recyclerVideos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Item video = videos.get(position);
                String videoId = video.id.videoId;

                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                i.putExtra("videoId", videoId);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }
        ));
    }
}
