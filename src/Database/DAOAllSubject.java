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
	static final String PASSWORD = "201402408";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("클래스 로드 실패 : " + e.getMessage());
		}
	}

	private DAOAllSubject() {
	}

	private static DAOAllSubject obj;

	public static DAOAllSubject sharedInstance() {
		if (obj == null) {
			obj = new DAOAllSubject();
		}
		return obj;
	}

	// 데이터베이스 연동에 필요한 변수들을 선언
	Connection conn;

	// SQL 실행에 필요한 변수
	Statement stmt;

	// select 구문을 수행했을 때 결과를 저장할 변수
	private ResultSet rs;

	private boolean connect() {
		boolean result = false;

		try {
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			result = true;
		} catch (Exception e) {
			System.out.println("연결 실패 : " + e.getMessage());
		}
		return result;
	}

	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			System.out.println("해제 실패 : " + e.getMessage());
		}
	}

	public List<AllSubject> getAllSubjectList() { // select
		List<AllSubject> list = null;
		String sql = "SELECT * FROM allsubject";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					list = new ArrayList<AllSubject>();

					while (rs.next()) {
						AllSubject allSubject = new AllSubject();

						allSubject.setSubjectName(rs.getString("subjectName"));
						allSubject.setProfName(rs.getString("profName"));
						allSubject.setCredit(rs.getInt("credit"));

						list.add(allSubject);
					}
				}
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		} else {
			System.out.println("데이터베이스 연결에 실패했습니다.");
			System.exit(0);
		}
		return list;
	}

	public boolean InsertAllSubject(AllSubject allSubject) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "INSERT INTO allsubject VALUES (?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, allSubject.getSubjectName());
				pstmt.setString(2, allSubject.getProfName());
				pstmt.setInt(3, allSubject.getCredit());

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				//System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}

	public boolean modifyAllSubject(AllSubject allSubject) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "UPDATE allsubject set profName='" + allSubject.getProfName() + "', credit='"
						+ allSubject.getCredit() + "' " + "WHERE subjectName ='" + allSubject.getSubjectName() + "'";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				//System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}

	public boolean deleteAllSubject(AllSubject allSubject) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "DELETE from allSubject WHERE subjectName='" + allSubject.getSubjectName() + "'";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				//System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}
}
