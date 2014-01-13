package com.maltelenz.climbscore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ClimbDataSource {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	// Database fields
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_INOUT, MySQLiteHelper.COLUMN_TYPE,
			MySQLiteHelper.COLUMN_GRADE, MySQLiteHelper.COLUMN_GRADE_SYSTEM,
			MySQLiteHelper.COLUMN_TIMESTAMP };
	
	private DateFormat dateFormat;

	public ClimbDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
		
		dateFormat = DateFormat.getDateTimeInstance();
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Climb createClimb(String inout, String type, String grade,
			String gradesystem, Long timestamp) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_INOUT, inout);
		values.put(MySQLiteHelper.COLUMN_TYPE, type);
		values.put(MySQLiteHelper.COLUMN_GRADE, grade);
		values.put(MySQLiteHelper.COLUMN_GRADE_SYSTEM, gradesystem);
		values.put(MySQLiteHelper.COLUMN_TIMESTAMP, timestamp);

		long insertId = database.insert(MySQLiteHelper.TABLE_CLIMBS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_CLIMBS, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		Climb newClimb = cursorToClimb(cursor);
		cursor.close();
		return newClimb;
	}

	public void deleteClimb(Climb climb) {
		long id = climb.getId();
		// System.out.println("Comment deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_CLIMBS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Climb> getAllClimbs() {
		List<Climb> climbs = new ArrayList<Climb>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_CLIMBS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Climb climb = cursorToClimb(cursor);
			climbs.add(climb);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return climbs;
	}

	private Climb cursorToClimb(Cursor cursor) {
		Climb climb = new Climb();
		climb.setId(cursor.getLong(0));
		climb.setInoutdoors(cursor.getString(1));
		climb.setType(cursor.getString(2));
		climb.setGrade(cursor.getString(3));
		climb.setGradesystem(cursor.getString(4));
		climb.setTimestamp(dateFormat.format(new Date(cursor.getLong(5) * 1000)));
		return climb;
	}
}