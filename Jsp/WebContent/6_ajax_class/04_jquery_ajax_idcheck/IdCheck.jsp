<%@ page contentType="text/xml; charset=utf-8" %>
<%@ page language="java" import="java.sql.*"%>

<%
String driver="oracle.jdbc.driver.OracleDriver";
String user="lsh";
String pass="lsh";
String dbURL="jdbc:oracle:thin:@192.168.0.244:1521:orcl";


	Class.forName(driver);
	Connection connection=DriverManager.getConnection(dbURL,user,pass);
	
	String sql = "select * from membertest where id='" + request.getParameter("userid")+"'";
	System.out.println(sql);
	Statement stmt = connection.createStatement();
	ResultSet rs = stmt.executeQuery(sql);		

	String result="NO"; // 초기값 노 
	if (rs.next()){		
		result = "YES"; // 해당 id 있으면 예스
	}		
	out.print(result);
%>

