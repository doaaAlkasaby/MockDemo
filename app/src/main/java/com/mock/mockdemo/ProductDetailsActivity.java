package com.mock.mockdemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mock.mockdemo.utils.Constants;
import com.mock.mockdemo.model.Product;

public class ProductDetailsActivity extends AppCompatActivity {

    Product product;
    public TextView priceTv;
    public TextView descriptionTv;
    SimpleDraweeView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initializeViews();

        priceTv.setText(product.getPrice() + "");
        descriptionTv.setText(product.getProductDescription());
        imageView.setImageURI(Uri.parse(product.getImage().getUrl()));

    }

    private void initializeViews() {
        product = getIntent().getExtras().getParcelable(Constants.PRODUCT);

        imageView = (SimpleDraweeView) findViewById(R.id.imageView);

        priceTv = (TextView) findViewById(R.id.priceTv);
        descriptionTv = (TextView) findViewById(R.id.descriptionTv);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (product.getImage().getWidth(), product.getImage().getHeight());
        imageView.setLayoutParams(layoutParams);

    }
}
