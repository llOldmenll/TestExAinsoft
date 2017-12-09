package com.oldmen.testexainsoft.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.oldmen.testexainsoft.database.DbHandler;
import com.oldmen.testexainsoft.model.Product;
import com.oldmen.testexainsoft.adapter.ProductAdapter;
import com.oldmen.testexainsoft.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.ItemClickListener {

    @BindView(R.id.recycler_product)
    RecyclerView mRecycler;
    @BindView(R.id.notif_no_products)
    TextView mNotifNoData;

    private DbHandler mDbHandler = new DbHandler(this);
    private ArrayList<Product> mProductList = new ArrayList<>();
    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        getProducts();
        mNotifNoData.setVisibility((mProductList.size() > 0) ? View.GONE : View.VISIBLE);
        mAdapter = new ProductAdapter(this, mProductList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(mAdapter);
    }

    private ArrayList<Product> getProducts() {
        mProductList.clear();
        mProductList.addAll(mDbHandler.getAllProducts());
        return mProductList;
    }

    private void changeProductPrice(Product product, String price) {
        product.setmPrice(Double.valueOf(price));
        mDbHandler.updateProduct(product);
        getProducts();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(Product product) {
        View editDialogView = LayoutInflater.from(this).inflate(R.layout.change_price_dialog, null);
        EditText editTxt = editDialogView.findViewById(R.id.edit_txt_rename_dialog);
        editTxt.setHint(String.valueOf(product.getmPrice()));


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(product.getmName())
                .setView(editDialogView)
                .setPositiveButton(getString(R.string.ok),
                        (dialogInterface, i) -> {
                            if (!editTxt.getText().toString().isEmpty())
                                changeProductPrice(product, editTxt.getText().toString());
                        })
                .setNegativeButton(getString(R.string.cancel), null)
                .create().show();
    }

}
