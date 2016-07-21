package com.lms.volleydemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dvp.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class JsonActivity extends Activity{

	private RequestQueue mQueue;
	
	private static final String WEATHER_LINK = "http://www.weather.com.cn/data/sk/101280101.html";
	
	private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>(); 
	
	private ListView lvWeather;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_json);
		
		lvWeather = (ListView)findViewById(R.id.lvWeather);
		
		mQueue = VolleyQueue.getInstance(this);
		getWeatherInfo();	
	}
	
	public void getWeatherInfo(){
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(WEATHER_LINK, null, 
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject arg0) {
						list.clear();
						Iterator<String> it = arg0.keys();
						while(it.hasNext()){
							String key = it.next();
							JSONObject obj = null;
							try {
								 obj = arg0.getJSONObject(key);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (obj != null) {
								Iterator<String> objIt = obj.keys();
								while (objIt.hasNext()) {
									String objKey = objIt.next();
									String objValue;
									try {
										objValue = obj.getString(objKey);
										HashMap<String, String> map = new HashMap<String, String>();
										map.put("title", objKey);
										map.put("content", objValue);
										Log.v(Helper.TAG, "title = " + objKey + " | content = " + objValue);
										list.add(map);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

								SimpleAdapter simpleAdapter = new SimpleAdapter(JsonActivity.this, list, 
										android.R.layout.simple_list_item_2, new String[] {"title","content"}, 
										new int[] {android.R.id.text1, android.R.id.text2});			
								
								lvWeather.setAdapter(simpleAdapter);
							}
						}
					}			
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(JsonActivity.this, "Error Happened", Toast.LENGTH_LONG);
					}
				});
		mQueue.add(jsonObjectRequest);
	}
}
