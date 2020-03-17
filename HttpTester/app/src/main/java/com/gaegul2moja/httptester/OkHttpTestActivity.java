package com.gaegul2moja.httptester;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OkHttpTestActivity extends AppCompatActivity {
    private TextView okHttpTestTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_okhttptest);

        okHttpTestTV = findViewById(R.id.okhttp_weather_tv);

        new ReceiveShortWeatherAsync(okHttpTestTV).execute();
    }
}
