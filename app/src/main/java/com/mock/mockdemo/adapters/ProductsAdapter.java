package com.mock.mockdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mock.mockdemo.ProductDetailsActivity;
import com.mock.mockdemo.R;
import com.mock.mockdemo.utils.Constants;
import com.mock.mockdemo.model.Product;
import com.mock.mockdemo.utils.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
//import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 007 on 10/07/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    //all data fetched
    ArrayList<Product> products;
    //for one item
    Product product;
    Context context;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView priceTv;
        public TextView descriptionTv;
        //*fresco
        SimpleDraweeView imageView;

        public ViewHolder(View v) {
            super(v);
            //*fresco
            imageView = (SimpleDraweeView) v.findViewById(R.id.imageView);
            priceTv = (TextView) v.findViewById(R.id.priceTv);
            descriptionTv = (TextView) v.findViewById(R.id.descriptionTv);
        }
    }

    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view =
                inflater.inflate(R.layout.product_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.ViewHolder holder, final int position) {

        product = products.get(position);

        holder.priceTv.setText(product.getPrice() + "");
        holder.descriptionTv.setText(product.getProductDescription());
        holder.imageView.getLayoutParams().height = product.getImage().getHeight();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra(Constants.PRODUCT, (Parcelable) products.get(position));
                context.startActivity(intent);
            }
        });

        //*fresco
        holder.imageView.setImageURI(Uri.parse(product.getImage().getUrl()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
