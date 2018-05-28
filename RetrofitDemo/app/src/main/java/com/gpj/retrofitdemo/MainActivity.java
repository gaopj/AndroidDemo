package com.gpj.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<List<Repo>> gpjList = gitHubService.listRepos("gaopj");
        gpjList.enqueue(mCallback);
    }

    private Callback<List<Repo>> mCallback = new Callback<List<Repo>>() {
        @Override
        public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
            List<Repo> body = response.body();
            for (int i = 0; i < body.size(); i++) {
                Repo repo = body.get(i);
                Log.d(TAG, "onResponse: " + repo.getName());
            }
        }

        @Override
        public void onFailure(Call<List<Repo>> call, Throwable t) {
            Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
        }
    };
}
