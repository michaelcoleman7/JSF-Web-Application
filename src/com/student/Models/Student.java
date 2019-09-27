package com.student.Models;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Student {
	String sid;
	String cID;
	String name;
	String address;
	public Student() {
		super();
	}
	public Student(String sid, String cID, String name, String address) {
		super();
		this.sid = sid;
		this.cID = cID;
		this.name = name;
		this.address = address;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getcID() {
		return cID;
	}
	public void setcID(String cID) {
		this.cID = cID;
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
