package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOGrade {
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

	private DAOGrade() {
	}

	private static DAOGrade obj;

	public static DAOGrade sharedInstance() {
		if (obj == null) {
			obj = new DAOGrade();
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

	public List<Grade> getGradeList() { // select
		List<Grade> list = null;
		String sql = "SELECT * FROM grade";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					list = new ArrayList<Grade>();

					while (rs.next()) {
						Grade grade = new Grade();

						grade.setClassIdNum(rs.getString("classIdNum"));
						grade.setUserId(rs.getString("userId"));
						grade.setGrade(rs.getFloat("grade"));

						list.add(grade);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// 연결에 실패했을 때 작업
			System.out.println("데이터베이스 연결에 실패했습니다.");
			System.exit(0);
		}
		return list;
	}

	public boolean InsertGrade(Grade grade) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "INSERT INTO grade VALUES (?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, grade.getClassIdNum());
				pstmt.setString(2, grade.getUserId());
				pstmt.setFloat(3, (float) 0);

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				// 데이터베이스 생성 객체 해제
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}

	public List<Grade> getUserGradeList(String newAttenduserId) {
		List<Grade> list = null;
		String sql = "SELECT * FROM grade WHERE userId ='" + newAttenduserId + "'";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					list = new ArrayList<Grade>();

					while (rs.next()) {
						Grade grade = new Grade();

						grade.setClassIdNum(rs.getString("classIdNum"));
						grade.setUserId(rs.getString("userId"));
						grade.setGrade(rs.getFloat("grade"));

						list.add(grade);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// 연결에 실패했을 때 작업
			System.out.println("데이터베이스 연결에 실패했습니다.");
			System.exit(0);
		}
		return list;
	}

	public boolean deleteGrade(Grade grade) {
		boolean result = false;
		if (this.connect()) {
			try {
				String sql = "DELETE from grade WHERE classIdNum='" + grade.getClassIdNum() + "' and userID='"
						+ grade.getUserId() + "'";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int r = pstmt.executeUpdate();
				if (r > 0) {
					result = true;
				}
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}

	public List<Grade> getStudentReport(Grade grade) { // 학생별 성적표 조회
		List<Grade> list = null;

		String sql = "SELECT * FROM grade where userId='" + grade.getUserId() + "'";
		if (connect()) {
			try {
				stmt = conn.createStatement();

				if (stmt != null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<>();

					while (rs.next()) {
						Grade grade1 = new Grade();
						grade1.setClassIdNum(rs.getString("classIdNum"));
						grade1.setGrade(rs.getFloat("grade"));
						grade1.setUserId(grade.getUserId());
						list.add(grade1);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("데이터베이스 연결에 실패했습니다.");
			System.exit(0);
		}
		return list;
	}

	public boolean registerGrade(Grade grade) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "UPDATE grade set grade=" + grade.getGrade() + " " + "WHERE classIdNum ='"
						+ grade.getClassIdNum() + "' and userId='" + grade.getUserId() + "'";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				// 데이터베이스 생성 객체 해제
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}

	public boolean modifyGrade(Grade grade) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "UPDATE grade set grade=" + grade.getGrade() + " " + "WHERE classIdNum ='"
						+ grade.getClassIdNum() + "' and userId='" + grade.getUserId() + "'";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				// 데이터베이스 생성 객체 해제
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("데이터베이스 연결에 실패");
			System.exit(0);
		}
		return result;
	}

	// 장학생 선발시 수행, 지정된 수만큼의 학생의 ( 학생 id + 이름 + 평균 학점 ) 등수 순으로 반환
	public List<String> getScholarList(int number) { // 장학생 리스트 출력
		List<String> list = null;
		String sql = "SELECT userId, name, avgGrade FROM user ORDER BY avgGrade DESC limit " + number;
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);
					list = new ArrayList<>();
					while (rs.next()) {
						String temp = "";
						temp = rs.getString(1) + "#" + rs.getString(2) + "#" + rs.getString(3);
						list.add(temp); // 학번#이름#평균 학점
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("데이터베이스 연결에 실패했습니다.");
			System.exit(0);
		}
		return list;
	}

	public boolean checkGradeFinish() { // 성적 기입이 끝났는지 여부를 반환하는 함수
		boolean result = false;

		String sql = "SELECT count(*) FROM grade WHERE grade = 0";
		if (this.connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					rs.next();
					if (rs.getInt(1) >= 1) { // 기입이 하나라도 끝난게 있다면 false
						result = false;
					} else { // 모두 기입이 되었다면 true
						result = true;
					}
					this.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("데이터베이스 연결에 실패하였습니다.");
			System.exit(0);
		}
		return result;
	}

	public List<String> getUserList() { // 학생의 아이디를 그룹별로 출력함
		List<String> list = null;
		String sql = "SELECT * FROM grade GROUP BY userId";

		if (this.connect()) {

			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					list = new ArrayList<String>();

					while (rs.next()) {
						String temp = "";
						temp = rs.getString("userId");
						list.add(temp);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("데이터베이스 연결에 실패했습니다.");
			System.exit(0);
		}
		return list;
	}

}
