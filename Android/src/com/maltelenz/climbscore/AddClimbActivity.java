package com.maltelenz.climbscore;

import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddClimbActivity extends Activity {

	private View typeContainer;
	private View gradeContainer;
	private RadioButton indoorsRadioButton;
	private RadioButton outdoorsRadioButton;
	private RadioButton tradRadioButton;
	private RadioButton leadRadioButton;
	private RadioButton boulderRadioButton;
	private RadioButton topropeRadioButton;
	private NumberPicker gradePicker;
	private Button saveButton;

	private RadioGroup inoutdoorsRadioGroup;
	private RadioGroup typeRadioGroup;

	private ClimbDataSource datasource;

	HashMap<String, String[]> climbingGrades = new HashMap<String, String[]>();

	// Free climbing grades
	String[] frenchGrades = { "1", "2", "3", "4a", "4b", "4c", "5a", "5b",
			"5c", "6a", "6a+", "6b", "6b+", "6c", "6c+", "7a", "7a+", "7b",
			"7b+", "7c", "7c+", "8a", "8a+", "8b", "8b+", "8c", "8c+", "9a",
			"9a+", "9b", "9b+" };

	// Bouldering grades
	String[] fontGrades = { "3", "4-", "4", "4+", "5-", "5", "5+", "6A", "6A+",
			"6B", "6B+", "6C", "6C+", "7A", "7A+", "7B", "7B+", "7C", "7C+",
			"8A", "8A+", "8B", "8B+", "8C", "8C+", };

	private String currentGradesystem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_climb);
		// Show the Up button in the action bar.
		setupActionBar();

		// Initialize the various view fields
		typeContainer = findViewById(R.id.typecontainer);
		gradeContainer = findViewById(R.id.gradeContainer);
		indoorsRadioButton = (RadioButton) findViewById(R.id.radioIndoors);
		outdoorsRadioButton = (RadioButton) findViewById(R.id.radioOutdoors);
		tradRadioButton = (RadioButton) findViewById(R.id.radioTrad);
		leadRadioButton = (RadioButton) findViewById(R.id.radioLead);
		topropeRadioButton = (RadioButton) findViewById(R.id.radioTopRope);
		boulderRadioButton = (RadioButton) findViewById(R.id.radioBouldering);
		gradePicker = (NumberPicker) findViewById(R.id.gradePicker);
		saveButton = (Button) findViewById(R.id.saveclimb);
		
		inoutdoorsRadioGroup = (RadioGroup) findViewById(R.id.inoutdoorsradio);
		typeRadioGroup = (RadioGroup) findViewById(R.id.typeradio);

		// Disable keyboard/editing of grade picker.
		gradePicker
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		// Initialize the climbing grades
		climbingGrades.put("French", frenchGrades);
		climbingGrades.put("Font", fontGrades);

		// Set up database connection
		datasource = new ClimbDataSource(this);
		datasource.open();
		
		//initialize form from latest climb
		Climb latestClimb = datasource.getLatestClimb();
		if(latestClimb != null) {
			String type = latestClimb.getType();
			if(latestClimb.getInOutDoors().equals(getResources().getString(R.string.indoors))) {
				showTypeRadioGroupIndoors(type);
			} else {
				showTypeRadioGroupOutdoors(type);
			}
			
			showGradePicker(latestClimb.getGradesystem());
			gradePicker.setValue(Arrays.asList(climbingGrades.get(currentGradesystem)).indexOf(latestClimb.getGrade()));
		}
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
		switch (view.getId()) {
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
		outdoorsRadioButton.setChecked(true);
		tradRadioButton.setVisibility(View.VISIBLE);
		tradRadioButton.setChecked(true);
		showGradePickerTrad();
		typeContainer.setVisibility(View.VISIBLE);
	}

	private void showTypeRadioGroupOutdoors(String type) {
		showTypeRadioGroupOutdoors();
		if (type.equals(getResources().getString(R.string.trad))) {
			tradRadioButton.setChecked(true);
		} else if (type.equals(getResources().getString(R.string.bouldering))) {
			boulderRadioButton.setChecked(true);
		} else if (type.equals(getResources().getString(R.string.lead))) {
			leadRadioButton.setChecked(true);
		} else {
			topropeRadioButton.setChecked(true);
		}
	}

	private void showTypeRadioGroupIndoors() {
		indoorsRadioButton.setChecked(true);
		tradRadioButton.setVisibility(View.GONE);
		leadRadioButton.setChecked(true);
		showGradePickerLead();
		typeContainer.setVisibility(View.VISIBLE);
	}

	private void showTypeRadioGroupIndoors(String type) {
		showTypeRadioGroupIndoors();
		if (type.equals(getResources().getString(R.string.bouldering))) {
			boulderRadioButton.setChecked(true);
		} else if (type.equals(getResources().getString(R.string.lead))) {
			leadRadioButton.setChecked(true);
		} else {
			topropeRadioButton.setChecked(true);
		}
	}

	
	private void showGradePickerBouldering() {
		showGradePicker("Font");
	}

	private void showGradePickerTrad() {
		showGradePicker("French");
	}

	private void showGradePickerTopRope() {
		showGradePicker("French");
	}

	private void showGradePickerLead() {
		showGradePicker("French");
	}

	private void showGradePicker(String gradeSystem) {
		currentGradesystem = gradeSystem;
		String[] usedGrades = climbingGrades.get(gradeSystem);
		gradePicker.setDisplayedValues(null);
		gradePicker.setMaxValue(usedGrades.length - 1);
		gradePicker.setMinValue(0);
		gradePicker.setDisplayedValues(usedGrades);
		gradeContainer.setVisibility(View.VISIBLE);
		saveButton.setVisibility(View.VISIBLE);
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

	public void saveClimb(View view) {
		RadioButton inout = (RadioButton) findViewById(inoutdoorsRadioGroup
				.getCheckedRadioButtonId());
		RadioButton type = (RadioButton) findViewById(typeRadioGroup.getCheckedRadioButtonId());
		int grade = gradePicker.getValue();
		Long timestamp = System.currentTimeMillis() / 1000L;

		datasource.createClimb(inout.getText().toString(),
				type.getText().toString(),
				climbingGrades.get(currentGradesystem)[grade],
				currentGradesystem, timestamp);
		NavUtils.navigateUpFromSameTask(this);
	}

}
