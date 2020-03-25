package com.gaegul2moja.httptester;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderByNumberApi {
    @GET("posts") //url + post/ ~로부터 가져오는 것
    Call<List<Post>> getPost(@Query("userId") int id); //userId를 기반으로 질의한다.
}
