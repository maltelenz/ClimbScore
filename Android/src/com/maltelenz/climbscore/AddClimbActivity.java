package com.maltelenz.climbscore;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class AddClimbActivity extends Activity {

	private View typeContainer;
	private RadioButton tradRadioButton;
	private RadioButton leadRadioButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_climb);
		// Show the Up button in the action bar.
		setupActionBar();

		// Initialize the various view fields
		typeContainer = findViewById(R.id.typecontainer);
		tradRadioButton = (RadioButton) findViewById(R.id.radioTrad);
		leadRadioButton = (RadioButton) findViewById(R.id.radioLead);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.radioIndoors:
	            if (checked)
	                showTypeRadioGroupIndoors();
	            break;
	        case R.id.radioOutdoors:
	            if (checked)
	                showTypeRadioGroupOutdoors();
	            break;
	    }
	}
	
	private void showTypeRadioGroupOutdoors() {
		tradRadioButton.setVisibility(View.VISIBLE);
		tradRadioButton.setChecked(true);
		typeContainer.setVisibility(View.VISIBLE);
	}

	private void showTypeRadioGroupIndoors() {
		tradRadioButton.setVisibility(View.GONE);
		leadRadioButton.setChecked(true);
		typeContainer.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_climb, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
