package com.mock.mockdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mock.mockdemo.Interface.ProductApi;
import com.mock.mockdemo.utils.Constants;
import com.mock.mockdemo.utils.Url;
import com.mock.mockdemo.adapters.ProductsAdapter;
import com.mock.mockdemo.model.Product;
import com.mock.mockdemo.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;


public class MainActivity extends AppCompatActivity {

    ArrayList<Product> allProducts;
    int fromID = 0;
    Context context;
    ProductApi productApi;
    ProductsAdapter adapter;

    private RecyclerView productsRecyclerView;
    ProgressBar loadingSpinner;
    TextView noProductsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeViews();

        if (Util.isConnected(context)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Url.GET_PRODUCTS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            productApi = retrofit.create(ProductApi.class);

            fetchNewProducts();

            productsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                    super.onScrollStateChanged(recyclerView, scrollState);
                    //detect if scrollbar reach to bottom
                    if (!recyclerView.canScrollVertically(1) && scrollState == SCROLL_STATE_IDLE) {
                        //fetch more allProducts
                        fromID += 20;
                        fetchNewProducts();
                    }
                }

            });
        } else {
            allProducts = Util.getSavedArrayList(context, Constants.DATA_FILE);
            if (allProducts != null && allProducts.size() > 0) {
                adapter = new ProductsAdapter(context, allProducts);
                productsRecyclerView.setAdapter(adapter);
            }else{
                noProductsFound.setVisibility(View.VISIBLE);
            }
        }
    }

    private void fetchNewProducts() {
        loadingSpinner.setVisibility(View.VISIBLE);

        Call connection = productApi.getProducts(fromID, Constants.PRODUCT_COUNT);

        connection.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("onResponse", response.body() + "");
                List<Product> products = (List<Product>) response.body();


                if (products != null) {
                    if (allProducts == null || allProducts.size() == 0) {
                        allProducts.addAll(products);
                        adapter = new ProductsAdapter(context, allProducts);
                        productsRecyclerView.setAdapter(adapter);

                    } else if (allProducts.size() > 0) {
                        allProducts.addAll(products);
                    }
                    Util.saveArrayList(context, Constants.DATA_FILE, allProducts);
                    adapter.notifyDataSetChanged();
                }else{
                    noProductsFound.setVisibility(View.VISIBLE);
                }

                loadingSpinner.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                loadingSpinner.setVisibility(View.GONE);
            }
        });
    }


    private void InitializeViews() {
        context = MainActivity.this;

        //set actionbar title
        getSupportActionBar().setTitle(R.string.products);

        //initialize fresco image loader
        Fresco.initialize(this);

        allProducts = new ArrayList<>();

        noProductsFound = (TextView)findViewById(R.id.noProductsFound);
        noProductsFound.setVisibility(View.GONE);

        productsRecyclerView = (RecyclerView) findViewById(R.id.productsRecyclerView);
        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        productsRecyclerView.setLayoutManager(gaggeredGridLayoutManager);

        loadingSpinner = (ProgressBar) findViewById(R.id.loadingSpinner);
    }

}
