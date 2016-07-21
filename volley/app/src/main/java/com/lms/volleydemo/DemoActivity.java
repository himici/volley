package com.lms.volleydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dvp.R;

public class DemoActivity extends Activity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		
		Button btnJson = (Button)findViewById(R.id.btnJson);
		btnJson.setOnClickListener(this);
		Button btnImage = (Button)findViewById(R.id.btnImage);
		btnImage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnJson:
			intent = new Intent(this, JsonActivity.class);
			break;
		case R.id.btnImage:
			intent = new Intent(this, MainActivity.class);
			break;
		}
		if (intent != null) {
			startActivity(intent);
		}
	}
}
