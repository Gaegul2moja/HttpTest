package com.gaegul2moja.httptester;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonTestActivity extends AppCompatActivity {
    private final String BASEURL = "https://jsonplaceholder.typicode.com/";
    private TextView jsonResultTextView;
    private Button jsonPostAllButton;
    private Button jsonPostByIdButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        jsonResultTextView = findViewById(R.id.json_result_tv);

        jsonPostAllButton = findViewById(R.id.json_posts_all_button);
        jsonPostByIdButton = findViewById(R.id.json_posts_by_id_button);

        //baseUrl build
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();

        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        final JsonPlaceHolderByNumberApi jsonPlaceHolderByNumberApi = retrofit.create(JsonPlaceHolderByNumberApi.class);

        //onclick
        jsonPostAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JsonTestActivity.this, "POSTS ALL FINISHED", Toast.LENGTH_SHORT).show();
                getAllPosts(jsonPlaceHolderApi);
            }
        });

        jsonPostByIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JsonTestActivity.this, "POSTS BY ID FINISHED", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder getIdDialog = new AlertDialog.Builder(JsonTestActivity.this);
                getIdDialog.setTitle("GetIdDialog");
                getIdDialog.setMessage("검색 할 userId를 입력하세요");

                final EditText idInput = new EditText(JsonTestActivity.this);
                idInput.setText("1");
                getIdDialog.setView(idInput);

                getIdDialog.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userIdValue = idInput.getText().toString();
                        int idNum = Integer.parseInt(userIdValue);

                        getPostsById(jsonPlaceHolderByNumberApi, idNum);
                    }
                });

                getIdDialog.show();
            }
        });

    }

    private void getAllPosts (JsonPlaceHolderApi jsonPlaceHolderApi) {
        Call<List<Post>> call = jsonPlaceHolderApi.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    jsonResultTextView.setText("결과 : " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n";
                    content += "============================" + "\n";

                    jsonResultTextView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                jsonResultTextView.setText("ERROR : " + t.getMessage());
            }
        });
    }

    private void getPostsById (JsonPlaceHolderByNumberApi jsonPlaceHolderByNumberApi, int userIdNum) {
        Call<List<Post>> call = jsonPlaceHolderByNumberApi.getPost(userIdNum);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    jsonResultTextView.setText("결과 : " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n";
                    content += "============================" + "\n";

                    jsonResultTextView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                jsonResultTextView.setText("ERROR : " + t.getMessage());
            }
        });
    }
}
