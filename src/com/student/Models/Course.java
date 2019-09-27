package com.student.Models;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Course {
	String cID;
	String cName;
	int duration;
	
	public Course() {
		super();
	}

	public Course(String cID, String cName, int duration) {
		super();
		this.cID = cID;
		this.cName = cName;
		this.duration = duration;
	}

	public String getcID() {
		return cID;
	}

	public void setcID(String cID) {
		this.cID = cID;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
