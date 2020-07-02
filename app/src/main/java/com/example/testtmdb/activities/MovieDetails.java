package com.example.testtmdb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testtmdb.interfaces.Api;
import com.example.testtmdb.R;
import com.example.testtmdb.adapters.VideoListAdapter;
import com.example.testtmdb.models.MovieModel;
import com.example.testtmdb.models.ReviewModel;
import com.example.testtmdb.models.ReviewModelDetails;
import com.example.testtmdb.models.VideoModel;
import com.example.testtmdb.models.VideoModelJSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {

    // finals

    // view
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private MovieModel movie;
    private ImageView imgMovie_details;
    private TextView txtMovieName;
    private TextView txtMovieRate;
    private TextView txtMovieRelease;
    private TextView txtMovieVote;
    private TextView txtMovieDesc;
    private ListView listViewReviews;
    private RecyclerView recyclerViewVideo;


    // var
    private ArrayList<ReviewModelDetails> reviewModelList;
    private String _id;
    private static ArrayList<String> reviewsList;
    private static ArrayAdapter adapter;
    private static ArrayList<VideoModel> retVideosList;
    private static ArrayList<MovieModel> favouriteMoviesIdList;
    private static int ICON_ADD_TO_FAV_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setTitle(R.string.movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        movie = (MovieModel) intent.getSerializableExtra("MyClass");
        _id = movie.getId();
        if (movie != null) {
            if (movie.getTitle() != null && !movie.getTitle().equals(""))
                getSupportActionBar().setTitle(movie.getTitle());
        }
        initViews();
        getMovieReview();
        getMovieVideos();
        if (savedInstanceState != null) {
            ICON_ADD_TO_FAV_ID = savedInstanceState.getInt("found", -1);
        } else {
            if (isFavMovie() >= 0)
                ICON_ADD_TO_FAV_ID = R.drawable.ic_favorite_added;
            else
                ICON_ADD_TO_FAV_ID = R.drawable.ic_favorite_border;
        }
    }

    private void initViews() {
        imgMovie_details = findViewById(R.id.img_movie_details);
        txtMovieName = findViewById(R.id.txt_movie_name_details);
        txtMovieRate = findViewById(R.id.txt_movie_rate);
        txtMovieRelease = findViewById(R.id.txt_movie_release_date);
        txtMovieVote = findViewById(R.id.txt_movie_vote);
        txtMovieDesc = findViewById(R.id.txt_movie_desc_details);
        listViewReviews = findViewById(R.id.list_view_reviews);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVideo = findViewById(R.id.recycler_view_trailers);
        recyclerViewVideo.setLayoutManager(layoutManager);
        reviewsList = new ArrayList<>();
        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("FAV_LIST", null);
        Type type = new TypeToken<ArrayList<MovieModel>>() {
        }.getType();
        favouriteMoviesIdList = gson.fromJson(json, type);
        if (favouriteMoviesIdList == null)
            favouriteMoviesIdList = new ArrayList<>();
        if (movie != null) {
            String imgURL = "http://image.tmdb.org/t/p/w185/";
            imgURL += movie.getPoster_path();
            Picasso.get().load(imgURL).resize(500, 1000).into(imgMovie_details);
            txtMovieName.setText(movie.getTitle());
            txtMovieDesc.setText(movie.getOverview());
            txtMovieVote.setText("Votes: " + movie.getVote_count());
            txtMovieRelease.setText("Released: " + movie.getRelease_date());
            txtMovieRate.setText(movie.getVote_average() + "/10");
        }
    }

    private void getMovieVideos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<VideoModelJSON> call = api.getMovieVideos(_id);
        call.enqueue(new Callback<VideoModelJSON>() {
            @Override
            public void onResponse(Call<VideoModelJSON> call, Response<VideoModelJSON> response) {
                VideoModelJSON ret = response.body();
                retVideosList = ret.getResults();
                VideoListAdapter adapter1 = new VideoListAdapter(MovieDetails.this, retVideosList);
                recyclerViewVideo.setAdapter(adapter1);
            }

            @Override
            public void onFailure(Call<VideoModelJSON> call, Throwable t) {
                Toast.makeText(MovieDetails.this, "Can not fetch Movie Details!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getMovieReview() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<ReviewModel> call = api.getMovieReview(_id);
        call.enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                ReviewModel rev = (ReviewModel) response.body();
                reviewModelList = rev.getResults();
                if (reviewModelList != null && reviewModelList.size() > 0) {
                    for (int i = 0; i < reviewModelList.size(); i++) {
                        String ans = reviewModelList.get(i).getAuthor() + ": "
                                + reviewModelList.get(i).getContent();
                        reviewsList.add(ans);
                    }
                    adapter = new ArrayAdapter(MovieDetails.this, android.R.layout.simple_list_item_1, reviewsList);
                    listViewReviews.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {
                Toast.makeText(MovieDetails.this, "Can not fetch Movie Details!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private int isFavMovie() {
        int idx = -1;
        for (int i = 0; i < favouriteMoviesIdList.size(); i++) {
            MovieModel model = favouriteMoviesIdList.get(i);
            if (model.getId().equals(movie.getId())) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    private void addFavourite(MenuItem item) {
        String toast = "";
        editor = preferences.edit();
        int idx = isFavMovie();
        if (idx >= 0) {
            toast = "Removed from Favourite!";
            favouriteMoviesIdList.remove(idx);
            ICON_ADD_TO_FAV_ID = R.drawable.ic_favorite_border;
        } else {
            toast = "Added to Favourite!";
            favouriteMoviesIdList.add(movie);
            ICON_ADD_TO_FAV_ID = R.drawable.ic_favorite_added;
        }
        item.setIcon(ICON_ADD_TO_FAV_ID);
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        String json = gson.toJson(favouriteMoviesIdList);
        editor.putString("FAV_LIST", json);
        editor.commit();
        editor.apply();
    }

    private void shareMovie() {
        String url = "https://www.youtube.com/";
        if (retVideosList != null) {
            url += "watch?v=";
            url += retVideosList.get(0).getKey();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("found", ICON_ADD_TO_FAV_ID);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_activity_menu, menu);
        menu.getItem(0).setIcon(ICON_ADD_TO_FAV_ID);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.share:
                shareMovie();
                break;
            case R.id.add_to_fav:
                addFavourite(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}