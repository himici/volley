package com.dvp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/7/20.
 */
public class ImageRequestActivity extends Activity{

    ImageView img_net;
    ImageView img_net_imageloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_request);

        img_net = (ImageView) findViewById(R.id.img_net);
        img_net_imageloader = (ImageView) findViewById(R.id.img_net_imageloader);
        setImageview();
        setImageviewImageLoader();
    }

    private void setImageview(){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(
                "http://image.cnwest.com/attachement/jpg/site1/20110412/001372d8a0e00f0e40f12f.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        img_net.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                img_net.setImageResource(R.mipmap.ic_launcher);
            }
        });
        mQueue.add(imageRequest);
    }

    private void setImageviewImageLoader(){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(img_net_imageloader,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
//        imageLoader.get("http://image.cnwest.com/attachement/jpg/site1/20110412/001372d8a0e00f0e40f12f.jpg", listener);
        imageLoader.get("http://image.cnwest.com/attachement/jpg/site1/20110412/001372d8a0e00f0e40f12f.jpg",listener, 200, 200);
    }

}
