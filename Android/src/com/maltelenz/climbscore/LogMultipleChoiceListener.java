package com.maltelenz.climbscore;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;

public final class LogMultipleChoiceListener implements
		MultiChoiceModeListener {
	/**
	 * 
	 */
	private final ClimbLogActivity climbLogActivity;

	/**
	 * @param climbLogActivity
	 */
	LogMultipleChoiceListener(ClimbLogActivity climbLogActivity) {
		this.climbLogActivity = climbLogActivity;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		mode.setTitle("Selected: "
				+ Integer.toString(this.climbLogActivity.getListView().getCheckedItemCount()));
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// Respond to clicks on the actions in the CAB
		switch (item.getItemId()) {
		case R.id.action_delete:
			this.climbLogActivity.deleteSelectedItems();
			mode.finish(); // Action picked, so close the CAB
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// Inflate the menu for the CAB
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.climb_log_action, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// Here you can perform updates to the CAB due to
		// an invalidate() request
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {

	}
}