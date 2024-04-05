package com.example.loadcatagory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.loadcatagory.Category;
public interface APIService {
    @GET("categories.php")
    Call<List<Category>>getCategoryAll();
}
