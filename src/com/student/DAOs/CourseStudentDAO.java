package com.student.DAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.PreparedStatement;

import com.student.Models.CourseStudent;

public class CourseStudentDAO {
	private DataSource mysqlDS;

	//Constructor
	public CourseStudentDAO() throws Exception {
	     Context context = new InitialContext();
	     String jndiName = "java:comp/env/jdbc/collegeDB";
	     mysqlDS = (DataSource) context.lookup(jndiName);
	   }
	//load course students via courseID
	public ArrayList<CourseStudent> loadCourseStudents(String courseId) throws SQLException{
		Connection conn = mysqlDS.getConnection();
		//join query to combine both student and course results to add to CourseStudent object
		PreparedStatement myStmt = conn.prepareStatement("select c.cID,c.CName,c.duration,s.name,s.address from course c join student s on(c.cID =s.cID) where c.cID = ?");
		myStmt.setString(1, courseId);
		ResultSet rs = myStmt.executeQuery();
		ArrayList<CourseStudent> courseStudents = new ArrayList<CourseStudent>();
		
		while( rs.next()) {
			//store values to local variables
			String cid = rs.getString("cID");
			String cname = rs.getString("cName");
			int duration = rs.getInt("duration");
			String name = rs.getString("name");
			String address = rs.getString("address");
			//create CourseStudent and add values to it
			CourseStudent c = new CourseStudent(cid,cname,duration,name,address);
			courseStudents.add(c);//add to array
		}
		return courseStudents;//return array
	}
}
