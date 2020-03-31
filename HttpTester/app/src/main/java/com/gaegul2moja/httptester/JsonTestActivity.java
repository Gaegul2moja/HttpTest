package com.gaegul2moja.httptester;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class JsonTestActivity extends AppCompatActivity {
    private final String BASEURL = "https://jsonplaceholder.typicode.com/";
    private TextView jsonResultTextView;
    private Button jsonPostAllButton;
    private Button jsonPostByIdButton;
    private Button jsonPostCreateButton;
    private Button jsonPutButton;
    private Button jsonDeleteButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        jsonResultTextView = findViewById(R.id.json_result_tv);

        jsonPostAllButton = findViewById(R.id.json_posts_all_button);
        jsonPostByIdButton = findViewById(R.id.json_posts_by_id_button);
        jsonPostCreateButton = findViewById(R.id.json_posts_create_button);
        jsonPutButton = findViewById(R.id.json_put_button);
        jsonDeleteButton = findViewById(R.id.json_delete_button);

        //baseUrl build
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();

        final JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        final JsonPlaceHolderByNumberApi jsonPlaceHolderByNumberApi = retrofit.create(JsonPlaceHolderByNumberApi.class);

        //onclick
        jsonPostAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllPosts(jsonPlaceHolderApi);
            }
        });

        jsonPostByIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        jsonPostCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JsonTestActivity.this);
                LayoutInflater inflater = (LayoutInflater) JsonTestActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View view = inflater.inflate(R.layout.json_dialog_layout, null);
                alertDialogBuilder.setView(view);

                final AlertDialog jsonDialog = alertDialogBuilder.create();
                jsonDialog.setTitle("JsonPostDialog");

                TextView firstTitle = view.findViewById(R.id.dialog_first_et_title);
                TextView secondTitle = view.findViewById(R.id.dialog_second_et_title);
                TextView thirdTitle = view.findViewById(R.id.dialog_third_et_title);
                final EditText firstEdit = view.findViewById(R.id.first_et);
                final EditText secondEdit = view.findViewById(R.id.second_et);
                final EditText thirdEdit = view.findViewById(R.id.third_et);
                Button yesButton = view.findViewById(R.id.dialog_positive_button);
                Button noButton = view.findViewById(R.id.dialog_negative_button);
                Button fieldButton = view.findViewById(R.id.dialog_middle_button);

                firstTitle.setText("userId : ");
                secondTitle.setText("Title : ");
                thirdTitle.setText("Text : ");
                firstEdit.setInputType(TYPE_CLASS_NUMBER);
                firstEdit.setText("33");
                secondEdit.setText("CreateTestTitle");
                thirdEdit.setText("CreateTestText");
                fieldButton.setText("Map 전송");

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         int userId = Integer.parseInt(firstEdit.getText().toString());
                         String title = secondEdit.getText().toString();
                         String text = thirdEdit.getText().toString();

                         createPost(jsonPlaceHolderApi, userId, title, text);

                         jsonDialog.dismiss();
                    }
                });
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jsonDialog.dismiss();
                    }
                });
                fieldButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int userId = Integer.parseInt(firstEdit.getText().toString());
                        String title = secondEdit.getText().toString();
                        String text = thirdEdit.getText().toString();

                        createPostByFieldMap(jsonPlaceHolderApi, userId, title, text);

                        jsonDialog.dismiss();
                    }
                });
                jsonDialog.show();
            }
        });

        jsonPutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JsonTestActivity.this);
                LayoutInflater inflater = (LayoutInflater) JsonTestActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View view = inflater.inflate(R.layout.json_dialog_layout, null);
                alertDialogBuilder.setView(view);

                final AlertDialog jsonDialog = alertDialogBuilder.create();
                jsonDialog.setTitle("JsonPutDialog");

                TextView firstTitle = view.findViewById(R.id.dialog_first_et_title);
                TextView secondTitle = view.findViewById(R.id.dialog_second_et_title);
                TextView thirdTitle = view.findViewById(R.id.dialog_third_et_title);
                final EditText firstEdit = view.findViewById(R.id.first_et);
                final EditText secondEdit = view.findViewById(R.id.second_et);
                final EditText thirdEdit = view.findViewById(R.id.third_et);
                Button yesButton = view.findViewById(R.id.dialog_positive_button);
                Button noButton = view.findViewById(R.id.dialog_negative_button);
                Button fieldButton = view.findViewById(R.id.dialog_middle_button);

                firstTitle.setText("userId : ");
                secondTitle.setText("Title : ");
                thirdTitle.setText("Text : ");
                firstEdit.setInputType(TYPE_CLASS_NUMBER);
                firstEdit.setText("77");
                secondEdit.setText("UpdateTestTitle");
                thirdEdit.setText("UpdateTestText");
                fieldButton.setVisibility(View.INVISIBLE);

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int userId = Integer.parseInt(firstEdit.getText().toString());
                        String title = secondEdit.getText().toString();
                        String text = thirdEdit.getText().toString();

                        updatePost(jsonPlaceHolderApi, userId, title, text);

                        jsonDialog.dismiss();
                    }
                });
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jsonDialog.dismiss();
                    }
                });

                jsonDialog.show();
            }
        });
        jsonDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JsonTestActivity.this);
                LayoutInflater inflater = (LayoutInflater) JsonTestActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View view = inflater.inflate(R.layout.json_dialog_layout, null);
                alertDialogBuilder.setView(view);

                final AlertDialog jsonDialog = alertDialogBuilder.create();
                jsonDialog.setTitle("JsonPutDialog");

                TextView firstTitle = view.findViewById(R.id.dialog_first_et_title);
                TextView secondTitle = view.findViewById(R.id.dialog_second_et_title);
                TextView thirdTitle = view.findViewById(R.id.dialog_third_et_title);
                final EditText firstEdit = view.findViewById(R.id.first_et);
                final EditText secondEdit = view.findViewById(R.id.second_et);
                final EditText thirdEdit = view.findViewById(R.id.third_et);
                Button yesButton = view.findViewById(R.id.dialog_positive_button);
                Button noButton = view.findViewById(R.id.dialog_negative_button);
                Button fieldButton = view.findViewById(R.id.dialog_middle_button);

                firstTitle.setText("userId : ");
                firstEdit.setInputType(TYPE_CLASS_NUMBER);
                firstEdit.setText("77");
                fieldButton.setVisibility(View.INVISIBLE);
                secondTitle.setVisibility(View.INVISIBLE);
                thirdTitle.setVisibility(View.INVISIBLE);
                secondEdit.setVisibility(View.INVISIBLE);
                thirdEdit.setVisibility(View.INVISIBLE);


                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int userId = Integer.parseInt(firstEdit.getText().toString());

                        deletePost(jsonPlaceHolderApi, userId);

                        jsonDialog.dismiss();
                    }
                });
                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jsonDialog.dismiss();
                    }
                });

                jsonDialog.show();
            }
        });
    }

    private void getAllPosts (JsonPlaceHolderApi jsonPlaceHolderApi) {
        jsonResultTextView.setText("PROCESSING ...");
        Call<List<Post>> call = jsonPlaceHolderApi.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    jsonResultTextView.setText("결과 : " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                jsonResultTextView.setText("");

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
        jsonResultTextView.setText("PROCESSING ...");
        Call<List<Post>> call = jsonPlaceHolderByNumberApi.getPost(userIdNum);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    jsonResultTextView.setText("결과 : " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                jsonResultTextView.setText("");

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

     public void createPost (JsonPlaceHolderApi jsonPlaceHolderApi, int userId, String title, String text) {
        jsonResultTextView.setText("PROCESSING ...");
         Post post = new Post(userId, title, text);

         Call<Post> call = jsonPlaceHolderApi.createPost(post);

         call.enqueue(new Callback<Post>() {
             @Override
             public void onResponse(Call<Post> call, Response<Post> response) {
                 if (!response.isSuccessful()) {
                     jsonResultTextView.setText("code: " + response.code());
                     return;
                 }

                 Post postResponse = response.body();

                 String content = "";
                 content += "Code : " + response.code() + "\n";
                 content += "Id: " + postResponse.getId() + "\n";
                 content += "User Id: " + postResponse.getUserId() + "\n";
                 content += "Title: " + postResponse.getTitle() + "\n";
                 content += "Text: " + postResponse.getText() + "\n";

                 jsonResultTextView.setText(content);
             }

             @Override
             public void onFailure(Call<Post> call, Throwable t) {
                 jsonResultTextView.setText("Error : " + t.getMessage());
             }
         });
     }
     public void createPostByFieldMap (JsonPlaceHolderApi jsonPlaceHolderApi, int userId, String title, String text) {
         jsonResultTextView.setText("PROCESSING ...");

         Map<String, String> map = new HashMap<>();
         map.put("userId", userId + "");
         map.put("title", title);
         map.put("body", text);

         Call<Post> call = jsonPlaceHolderApi.createPostByFieldMap(map);

         call.enqueue(new Callback<Post>() {
             @Override
             public void onResponse(Call<Post> call, Response<Post> response) {
                 if (!response.isSuccessful()) {
                     jsonResultTextView.setText("code: " + response.code());
                     return;
                 }

                 Post postResponse = response.body();

                 String content = "";
                 content += "Code : " + response.code() + "\n";
                 content += "Id: " + postResponse.getId() + "\n";
                 content += "User Id: " + postResponse.getUserId() + "\n";
                 content += "Title: " + postResponse.getTitle() + "\n";
                 content += "Text: " + postResponse.getText() + "\n";

                 jsonResultTextView.setText(content);
             }

             @Override
             public void onFailure(Call<Post> call, Throwable t) {
                 jsonResultTextView.setText(t.getMessage());
             }
         });
     }
    public void updatePost(JsonPlaceHolderApi jsonPlaceHolderApi, int userId, String title, String text) {
        jsonResultTextView.setText("PROCESSING ...");
        Post post = new Post(userId, title, text);

        Call<Post> call = jsonPlaceHolderApi.putPost(5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    jsonResultTextView.setText("code: " + response.message());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "Id : " + postResponse.getId() + "\n";
                content += "User Id : " + postResponse.getUserId() + "\n";
                content += "Title : " + postResponse.getTitle() + "\n";
                content += "Text : " + postResponse.getText() + "\n";

                jsonResultTextView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                jsonResultTextView.setText(t.getMessage());
            }
        });
    }
    private void deletePost(JsonPlaceHolderApi jsonPlaceHolderApi, final int userId) {
        jsonResultTextView.setText("PROCESSING ...");
        Call<Void> call = jsonPlaceHolderApi.deletePost(userId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    jsonResultTextView.setText("code: " + response.message());
                    return;
                }
                String content = "";
                content += "code: " + response.code()+"\n";
                content += ("userId : " + userId + " 정상적으로 삭제되었습니다.");

                jsonResultTextView.setText(content);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jsonResultTextView.setText(t.getMessage());
            }
        });
    }
}

