package com.gaegul2moja.httptester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button toOkHttpButton;
    private Button toRetrofitButton;
    private Button toVolleyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toOkHttpButton = findViewById(R.id.main_to_okhttp_button);
        toRetrofitButton = findViewById(R.id.main_to_retrofit_button);
        toVolleyButton = findViewById(R.id.main_to_volley_button);

        toOkHttpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OkHttpTestActivity.class);
                startActivity(intent);
            }
        });
        toRetrofitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RetrofitTestActivity.class);
                startActivity(intent);
            }
        });
        toVolleyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VolleyTestActivity.class);
                startActivity(intent);
            }
        });

    }

}
