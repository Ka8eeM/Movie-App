package com.ka8eem.testtmdb.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ka8eem.testtmdb.models.MovieModel;
import com.ka8eem.testtmdb.models.PageModel;
import com.ka8eem.testtmdb.models.ReviewModel;
import com.ka8eem.testtmdb.models.ReviewModelDetails;
import com.ka8eem.testtmdb.models.VideoModel;
import com.ka8eem.testtmdb.models.VideoModelJSON;
import com.ka8eem.testtmdb.repository.MovieClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {

    public MutableLiveData<ArrayList<MovieModel>> movieModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<ReviewModelDetails>> reviewModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<VideoModel>> listMutableLiveData = new MutableLiveData<ArrayList<com.ka8eem.testtmdb.models.VideoModel>>();
    MutableLiveData<String> data = new MutableLiveData<>();

    public void getMovies() {
        MovieClient.getINSTANCE().getMovies().enqueue(new Callback<PageModel>() {
            @Override
            public void onResponse(Call<PageModel> call, Response<PageModel> response) {
                movieModelMutableLiveData.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<PageModel> call, Throwable t) {
                data.postValue("Error while fetching movies");
            }
        });
    }

    public void getPopular() {
        MovieClient.getINSTANCE().getPopular().enqueue(new Callback<PageModel>() {
            @Override
            public void onResponse(Call<PageModel> call, Response<PageModel> response) {
                movieModelMutableLiveData.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<PageModel> call, Throwable t) {

            }
        });
    }

    public void getTopRated() {
        MovieClient.getINSTANCE().getTopRated().enqueue(new Callback<PageModel>() {
            @Override
            public void onResponse(Call<PageModel> call, Response<PageModel> response) {
                movieModelMutableLiveData.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<PageModel> call, Throwable t) {

            }
        });
    }

    public void getMovieReview(String id) {
        MovieClient.getINSTANCE().getMovieReview(id).enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                reviewModelMutableLiveData.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {

            }
        });
    }
    public void getMovieVideos(String id)
    {
        MovieClient.getINSTANCE().getMovieVideos(id).enqueue(new Callback<VideoModelJSON>() {
            @Override
            public void onResponse(Call<VideoModelJSON> call, Response<VideoModelJSON> response) {
                listMutableLiveData.postValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<VideoModelJSON> call, Throwable t) {

            }
        });
    }
}
