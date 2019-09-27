package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.student.DAOs.CourseStudentDAO;
import com.student.DAOs.StudentDAO;
import com.student.Models.CourseStudent;

@ManagedBean
@SessionScoped
public class CourseStudentController {
	private CourseStudentDAO dao;
	ArrayList<CourseStudent> courseStudentArray;
	String courseID;
	
	//Constructor
	public CourseStudentController() throws Exception {
		super();
		dao = new CourseStudentDAO();
	}
	
	//getters/setters
	public ArrayList<CourseStudent> getCourseStudentArray() {
		return courseStudentArray;
	}

	public void setCourseStudentArray(ArrayList<CourseStudent> courseStudentArray) {
		this.courseStudentArray = courseStudentArray;
	}
	
	//load course students
	public String loadCourseStudents(String courseID) throws SQLException{
		courseStudentArray = dao.loadCourseStudents(courseID);//called from DAO class
		return "course_student_details.xhtml";
	}	
}
