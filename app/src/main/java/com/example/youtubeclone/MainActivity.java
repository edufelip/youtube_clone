package com.example.youtubeclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener;
    private YouTubePlayer.PlaybackEventListener playbackEventListener;
    private static final String GOOGLE_API_KEY = "AIzaSyCoR8SYAl7AxzYjV-f1FLmdG_g3xs5xdpo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        youTubePlayerView.initialize(GOOGLE_API_KEY, this);
        youTubePlayerView = findViewById(R.id.viewYoutubePlayer);
        playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoEnded() {

            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        };
        playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {

            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onBuffering(boolean b) {

            }

            @Override
            public void onSeekTo(int i) {

            }
        };
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        //youTubePlayer.loadVideo("GwaRztMaoY0");
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        if(!wasRestored) {
            youTubePlayer.cueVideo("GwaRztMaoY0");
        }
        Toast.makeText(this, "Iniciado com sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Erro ao iniciar" + youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
    }
}
