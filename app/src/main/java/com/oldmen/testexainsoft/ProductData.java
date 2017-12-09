package com.oldmen.testexainsoft;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by MVP on 09.12.2017.
 */

@Root(name = "products", strict = false)
public class ProductData {

    @ElementList(inline = true)
    private ArrayList<Product> mProductList;

    public ProductData() {
    }

    public ArrayList<Product> getmProductList() {
        return mProductList;
    }

    public void setmProductList(ArrayList<Product> mProductList) {
        this.mProductList = mProductList;
    }
}
