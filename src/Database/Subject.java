package Database;

import java.io.Serializable;

public class Subject implements Serializable {
	private String classIdNum;
	private String subjectName;
	private String classNum;
	private String classTime;
	private String classRoom;
	private String syllabus;
	private Integer availNum = 0;

	public Subject() {
		super();
	}

	public Subject(String subjectName) {
		super();
		this.subjectName = subjectName;
	}

	public String getClassIdNum() {
		return classIdNum;
	}

	public void setClassIdNum(String classIdNum) {
		this.classIdNum = classIdNum;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getClassTime() {
		return classTime;
	}

	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}

	public String getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public Integer getAvailNum() {
		return availNum;
	}

	public void setAvailNum(Integer availNum) {
		this.availNum = availNum;
	}

	@Override
	public String toString() {
		return "classIdNum : " + classIdNum + " subjectName : " + subjectName + "" + " classNum : " + classNum
				+ " classTime : " + classTime + " classRoom : " + classRoom + " syllabus : " + syllabus + " availNum : "
				+ availNum;
	}

}
