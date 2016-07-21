package com.dvp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class StringRequestActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);
        setStringGETRequest();
//        setJsonRequest();
    }

    /**
     * StringRequest get请求
     * */
    private void setStringGETRequest(){
        // 1. 获取到一个RequestQueue对象
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        // 2. 创建一个StringRequest对象   (Request.Method.GET,这个参数，可有可没有，没有的话，默认是get请求，可以点进去看源码)
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://www.baidu.com/", new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.i("TAG",response);
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG",error.getMessage());
            }
        });
        //3. 将StringRequest对象添加到RequestQueue里面
        mRequestQueue.add(stringRequest);
    }

    /**
     * StringRequest post 请求
     * */
//    private void setStringPOSTRequest(){
//        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  listener, errorListener) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("params1", "value1");
//                map.put("params2", "value2");
//                return map;
//            }
//        };
//        mRequestQueue.add(mRequestQueue);
//    }

    private void setJsonRequest(){
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }

}
