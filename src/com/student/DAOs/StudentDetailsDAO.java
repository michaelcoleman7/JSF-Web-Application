package com.student.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.student.Models.StudentDetails;

public class StudentDetailsDAO {
	private DataSource mysqlDS;

	//Constructor
	public StudentDetailsDAO() throws Exception {
	     Context context = new InitialContext();
	     String jndiName = "java:comp/env/jdbc/collegeDB";
	     mysqlDS = (DataSource) context.lookup(jndiName);
	   }
	
	//Load all student details
	public ArrayList<StudentDetails> loadStudentDetails(String studentID) throws SQLException{
		Connection conn = mysqlDS.getConnection();
		//join where student course courseID == student courseID
		PreparedStatement myStmt = conn.prepareStatement("select s.sid,s.name,c.cID,c.CName,c.duration from course c join student s on(c.cID =s.cID) where s.sid = ?");
		myStmt.setString(1, studentID);
		ResultSet rs = myStmt.executeQuery();
		ArrayList<StudentDetails> studentsDetails = new ArrayList<StudentDetails>();
		
		while( rs.next()) {
			//add results to local variables
			String sid = rs.getString("sid");
			String name = rs.getString("name");
			String cid = rs.getString("cID");
			String cname = rs.getString("cName");
			int duration = rs.getInt("duration");
			
			//add local variables to new studentDetails object
			StudentDetails sd = new StudentDetails(sid,name,cid,cname,duration);
			studentsDetails.add(sd);//add to studentdetails array
		}
		return studentsDetails;//return studentdetails array
	}
}
