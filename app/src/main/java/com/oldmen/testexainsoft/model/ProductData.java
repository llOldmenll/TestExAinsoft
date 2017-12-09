package com.oldmen.testexainsoft.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;

import java.util.List;

/**
 * Created by MVP on 09.12.2017.
 */

public class ProductData {

    @Path("products")
    @ElementList(inline = true, required = false)
    private List<Product> mProductList;


    public List<Product> getmProductList() {
        return mProductList;
    }

    public void setmProductList(List<Product> mProductList) {
        this.mProductList = mProductList;
    }
}
