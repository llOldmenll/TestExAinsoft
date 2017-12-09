package com.oldmen.testexainsoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oldmen.testexainsoft.R;
import com.oldmen.testexainsoft.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MVP on 09.12.2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context mContext;
    private ArrayList<Product> mProductList;
    private ItemClickListener mListener;


    public ProductAdapter(Context mContext, ArrayList<Product> mProductList) {
        this.mProductList = mProductList;
        this.mContext = mContext;
        if(mContext instanceof ItemClickListener)
            mListener = (ItemClickListener) mContext;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_item, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name_item)
        TextView mName;
        @BindView(R.id.txt_price_item)
        TextView mPrice;

        public ProductHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(int position) {
            Product product = mProductList.get(position);
            mName.setText(String.format(mContext.getString(R.string.item_product_name), product.getmName()));
            mPrice.setText(String.format(mContext.getString(R.string.item_product_price), product.getmPrice()));
            itemView.setOnClickListener(view -> {
                mListener.onItemClicked(mProductList.get(position));
            });
        }
    }

    public interface ItemClickListener{
        void onItemClicked(Product product);
    }
}
