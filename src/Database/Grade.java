package Database;

import java.io.Serializable;

public class Grade implements Serializable {
	private String classIdNum;
	private String userId;
	private Float grade = null;

	public Grade() {
		super();
	}

	public String getClassIdNum() {
		return classIdNum;
	}

	public void setClassIdNum(String classIdNum) {
		this.classIdNum = classIdNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Float getGrade() {
		return grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "classIdNum : " + classIdNum + " userId : " + userId + " grade : " + grade;
	}
}
