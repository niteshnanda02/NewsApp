package com.example.citispotter.rests;

import com.example.citispotter.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<ResponseModel> getLatestNews(@Query("sources") String source, @Query("apiKey") String apiKey);
    @GET("top-headlines")
    Call<ResponseModel> getNews(@Query("country") String country,@Query("category") String category,@Query("apiKey") String apiKey);

}
