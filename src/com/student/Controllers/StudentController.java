package com.student.Controllers;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

import org.neo4j.driver.v1.exceptions.ClientException;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

import com.mysql.jdbc.CommunicationsException;
import com.student.DAOs.CourseDAO;
import com.student.DAOs.Neo4jDAO;
import com.student.DAOs.StudentDAO;
import com.student.Models.Course;
import com.student.Models.Student;

@ManagedBean
@SessionScoped
public class StudentController {
	private StudentDAO dao;
	private Neo4jDAO neo4jDAO;
	ArrayList<Student> ss;
	ArrayList<Student> courseStudent;

	//Constructor
	public StudentController() throws Exception {
		super();
		dao = new StudentDAO();
		neo4jDAO = new Neo4jDAO();
	}

	//getter/setters
	public ArrayList<Student> getSs() {
		return ss;
	}
	
	//load students from StudentDAO
	public void loadStudents() throws SQLException{
		ss = dao.loadStudents();
	}
	
	//Add student to MySQL and Neo4j
	public String addStudent(Student s) throws SQLException{
		try
		{
			dao.addStudent(s);//add to mysql from studentDAO
			try {
				neo4jDAO.createNode(s.getName(), s.getAddress());//add node to neo4j from Neo4jDAO
				
			}
			catch(ServiceUnavailableException e) {
				FacesMessage message = new FacesMessage("Warning: Student"+s.getName()+" has not been added to Neo4j Database as it is offline");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
			return "manage_students.xhtml";
		}
		catch(SQLIntegrityConstraintViolationException e){
			//if duplicate entry
			if(e.getErrorCode() == 1062) {
				FacesMessage message = new FacesMessage("Error: "+e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
			//if Course doesn't exist in MYSQL database
			else if(e.getErrorCode() == 1452) {
				FacesMessage message = new FacesMessage("Error: Course "+s.getcID()+" Does not Exist");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
			if(e.getErrorCode() == 0) {
				FacesMessage message = new FacesMessage("Error: cannot connect to MYSQL Database");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
			return null;
		}
		catch(SQLException e){
			//if database offline
			if(e.getErrorCode() == 0) {
				FacesMessage message = new FacesMessage("Error: cannot connect to MYSQL Database");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
			return null;
		}
	}
	//Delete student from MYSQL and Neo4j
	public String deleteStudent(Student s) throws SQLException{
		dao.deleteStudent(s);
		try {
			neo4jDAO.deleteNode(s.getName());
		}			
		catch(ClientException e) {
			FacesMessage message = new FacesMessage("Error: Student "+s.getName()+" cant delete from neo4j as he/she it has relationships");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		catch(ServiceUnavailableException e) {
			FacesMessage message = new FacesMessage("Error: Student "+s.getName()+" cannot be deleted from Neo4j, as it's offline");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;	
		}
		return "manage_students.xhtml";
	}
}
