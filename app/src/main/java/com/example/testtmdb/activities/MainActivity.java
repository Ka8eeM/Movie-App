package com.example.testtmdb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.testtmdb.interfaces.Api;
import com.example.testtmdb.adapters.MovieAdapter;
import com.example.testtmdb.R;
import com.example.testtmdb.models.MovieModel;
import com.example.testtmdb.models.PageModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/*
 * TODO (important) First You need to get your API Key from https://www.themoviedb.org/
 *  then create a class MyApiKey in utils package, then create a String variable as public static final
 *  and assign your key to the variable.
 *  NOTE: your app would not be work if you did not create your own key
 * */

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    public static PageModel retPageModel;
    public static ArrayList<MovieModel> listMovieModel;
    private String SORT_BY;
    private String LAYOUT_VIEW;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        setRecyclerViewLayout();
        loadData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerViewLayout();
        loadData();
    }


    private void setRecyclerViewLayout() {
        LAYOUT_VIEW = preferences.getString("LAYOUT_VIEW", "NOT");
        if (LAYOUT_VIEW != null && !LAYOUT_VIEW.equals("NOT")) {
            if (LAYOUT_VIEW.equals("Grid View"))
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            else
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void loadData() {
        SORT_BY = preferences.getString("SORT_BY", "NOT");
        editor = preferences.edit();
        if (SORT_BY != null && !SORT_BY.equals("NOT"))
            getSupportActionBar().setTitle(SORT_BY.toUpperCase());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<PageModel> call = null;
        if (SORT_BY != null && !SORT_BY.equals("NOT")) {
            if (SORT_BY.equals("Most Popular"))
                call = api.getPopular();
            else if (SORT_BY.equals("Top Rated"))
                call = api.getTopRated();
            else {
                Gson gson = new Gson();
                String json = preferences.getString("FAV_LIST", null);
                Type type = new TypeToken<ArrayList<MovieModel>>() {
                }.getType();
                listMovieModel = gson.fromJson(json, type);
                if (listMovieModel == null)
                    listMovieModel = new ArrayList<>();
                MovieAdapter adapter = new MovieAdapter(MainActivity.this, listMovieModel);
                recyclerView.setAdapter(adapter);
                return;
            }
        } else
            call = api.getPopular();


        call.enqueue(new Callback<PageModel>() {
            @Override
            public void onResponse(Call<PageModel> call, Response<PageModel> response) {
                retPageModel = response.body();
                listMovieModel = retPageModel.getResults();
                MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, listMovieModel);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<PageModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Can not load movies!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, 99);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}