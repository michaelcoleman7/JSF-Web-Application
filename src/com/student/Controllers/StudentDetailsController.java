package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.student.DAOs.StudentDetailsDAO;
import com.student.Models.StudentDetails;


@ManagedBean
@SessionScoped
public class StudentDetailsController {
	private StudentDetailsDAO dao;
	ArrayList<StudentDetails> studentDetailsArray;
	String courseID;

	//Constructor
	public StudentDetailsController() throws Exception {
		super();
		dao = new StudentDetailsDAO();
	}
	
	//getters/setters
	public ArrayList<StudentDetails> getStudentDetailsArray() {
		return studentDetailsArray;
	}

	public void setStudentDetailsArray(ArrayList<StudentDetails> studentDetailsArray) {
		this.studentDetailsArray = studentDetailsArray;
	}
	
	//Load Course students
	public String loadStudentDetails(String courseID) throws SQLException{
		studentDetailsArray = dao.loadStudentDetails(courseID);//load course specific students using course id from StudentDetailsDAO
		return "full_student_details.xhtml";
	}
}
