package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.student.Models.Course;

public class CourseDAO {
	private DataSource mysqlDS;
	
	//Constructor
	public CourseDAO() throws Exception {
		//setup db access routes etc..
	     Context context = new InitialContext();
	     String jndiName = "java:comp/env/jdbc/collegeDB";
	     mysqlDS = (DataSource) context.lookup(jndiName);
	}
	
	//load courses
	public ArrayList<Course> loadCourses() throws SQLException{
		//create connection to DB
		Connection conn = mysqlDS.getConnection();
		Statement myStmt = conn.createStatement();
		String query = "select * from course";
		ResultSet rs = myStmt.executeQuery(query);
		ArrayList<Course> courses = new ArrayList<Course>();
		
		while( rs.next() ) {
			//add each value received to local variables
			String cid = rs.getString("cID");
			String name = rs.getString("cName");
			int duration = rs.getInt("duration");
			//create new course using variables
			Course c = new Course(cid,name,duration);
			courses.add(c);//add course to array
		}
		return courses;//return array
	}
	//add course
	public void addCourse(Course c) throws SQLException{
		Connection conn = mysqlDS.getConnection();
		//prepared statement to get query based on user input
		PreparedStatement myStmt = conn.prepareStatement("Insert into course(cID,cName,duration)values(?,?,?);");
		//set ? values to user inputs in query
		myStmt.setString(1, c.getcID());
		myStmt.setString(2, c.getcName());
		myStmt.setInt(3, c.getDuration());
		//execute query, inserting course into database
		myStmt.executeUpdate();
	}
	
	//delete course
	public void deleteCourse(Course c) throws SQLException{
		Connection conn = mysqlDS.getConnection();
		//prepared statement-delete course from database
		PreparedStatement myStmt = conn.prepareStatement("delete from course where cID like ?;");
		myStmt.setString(1, c.getcID());
		myStmt.executeUpdate();
	}
}
