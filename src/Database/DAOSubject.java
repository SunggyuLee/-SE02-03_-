package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOSubject {
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
	
	private DAOSubject() {}
	private static DAOSubject obj;
	
	public static DAOSubject sharedInstance() {
		if(obj == null) {
			obj = new DAOSubject();
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
	
	public List<Subject> getSubjectList() { // select
		List<Subject> list = null;
		String sql = "SELECT * FROM subject";
		if(connect()) {
			try {
				stmt = conn.createStatement();
				if(stmt != null) {
					rs = stmt.executeQuery(sql);
					
					list = new ArrayList<Subject>();
					
					while(rs.next()) {
						Subject subject = new Subject();
						subject.setSubjectName(rs.getString("subjectName"));
						subject.setProfName(rs.getString("profName"));
						subject.setCredit(rs.getInt("credit"));
						
						list.add(subject);
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
	
	public boolean InsertSubject(Subject subject) {
		boolean result = false;
		
		if(this.connect()) {
			try {
				String sql = "INSERT INTO subject VALUES (?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, subject.getSubjectName());
				pstmt.setString(2, subject.getProfName());
				pstmt.setInt(3, subject.getCredit());
				
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
