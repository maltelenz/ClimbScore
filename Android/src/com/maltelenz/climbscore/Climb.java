package com.maltelenz.climbscore;

public class Climb {
	private long id;
	private String inoutdoors;
	private String type;
	private String grade;
	private String gradesystem;
	private String timestamp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInOutDoors() {
		return inoutdoors;
	}

	public void setInoutdoors(String inoutdoors) {
		this.inoutdoors = inoutdoors;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGradesystem() {
		return gradesystem;
	}

	public void setGradesystem(String gradesystem) {
		this.gradesystem = gradesystem;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return this.timestamp + ": " + this.inoutdoors + " - " + this.type + " - " + this.grade + " (" + this.gradesystem + ")";
	}
}