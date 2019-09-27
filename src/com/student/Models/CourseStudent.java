package com.student.Models;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class CourseStudent {
	String cID;
	String cName;
	int duration;
	String name;
	String address;
	
	public CourseStudent() {
		super();
	}

	public CourseStudent(String cID, String cName, int duration, String name, String address) {
		super();
		this.cID = cID;
		this.cName = cName;
		this.duration = duration;
		this.name = name;
		this.address = address;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
