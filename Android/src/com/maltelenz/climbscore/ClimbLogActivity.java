package com.maltelenz.climbscore;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ClimbLogActivity extends ListActivity {

	private ClimbDataSource datasource;
	private ListView listView;
	private List<Climb> allClimbs;
	private ArrayAdapter<Climb> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_climb_log);
		// Show the Up button in the action bar.
		setupActionBar();

		// Set up database connection
		datasource = new ClimbDataSource(this);
		datasource.open();

		allClimbs = datasource.getAllClimbs();

		adapter = new ArrayAdapter<Climb>(this, android.R.layout.simple_list_item_multiple_choice, allClimbs);
		setListAdapter(adapter);

		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

		listView.setMultiChoiceModeListener(new LogMultipleChoiceListener(this));

	}

	protected void deleteSelectedItems() {

		SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
		Log.i("ClimbLogActivity", "Selected item for deletion: " + checkedItems);
		List<Climb> toRemoveList = new ArrayList<Climb>();
		for (int i = 0; i < checkedItems.size(); i++) {
			if (checkedItems.valueAt(i)) {
				Climb toRemove = allClimbs.get(checkedItems.keyAt(i));
				toRemoveList.add(toRemove);
			}
		}

		for (Climb c : toRemoveList) {
			Log.i("ClimbLogActivity", "Asking for deletion of climb: " + c.toString());
			datasource.deleteClimb(c);
			adapter.remove(c);
		}

		Context context = getApplicationContext();
		CharSequence text = "Deleted " + checkedItems.size() + " items.";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		adapter.notifyDataSetChanged();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.climb_log, menu);
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

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		l.setItemChecked(position, true);
	}
}
