package com.oldmen.testexainsoft.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.oldmen.testexainsoft.utills.Constants;
import com.oldmen.testexainsoft.database.DbHandler;
import com.oldmen.testexainsoft.utills.InternetConnectionCheck;
import com.oldmen.testexainsoft.model.Product;
import com.oldmen.testexainsoft.model.ProductData;
import com.oldmen.testexainsoft.R;
import com.oldmen.testexainsoft.api.RetrofitClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_download_main)
    Button mBtnDownload;
    @BindView(R.id.btn_show_main)
    Button mBtnShow;
    @BindView(R.id.progress_bar_main)
    ProgressBar mProgressBar;

    private ArrayList<Product> mProductList = new ArrayList<>();
    private DbHandler mDbHandler = new DbHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initBtn();
    }

    private void initBtn() {
        mBtnDownload.setOnClickListener(view -> {
            if (InternetConnectionCheck.checkConnection(getApplicationContext())) {
                mBtnDownload.setVisibility(View.INVISIBLE);
                mBtnShow.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                downloadData();
            } else {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle(getString(R.string.download_data))
                        .setMessage(getString(R.string.internet_unavailable))
                        .setPositiveButton(getString(R.string.ok), null)
                        .create().show();
            }
        });
        mBtnShow.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ProductActivity.class)));
    }

    private void downloadData() {
        Call<ProductData> call = RetrofitClient.getApiService().getProducts();
        call.enqueue(new Callback<ProductData>() {

            @Override
            public void onResponse(@NonNull Call<ProductData> call, @NonNull Response<ProductData> response) {
                if (response.body() != null && response.body().getmProductList() != null) {
                    mProductList.addAll(response.body().getmProductList());
                    mDbHandler.onUpgrade(mDbHandler.getWritableDatabase(),
                            Constants.DATABASE_VERSION, Constants.DATABASE_VERSION);
                    addProductsInDB();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductData> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Downloading was failed", Toast.LENGTH_SHORT).show();
            }
        });
        mBtnDownload.setVisibility(View.VISIBLE);
        mBtnShow.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void addProductsInDB() {
        for (Product product : mProductList)
            mDbHandler.addProduct(product);

        Toast.makeText(MainActivity.this, "Downloading Success", Toast.LENGTH_SHORT).show();
    }
}
