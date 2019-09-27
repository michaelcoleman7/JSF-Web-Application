package com.student.Controllers;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import com.mysql.jdbc.CommunicationsException;
import com.student.DAOs.CourseDAO;
import com.student.Models.Course;

@ManagedBean
@SessionScoped
public class CourseController {	
	private CourseDAO dao;
	ArrayList<Course> cs;
	
	//constructor
	public CourseController() throws Exception {
		super();
		dao = new CourseDAO();
	}
	
	//getters/setters
	public ArrayList<Course> getCs() {
		return cs;
	}
	
	//load courses , called from CourseDAO
	public void loadCourses() throws SQLException{
		cs = dao.loadCourses();
	}
	
	//add course to mysql database
	public String addCourse(Course c) throws SQLException{
		try
		{
			dao.addCourse(c);//addcourse method from CourseDAO
			return "manage_courses.xhtml";//return to this page
		}
		//catch exceptions
		catch(SQLIntegrityConstraintViolationException e){
			//if duplicate courseID
			FacesMessage message = new FacesMessage("Error: Course ID "+c.getcID()+" Already Exists");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		catch(CommunicationsException e)
		{
			//if database offline
			FacesMessage message = new FacesMessage("Error: Cannot Connect to database");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}
	
	//delete course from mysql
	public String deleteCourse(Course c) throws SQLException{
		try {
			dao.deleteCourse(c);//delete course from CourseDAO
			return "manage_courses.xhtml";
		}
		catch(SQLIntegrityConstraintViolationException e) {
			//if course has links with students
			FacesMessage message = new FacesMessage("Error: Cannot Delete Course "+c.getcID()+", as there are associated Students");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}

}
