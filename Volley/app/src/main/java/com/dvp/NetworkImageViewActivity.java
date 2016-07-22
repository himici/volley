package com.dvp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/7/22.
 */
public class NetworkImageViewActivity extends Activity{

    NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_imageview);

        RequestQueue mQueue = Volley.newRequestQueue(this);
        networkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        networkImageView.setImageUrl("http://image.cnwest.com/attachement/jpg/site1/20110412/001372d8a0e00f0e40f12f.jpg",imageLoader);
    }
}
