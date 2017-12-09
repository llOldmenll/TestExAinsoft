package com.oldmen.testexainsoft.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by MVP on 09.12.2017.
 */

@Root(name = "product")
public class Product {

    @Element(name = "id")
    private int mId;
    @Element(name = "name")
    private String mName;
    @Element(name = "price")
    private double mPrice;


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }
}
