package com.mock.mockdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.mock.mockdemo.model.Product;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by 007 on 10/07/2017.
 */

public class Util {


    public static void saveArrayList(Context context, String fileName,ArrayList<Product> arrayList) {
        // check if sd card is mounted and available for read & write
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            try {
                FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                out.writeObject(arrayList);
                out.close();
                fileOutputStream.close();
                Log.e("saveArrayList", "saved");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Product> getSavedArrayList(Context context, String fileName) {
        ArrayList<Product> savedArrayList = null;
        // check if sd card is mounted and available for read & write
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            try {
                FileInputStream inputStream = context.openFileInput(fileName);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                savedArrayList = (ArrayList<Product>) in.readObject();
                in.close();
                inputStream.close();
                Log.e("getSavedArrayList", "read");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return savedArrayList;
    }

    //check device network  is available or not
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
