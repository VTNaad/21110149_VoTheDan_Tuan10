package com.example.loadcatagory;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcCate;
    private CategoryAdapter categoryAdapter;
    private APIService apiService;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        GetCategory();
    }

    private void AnhXa() {
        // Ánh xạ
        rcCate = findViewById(R.id.rc_category);
    }

    private void GetCategory() {
        // Create an instance of the APIService interface
        apiService = RetrofitClient.getRetrofit().create(APIService.class);

        // Perform the API call asynchronously
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    // Get the list of categories from the response body
                    categoryList = response.body();

                    // Initialize the adapter
                    categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);

                    // Configure RecyclerView
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcCate.setLayoutManager(layoutManager);
                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    // Handle unsuccessful response
                    int statusCode = response.code();
                    Log.d("MainActivity", "Request failed with status code: " + statusCode);
                    // Handle errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Log error message in case of failure
                Log.d("MainActivity", "Request failed: " + t.getMessage());
            }
        });
    }
}
