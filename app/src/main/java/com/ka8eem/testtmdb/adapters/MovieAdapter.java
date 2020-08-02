package com.ka8eem.testtmdb.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ka8eem.testtmdb.R;
import com.ka8eem.testtmdb.ui.activities.MovieDetails;
import com.ka8eem.testtmdb.models.MovieModel;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context context;
    ArrayList<MovieModel> list;

    public MovieAdapter() {
    }

    public void setList(ArrayList<MovieModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_list, parent, false);
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
