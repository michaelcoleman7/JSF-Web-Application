package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import com.student.Models.Student;

public class StudentDAO {
	private DataSource mysqlDS;
	
	//Constructor
	public StudentDAO() throws Exception{
	     Context context = new InitialContext();
	     String jndiName = "java:comp/env/jdbc/collegeDB";
	     mysqlDS = (DataSource) context.lookup(jndiName);
	}
	
	//Load students from database
	public ArrayList<Student> loadStudents() throws SQLException{
		//connect to database
		Connection conn = mysqlDS.getConnection();
		Statement myStmt = conn.createStatement();
		String query = "select * from student";
		ResultSet rs = myStmt.executeQuery(query);
		ArrayList<Student> students = new ArrayList<Student>();
		
		while( rs.next() ) {
			//store query results to local variables
			String sid = rs.getString("sid");
			String cid = rs.getString("cID");
			String name = rs.getString("name");
			String address = rs.getString("address");
			//create new student using local variables
			Student s = new Student(sid,cid,name,address);
			students.add(s);//add student to array
		}
		return students;//return students array
	}
	
	//Add student to MYSQL database
	public void addStudent(Student s) throws SQLException{
		Connection conn = mysqlDS.getConnection();
		//prepared statement to get query based on user input
		PreparedStatement myStmt = conn.prepareStatement("Insert into student(sid,cID,name,address)values(?,?,?,?);");
		//set ? values to user inputs in query
		myStmt.setString(1, s.getSid());
		myStmt.setString(2, s.getcID());
		myStmt.setString(3, s.getName());
		myStmt.setString(4, s.getAddress());
		myStmt.executeUpdate();
	}
	
	//delete student from MYSQL database
	public void deleteStudent(Student s) throws SQLException{
		Connection conn = mysqlDS.getConnection();
		PreparedStatement myStmt = conn.prepareStatement("delete from student where sid like ?;");
		//set ? values to deleted users student id
		myStmt.setString(1, s.getSid());
		myStmt.executeUpdate();
	}
}
