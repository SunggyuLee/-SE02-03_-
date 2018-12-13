package Database;

import java.io.Serializable;

public class AllSubject implements Serializable {
	private String subjectName;
	private String profName;
	private Integer credit;

	public AllSubject() {
		super();
	}

	public AllSubject(String subjectName) {
		super();
		this.subjectName = subjectName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getProfName() {
		return profName;
	}

	public void setProfName(String profName) {
		this.profName = profName;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		return "subjectName : " + subjectName + " profName : " + profName + " credit : " + credit;
	}
}
