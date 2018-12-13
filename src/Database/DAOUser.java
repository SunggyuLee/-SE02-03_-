package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOUser {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/se_term_1?autoReconnect=true&useSSL=false";
	static final String USERNAME = "root";
	static final String PASSWORD = "201402408";

	static {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Ŭ���� �ε� ���� : " + e.getMessage());
		}
	}

	private DAOUser() {
	}

	private static DAOUser obj;

	public static DAOUser sharedInstance() {
		if (obj == null) {
			obj = new DAOUser();
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

		try {
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			result = true;
		} catch (Exception e) {
			System.out.println("���� ���� : " + e.getMessage());
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
			System.out.println("���� ���� : " + e.getMessage());
		}
	}

	public List<User> getUserList() { // select
		List<User> list = null;
		String sql = "SELECT * FROM user";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					list = new ArrayList<User>();

					while (rs.next()) {
						User user = new User();

						user.setUserId((rs.getString("userId")));
						user.setPwd((rs.getString("pwd")));
						user.setName((rs.getString("name")));
						user.setBirth((rs.getString("birth")));
						user.setAddr((rs.getString("addr")));
						user.setPhoneNum((rs.getString("phoneNum")));
						user.setavgGrade(rs.getFloat("avgGrade"));

						list.add(user);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// ���ῡ �������� �� �۾�
			System.out.println("�����ͺ��̽� ���ῡ �����߽��ϴ�.");
			System.exit(0);
		}
		return list;
	}

	public boolean InsertUser(User user) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, user.getUserId());
				pstmt.setString(2, user.getPwd());
				pstmt.setString(3, user.getName());
				if ((user.getBirth()).length() != 8) { // ��������� 8�ڸ��� �ƴϸ� false
					pstmt.close();
					this.close();
					return false;
				}
				pstmt.setString(4, user.getBirth());
				pstmt.setString(5, user.getAddr());
				pstmt.setString(6, user.getPhoneNum());
				pstmt.setFloat(7, user.getavgGrade());

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				// �����ͺ��̽� ���� ��ü ����
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("�����ͺ��̽� ���ῡ ����");
			System.exit(0);
		}
		return result;
	}

	public List<User> inquiryStudent(String userId) {
		List<User> list = null;
		String sql = "SELECT * FROM user WHERE userId='" + userId + "'";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					list = new ArrayList<User>();

					while (rs.next()) {
						User user = new User();

						user.setUserId((rs.getString("userId")));
						user.setPwd((rs.getString("pwd")));
						user.setName((rs.getString("name")));
						user.setBirth((rs.getString("birth")));
						user.setAddr((rs.getString("addr")));
						user.setPhoneNum((rs.getString("phoneNum")));
						user.setavgGrade(rs.getFloat("avgGrade"));

						list.add(user);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// ���ῡ �������� �� �۾�
			System.out.println("�����ͺ��̽� ���ῡ �����߽��ϴ�.");
			System.exit(0);
		}
		return list;
	}

	public boolean modifyUser(User user) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "UPDATE user set pwd='" + user.getPwd() + "', name='" + user.getName() + "', " + "birth ='"
						+ user.getBirth() + "', addr = '" + user.getAddr() + "', phoneNum = '" + user.getPhoneNum()
						+ "' " + "WHERE userId ='" + user.getUserId() + "'";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				// �����ͺ��̽� ���� ��ü ����
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("�����ͺ��̽� ���ῡ ����");
			System.exit(0);
		}
		return result;
	}

	public boolean deleteUser(User user) {
		boolean result = false;

		if (this.connect()) {
			try {
				String sql = "DELETE from user WHERE userId='" + user.getUserId() + "'";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				int r = pstmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				// �����ͺ��̽� ���� ��ü ����
				pstmt.close();
				this.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("�����ͺ��̽� ���ῡ ����");
			System.exit(0);
		}
		return result;
	}

	public List<User> getScholarshipStudent(int scholarPercent, String userId) {
		List<User> list = null;
		String sql = "SELECT userId, name, avgGrade FROM user WHERE userId='" + userId
				+ "' order by 3 desc limit count(*)/" + scholarPercent;
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					list = new ArrayList<User>();

					while (rs.next()) {
						User user = new User();

						user.setUserId((rs.getString("userId")));
						user.setPwd((rs.getString("pwd")));
						user.setName((rs.getString("name")));
						user.setBirth((rs.getString("birth")));
						user.setAddr((rs.getString("addr")));
						user.setPhoneNum((rs.getString("phoneNum")));
						user.setavgGrade(rs.getFloat("avgGrade"));

						list.add(user);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// ���ῡ �������� �� �۾�
			System.out.println("�����ͺ��̽� ���ῡ �����߽��ϴ�.");
			System.exit(0);
		}
		return list;
	}

	public List<User> getStudentList() {
		List<User> list = null;
		String sql = "SELECT userId FROM user";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);

					list = new ArrayList<User>();

					while (rs.next()) {
						User user = new User();

						user.setUserId((rs.getString("userId")));

						list.add(user);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// ���ῡ �������� �� �۾�
			System.out.println("�����ͺ��̽� ���ῡ �����߽��ϴ�.");
			System.exit(0);
		}
		return list;
	}
}
