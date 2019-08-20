package com.lite.main.mvvm.repo;

import com.lite.main.mvvm.models.ImageSource;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface GetData {

    @GET("/photos")
    public Call<List<ImageSource>> getImages(@Query("page") int page);
}
