package com.mock.mockdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 007 on 10/07/2017.
 */

public class Product implements Parcelable , Serializable{
    Long id ;
    String productDescription;
    Image image;
    int price;

    Product parcable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //-----------------------------


    @Override
    public String toString() {
        return getProductDescription();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

       // out.writeLong(id);
        out.writeInt(price);
        out.writeString(productDescription);
        out.writeParcelable(image, flags);


        out.writeParcelable(parcable, flags);
    }

    private Product(Parcel in) {
       // id = in.readLong();
        price = in.readInt();

        productDescription = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());


        parcable = in.readParcelable(Product.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
