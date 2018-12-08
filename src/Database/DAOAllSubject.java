package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOAllSubject {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/se_term_1?autoReconnect=true&useSSL=false";
	static final String USERNAME = "root";
	static final String PASSWORD = "admin";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch(ClassNotFoundException e) {
			System.out.println("Ŭ���� �ε� ���� : "+e.getMessage());
		}
	}
	
	private DAOAllSubject() {}
	private static DAOAllSubject obj;
	
	public static DAOAllSubject sharedInstance() {
		if(obj == null) {
			obj = new DAOAllSubject();
		}
		return obj;
	}
	// �����ͺ��̽� ������ �ʿ��� �������� ����
	Connection conn;
	
	// SQL ���࿡ �ʿ��� ����
	Statement stmt; 
	
	// select ������ �������� �� ����� ������ ����
	private ResultSet rs;
	
	private boolean connect() {
		boolean result = false;
		
		try{
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			System.out.println("\n- MySQL Connection");
			result = true;
		} catch (Exception e) {
			System.out.println("���� ���� : " +e.getMessage());
		}
		return result;
	}
	
	private void close() {
		try {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		} catch( Exception e) {
			System.out.println("���� ���� : " + e.getMessage());
		}
	}
	
	public List<AllSubject> getAllSubjectList() { // select
		List<AllSubject> list = null;
		String sql = "SELECT * FROM allsubject";
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql);
					
					list = new ArrayList<AllSubject>();
					
					while(rs.next()) {
						AllSubject allSubject = new AllSubject();
						
						allSubject.setClassIdNum(rs.getString("classIdNum"));
						allSubject.setsubjectName(rs.getString("subjectName"));
						allSubject.setClassIdNum(rs.getString("classNum"));
						allSubject.setClassTime(rs.getString("classTime"));
						allSubject.setClassRoom(rs.getString("classRoom"));
						allSubject.setAvailNum(rs.getInt("availNum"));
						
						list.add(allSubject);
					}
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			// ���ῡ �������� �� �۾�
			System.out.println("�����ͺ��̽� ���ῡ �����߽��ϴ�.");
			System.exit(0);
		}
		return list;
	}
	
	public boolean InsertAllSubject(AllSubject allSubject) {
		boolean result = false;
		
		if(this.connect()) {
			try {
				String sql = "INSERT INTO allsubject VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, allSubject.getClassIdNum());
				pstmt.setString(2, allSubject.getsubjectName());
				pstmt.setString(3, allSubject.getClassNum());
				pstmt.setString(4, allSubject.getClassTime());
				pstmt.setString(5, allSubject.getClassRoom());
				pstmt.setString(6, allSubject.getSyllabus());
				pstmt.setInt(7, allSubject.getAvailNum());
				
				int r = pstmt.executeUpdate();
				
				if(r>0) {
					result = true;
				}
				// �����ͺ��̽� ���� ��ü ����
				pstmt.close();
				this.close();
			} catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("�����ͺ��̽� ���ῡ ����");
			System.exit(0);
		}
		return result;
	}
}
