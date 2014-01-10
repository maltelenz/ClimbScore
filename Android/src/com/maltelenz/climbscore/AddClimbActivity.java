package com.maltelenz.climbscore;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RadioButton;

public class AddClimbActivity extends Activity {

	private View typeContainer;
	private View gradeContainer;
	private RadioButton tradRadioButton;
	private RadioButton leadRadioButton;
	private NumberPicker gradePicker;

	// Free climbing grades
	String[] frenchGrades = {
			"1","2","3",
			"4a","4b","4c",
			"5a","5b","5c",
			"6a","6a+","6b","6b+","6c","6c+",
			"7a","7a+","7b","7b+","7c","7c+",
			"8a","8a+","8b","8b+","8c","8c+",
			"9a","9a+","9b","9b+"
		};

	// Bouldering grades
	String[] fontGrades = {"3",
			"4-","4","4+",
			"5-","5","5+",
			"6A","6A+","6B","6B+","6C","6C+",
			"7A","7A+","7B","7B+","7C","7C+",
			"8A","8A+","8B","8B+","8C","8C+",
		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_climb);
		// Show the Up button in the action bar.
		setupActionBar();

		// Initialize the various view fields
		typeContainer = findViewById(R.id.typecontainer);
		gradeContainer = findViewById(R.id.gradeContainer);
		tradRadioButton = (RadioButton) findViewById(R.id.radioTrad);
		leadRadioButton = (RadioButton) findViewById(R.id.radioLead);
		gradePicker = (NumberPicker) findViewById(R.id.gradePicker);
		// Disable keyboard/editing of grade picker.
		gradePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
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
	        case R.id.radioLead:
	        	if (checked)
	        		showGradePickerLead();
	        	break;
	        case R.id.radioTopRope:
	        	if (checked)
	        		showGradePickerTopRope();
	        	break;
	        case R.id.radioTrad:
	        	if (checked)
	        		showGradePickerTrad();
	        	break;
	        case R.id.radioBouldering:
	        	if (checked)
	        		showGradePickerBouldering();
	        	break;
	        	
	    }
	}
	
	private void showTypeRadioGroupOutdoors() {
		tradRadioButton.setVisibility(View.VISIBLE);
		tradRadioButton.setChecked(true);
		showGradePickerTrad();
		typeContainer.setVisibility(View.VISIBLE);
	}

	private void showTypeRadioGroupIndoors() {
		tradRadioButton.setVisibility(View.GONE);
		leadRadioButton.setChecked(true);
		showGradePickerLead();
		typeContainer.setVisibility(View.VISIBLE);
	}
	
	private void showGradePickerBouldering() {
		gradePicker.setDisplayedValues(null);
        gradePicker.setMaxValue(fontGrades.length - 1);
        gradePicker.setMinValue(0);
        gradePicker.setDisplayedValues(fontGrades);
        gradeContainer.setVisibility(View.VISIBLE);
	}

	private void showGradePickerTrad() {
		gradePicker.setDisplayedValues(null);
        gradePicker.setMaxValue(frenchGrades.length - 1);
        gradePicker.setMinValue(0);
        gradePicker.setDisplayedValues(frenchGrades);
        gradeContainer.setVisibility(View.VISIBLE);
	}

	private void showGradePickerTopRope() {
		gradePicker.setDisplayedValues(null);
        gradePicker.setMaxValue(frenchGrades.length - 1);
        gradePicker.setMinValue(0);
        gradePicker.setDisplayedValues(frenchGrades);
        gradeContainer.setVisibility(View.VISIBLE);
	}

	private void showGradePickerLead() {
		gradePicker.setDisplayedValues(null);
        gradePicker.setMaxValue(frenchGrades.length - 1);
        gradePicker.setMinValue(0);
        gradePicker.setDisplayedValues(frenchGrades);
        gradeContainer.setVisibility(View.VISIBLE);
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
