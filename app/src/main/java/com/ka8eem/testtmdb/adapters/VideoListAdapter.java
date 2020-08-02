package com.ka8eem.testtmdb.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ka8eem.testtmdb.R;
import com.ka8eem.testtmdb.ui.activities.VideoWebViewActivity;
import com.ka8eem.testtmdb.models.VideoModel;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    ArrayList<VideoModel> videoModelList;
    Context context;

    public VideoListAdapter() {
    }

    public void setVideoModelList(ArrayList<VideoModel> videoModelList) {
        this.videoModelList = videoModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_list, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, final int position) {
        holder.videoTrailerName.setText(videoModelList.get(position).getName());
        holder.imageView.setImageResource(R.drawable.ic_img_video);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoWebViewActivity.class);
                intent.putExtra("VIDEO_KEY", videoModelList.get(position).getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        if (videoModelList != null)
            return videoModelList.size();
        return 0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView videoTrailerName;
        ImageView imageView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTrailerName = itemView.findViewById(R.id.video_item_trailer_name);
            imageView = itemView.findViewById(R.id.id_img_trailer);
        }
    }
}


