package com.ka8eem.testtmdb.interfaces;

import com.ka8eem.testtmdb.utils.MyApiKey;
import com.ka8eem.testtmdb.models.PageModel;
import com.ka8eem.testtmdb.models.ReviewModel;
import com.ka8eem.testtmdb.models.VideoModelJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieInterface {

    //https://api.themoviedb.org/3/movie/
    //https://api.themoviedb.org/3/discover/movie?api_key=
    //String BASE_URL = "https://api.themoviedb.org/3/";

    String token = "discover/movie?api_key=" + MyApiKey.API_KEY;

    @GET(token)
    Call<PageModel> getMovies();

    String popular = token + "&sort_by=popularity.desc";

    @GET(popular)
    Call<PageModel> getPopular();

    String top_rated = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + MyApiKey.API_KEY;

    @GET(top_rated)
    Call<PageModel> getTopRated();

    String reviews = "movie/{id}/reviews?api_key=" + MyApiKey.API_KEY;

    @GET(reviews)
    Call<ReviewModel> getMovieReview(@Path("id") String id);

    String videos = "movie/{id}/videos?api_key=" + MyApiKey.API_KEY;

    @GET(videos)
    Call<VideoModelJSON> getMovieVideos(@Path("id") String id);

}
