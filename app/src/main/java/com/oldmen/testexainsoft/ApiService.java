package com.oldmen.testexainsoft;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MVP on 09.12.2017.
 */

public interface ApiService {

    @GET("test.xml")
    Call<ProductData> getProducts();

}
