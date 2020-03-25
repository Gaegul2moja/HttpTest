package com.gaegul2moja.httptester;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("posts") //url + post/ ~로부터 가져오는 것
    Call<List<Post>> getPost();
}
