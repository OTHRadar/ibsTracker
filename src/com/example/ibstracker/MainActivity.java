package com.example.ibstracker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button fodmapsTrackButton = (Button)findViewById(R.id.button1);
		final Button symptomButton = (Button)findViewById(R.id.button2);
		final Button fodmapsShopButton = (Button)findViewById(R.id.button3);
		final Button activityButton = (Button)findViewById(R.id.button4);
		final Button graphsButton = (Button)findViewById(R.id.button5);
		setButtonHandler(fodmapsTrackButton, new Intent(this, FodmapsTrackerActivity.class));
		setButtonHandler(symptomButton, new Intent(this, SymptomTrackerActivity.class));
		setButtonHandler(fodmapsShopButton , new Intent(this, FodmapsShopActivity.class));
		setButtonHandler(activityButton, new Intent(this, ActivityTrackerActivity.class));
		setButtonHandler(graphsButton, new Intent(this, GraphActivity.class));
	}

	protected void setButtonHandler(Button b, final Intent i) {
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View w){
				startActivity(i);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
