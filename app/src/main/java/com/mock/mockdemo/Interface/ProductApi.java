package com.mock.mockdemo.Interface;

import com.mock.mockdemo.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 007 on 10/07/2017.
 */

public interface ProductApi {

    @GET("new_products")
    Call <List<Product>> getProducts(
    @Query("from") int from,
    @Query("count") int count);

}
