package com.oldmen.testexainsoft;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> mProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void downloadData(){
        Call<ProductData> call = RetrofitClient.getApiService(this).getProducts();
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(@NonNull Call<ProductData> call, @NonNull Response<ProductData> response) {
                if(response.body() != null && response.body().getmProductList() != null) {
                    mProductList = response.body().getmProductList();
                    for (Product product:mProductList){
                        Log.i("", "onResponse: " + product.getmName());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductData> call, @NonNull Throwable t) {

            }
        });
    }
}
