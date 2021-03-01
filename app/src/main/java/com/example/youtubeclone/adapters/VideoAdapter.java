package com.example.youtubeclone.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeclone.R;
import com.example.youtubeclone.models.Item;
import com.example.youtubeclone.models.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private List<Item> videos = new ArrayList<>();
    private Context context;

    public VideoAdapter(List<Item> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_adapter, parent, false);
        return new VideoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item video = videos.get(position);
        holder.title.setText(video.snippet.title);

        String url = video.snippet.thumbnails.high.url;
        Picasso.get().load(url).into(holder.thumb);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView date;
        ImageView thumb;
        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.videoTitle);
            thumb = itemView.findViewById(R.id.imageThumb);
        }
    }
}
