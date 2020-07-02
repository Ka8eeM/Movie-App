package com.example.testtmdb.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testtmdb.R;
import com.example.testtmdb.activities.MovieDetails;
import com.example.testtmdb.models.MovieModel;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context context;
    ArrayList<MovieModel> list;
    LayoutInflater inflater;

    public MovieAdapter(Context context, ArrayList<MovieModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_item_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieModel model = list.get(position);
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("MyClass", (Serializable) model);
                context.startActivity(intent);
            }
        });
        MovieModel model = list.get(position);
        String imgURL = "http://image.tmdb.org/t/p/w185/";
        imgURL += model.getPoster_path();
        String title = model.getTitle();
        holder.txtMovieName.setText(title);
        //resize(6000, 2000).onlyScaleDown()
        Picasso.get().load(imgURL).fit().into(holder.imgMovieLogo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMovieLogo;
        TextView txtMovieName;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgMovieLogo = itemView.findViewById(R.id.movie_img_view);
            txtMovieName = itemView.findViewById(R.id.movie_txt_view);
        }
    }
}
