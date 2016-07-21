package com.lms.volleydemo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyQueue {

	private static RequestQueue mQueue;
	
	public static RequestQueue getInstance(Context context){
		if(mQueue == null){
			mQueue = Volley.newRequestQueue(context);
		}
		
		return mQueue;
	}
}
