package com.gaegul2moja.httptester;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitTestActivity extends AppCompatActivity {
    private TextView retrofitTestTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofittest);

        retrofitTestTV = findViewById(R.id.retrofit_test_weather_tv);

        GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
        Call<List<Contributor>> call = gitHubService.repoContributors("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                retrofitTestTV.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });

    }
    public class Contributor {
        String login;
        String html_url;
        int contributions;
        @Override
        public String toString() {
            return login + " (" + contributions + ")"; }
    }

}
