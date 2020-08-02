package com.ka8eem.testtmdb.repository;

import com.ka8eem.testtmdb.interfaces.MovieInterface;
import com.ka8eem.testtmdb.models.MovieModel;
import com.ka8eem.testtmdb.models.PageModel;
import com.ka8eem.testtmdb.models.ReviewModel;
import com.ka8eem.testtmdb.models.VideoModelJSON;
import com.ka8eem.testtmdb.utils.MyApiKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private MovieInterface dataInterface;
    private static MovieClient INSTANCE;

    private MovieClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataInterface = retrofit.create(MovieInterface.class);
    }

    public static MovieClient getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new MovieClient();
        return INSTANCE;
    }

    public Call<PageModel> getMovies() {
        return dataInterface.getMovies();
    }

    public Call<PageModel> getPopular() {
        return dataInterface.getPopular();
    }

    public Call<PageModel> getTopRated() {
        return dataInterface.getTopRated();
    }

    public Call<ReviewModel> getMovieReview(String id) {
        return dataInterface.getMovieReview(id);
    }

    public Call<VideoModelJSON> getMovieVideos(String id) {
        return dataInterface.getMovieVideos(id);
    }
}
