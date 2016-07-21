package com.lms.volleydemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

import com.dvp.R;

public class MainActivity extends Activity {

	private static final String[] URLS = {
		"http://img.my.csdn.net/uploads/201403/03/1393854094_4652.jpg",
		"http://img.my.csdn.net/uploads/201403/03/1393854084_6138.jpg",
		"http://img.my.csdn.net/uploads/201403/03/1393854084_1323.jpg",
		"http://img.my.csdn.net/uploads/201403/03/1393854084_8439.jpg",
		"http://img.my.csdn.net/uploads/201403/03/1393854083_6511.jpg",
		"http://img.my.csdn.net/uploads/201403/03/1393854083_2323.jpg"		
	};
	
	private static final String[] DESCS = {
		"photo1","photo2","photo3","photo4","photo5","photo6"
	};
	
	private RequestQueue mQueue;
	
	private GridView gvImages;
	
	private NetworkImageView networkImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mQueue = VolleyQueue.getInstance(this);
		
		gvImages = (GridView)findViewById(R.id.gvImages);
		GridAdapter adpater = new GridAdapter();
		gvImages.setAdapter(adpater);
				
		networkImageView = (NetworkImageView) findViewById(R.id.nivTestView);
		
		LruImageCache lruImageCache = LruImageCache.instance();
		
		ImageLoader imageLoader = new ImageLoader(mQueue,lruImageCache);
				
		networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
		networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
		networkImageView.setImageUrl(URLS[1], imageLoader);
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	class GridAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;

		public GridAdapter() {
			layoutInflater = LayoutInflater.from(MainActivity.this);
		}

		@Override
		public int getCount() {
			return URLS.length;
		}

		@Override
		public Object getItem(int position) {
			return URLS[position];
		}

		@Override
		public long getItemId(int position) {
			return position;					
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = layoutInflater.inflate(R.layout.grid_item, null);
				viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
				viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}			
			
			readBitmapViaVolley(URLS[position],viewHolder.ivImage);
			viewHolder.tvDesc.setText(DESCS[position]);
			return convertView;
		}

	}

	static class ViewHolder {
		ImageView ivImage;
		TextView tvDesc;
	}
	
	public void readBitmapViaVolley(String imgUrl, final ImageView imageView) {
		ImageRequest imgRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap arg0) {
						imageView.setImageBitmap(arg0);
					}
				}, 
				100, 
				100, 
				Config.ARGB_8888, 
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						imageView.setImageResource(R.mipmap.ic_launcher);
					}
				});
		mQueue.add(imgRequest);
	}

	public Bitmap readBitmapFromUrl(String imgUrl){
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inPreferredConfig = Bitmap.Config.ARGB_8888;
		op.inDither = false;
		op.inScaled = false;
		Bitmap bitmap = null;
		try {
			URL url = new URL(imgUrl);			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3 * 1000);
			
			if(conn.getResponseCode() != 200){ 
				throw new RuntimeException("Request Failed");
			}
			InputStream is = conn.getInputStream();
			
			bitmap = BitmapFactory.decodeStream(is,null,op);
			
			bitmap = Helper.zoomBitmap(bitmap, 150, 150);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return bitmap;
	}
}
