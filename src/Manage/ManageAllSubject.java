package Manage;

import java.util.List;
import java.util.Scanner;

import Database.AllSubject;
import Database.DAOAllSubject;
import Database.User;

public class ManageAllSubject {
	String subjectName;
	String profName;
	Integer credit;
	Scanner scan = new Scanner(System.in);
	DAOAllSubject daoa = DAOAllSubject.sharedInstance();
	AllSubject allSubject = new AllSubject();
	
	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.교과목 등록  2.교과목 수정  3.교과목 삭제  7.종료 ");

			switch (chooseWork) {
			case 1: // 교과목 등록
				System.out.println("교과목 등록을 시작합니다.");
				this.enrollAllSubject();
				break;

			case 2: // 교과목 수정
				System.out.println("교과목 수정을 시작합니다.");
				this.modifyAllSubject();
				break;
				
			case 3: // 교과목 삭제
				System.out.println("교과목 삭제를 시작합니다.");
				this.deleteAllSubject();
				break;
				
			case 7: // 종료
				System.out.println("종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}
	
	private void deleteAllSubject() {
		List<AllSubject> list = daoa.getAllSubjectList();
		
		for(AllSubject u : list) {
			System.out.println(u);
		}
		
		String subjectName = this.inputString("삭제할 학번 : ");
		
		allSubject.setSubjectName(subjectName);
		
		boolean r = daoa.deleteAllSubject(allSubject);
		
		if(r)
			System.out.println("교과목삭제가 완료되었습니다.");
		else
			System.out.println("교과목삭제가 실패하였습니다.");
	}
	
	private void modifyAllSubject() {
		List<AllSubject> list = daoa.getAllSubjectList();
		//처음에 전체가 아닌 교과목만 리스트 뜨게
		for(AllSubject u : list) {
			System.out.println("교과목 : " + u.getSubjectName());
		}
		
		String subjectName = this.inputString("변동할 과목명 : ");
		String profName = this.inputString("담당교수 : ");
		Integer credit = this.inputInt("학점 : ");
		
		allSubject.setSubjectName(subjectName);
		allSubject.setProfName(profName);
		allSubject.setCredit(credit);
		
		boolean r = daoa.modifyAllSubject(allSubject);
		
		if(r)
			System.out.println("교과목수정이 완료되었습니다.");
		else
			System.out.println("교과목수정이 실패하였습니다.");
		
		list = daoa.getAllSubjectList();
		
		for(AllSubject u : list) {
			System.out.println(u);
		}
	}
	
	private void enrollAllSubject() {
		//처음에 등록된 리스트 뜨게
		List<AllSubject> allSubjectList = daoa.getAllSubjectList();
		for (AllSubject a:
			 allSubjectList) {
			System.out.println("과목명 : " + a.getSubjectName());
		}

		String subjectName = this.inputString("과목명 : ");
		String profName = this.inputString("담당교수 : ");
		Integer credit = this.inputInt("학점 : ");

		allSubject.setSubjectName(subjectName);
		allSubject.setProfName(profName);
		allSubject.setCredit(credit);
		
		boolean r = daoa.InsertAllSubject(allSubject);
		
		if(r)
			System.out.println("교과목 등록이 완료되었습니다.");
		else
			System.out.println("교과목 등록이 실패하였습니다.");

		List<AllSubject> list = daoa.getAllSubjectList();
		
		for(AllSubject u : list) {
			System.out.println(u);
		}
	}

	private int inputInt(String string) {
		System.out.print(string);
		return Integer.parseInt(scan.nextLine());
	}

	private String inputString(String string) {
		System.out.print(string);
		return scan.nextLine();
	}
}