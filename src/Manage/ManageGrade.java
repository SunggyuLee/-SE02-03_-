package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.DAOUser;
import Database.Grade;
import Database.User;

public class ManageGrade {
	public static boolean endGrading = false;
	String classIdNum;
	String userId;
	Float grade;
	Scanner scan = new Scanner(System.in);
	DAOGrade daog = DAOGrade.sharedInstance();
	Grade gradee = new Grade();

	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.성적 등록  2.성적 수정  3.학생별 성적표 조회  4.종료 ");

			switch (chooseWork) {
			case 1: // 성적 등록
				if(endGrading)
					System.out.println("성적처리가 이미 끝났습니다.");
				else {
					System.out.println("성적 등록을 시작합니다.");					// 성적 등록을 시작학기 전에 성적등록이 하나라도 되어있지 않은 학생들의 리스트를 출력한다.
					this.giveGrade();
				}
				break;

			case 2: // 성적 수정
				if(endGrading)
					System.out.println("성적처리가 이미 끝났습니다.");
				else {
					System.out.println("성적 수정을 시작합니다.");					// 수정 전 모든 학생의 리스트를 출력한다.
					this.fixGrade();
				}
				break;
				
			case 3: // 학생별 성적표 조회
				System.out.println("학생별 성적표 조회를 시작합니다.");
				this.checkReport();
				break;
				
			case 4: // 종료
				System.out.println("종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

	private void checkReport() { 									//학생별 성적표 조회
		DAOUser daou = DAOUser.sharedInstance();
		List<User> list = daou.getStudentList();
		for(User u : list) {
			System.out.println("학번 : " + u.getUserId());
		}
		String checkUserId = inputString("> 성적표를 조회할 학생의 학번 : ");
		gradee.setUserId(checkUserId);
		List<Grade> list2 = daog.getStudentReport(gradee);
		if (list2.size() == 0) {
			System.out.println("조회할 리스트가 없습니다.");
		}
		else {
			for (Grade u:list2) {
				System.out.println("학번 : " + u.getUserId() + ", 학수번호 : "+ u.getClassIdNum() + ", 학점 : "+u.getGrade());
			}
		}
	}

	private void fixGrade() {
		// 학수번호와 학번 입력하고 성적수정
		// 성적이 존재하면 수정 가능하게
		String UserId = inputString("> 성적을 수정할 학생의 학번 : ");
		gradee.setUserId(UserId);
		List<Grade> list2 = daog.getStudentReport(gradee);
		for (Grade u:list2) {
			System.out.println("학번 : " + u.getUserId() + ", 학수번호 : "+ u.getClassIdNum() + ", 학점 : "+u.getGrade());
		}
		String classIdNum = inputString("> 성적을 수정할 학수 번호 : ");
		Float newGrade = inputFloat("> 성적 : ");
		
		gradee.setClassIdNum(classIdNum);
		gradee.setGrade(newGrade);
		
		boolean r = daog.modifyGrade(gradee);
        
		list2 = daog.getStudentReport(gradee);
		for (Grade u:list2) {
			System.out.println("학번 : " + u.getUserId() + ", 학수번호 : "+ u.getClassIdNum() + ", 학점 : "+u.getGrade());
		}
		
        if (r) {
            System.out.println("성적 수정이 완료되었습니다.");
        }
        else
            System.out.println("성적 수정이 실패하였습니다.");
	}

	@SuppressWarnings("unchecked")
	private void giveGrade() {
		// 학수번호와 학번 입력하고 성적등록
		// 성적이 존재하면 등록 불가하게
		
		
		// 성적 등록 전 모든 모든 학생의 리스트를 출력한다.
		System.out.println("***모든 학생의 학번 출력***");
		List<String> userList = daog.getUserList();
		int count1 = 1;
		for(String s : userList) {
			System.out.println(count1 + ". "+ s);
			count1++;
		}
		
		
		
		String UserId = inputString("> 성적을 등록할 학생의 학번 : ");
		System.out.println("*** 학수번호에 해당하는 학점이 0이면 아직 등록되지 않은 상태입니다. ***");
		gradee.setUserId(UserId);
		List<Grade> list2 = daog.getStudentReport(gradee);
		for (Grade u:list2) {
			System.out.println("학번 : " + u.getUserId() + ", 학수번호 : " + u.getClassIdNum() + ", 학점 : " + u.getGrade());
		}
		String classIdNum = inputString("> 성적을 등록할 학수 번호 : ");
		System.out.print("> 성적 : ");
		Float newGrade = scan.nextFloat();							// 성적 입력받음
		
		gradee.setClassIdNum(classIdNum);
		gradee.setGrade(newGrade);
		
		boolean r = daog.registerGrade(gradee);						// grade객체의 성적을 등록
        
		list2 = daog.getStudentReport(gradee);						// 변경된 해당 학생의 성적표 출력
		System.out.println("해당 학생의 변경된 성적표를 출력합니다.");
		for (Grade u:list2) {
			System.out.println("학번 : " + u.getUserId() +", 학수번호 : " + u.getClassIdNum() + ", 학점 : " + u.getGrade());
		}
		
        if (r) {
            System.out.println("성적 등록이 완료되었습니다.");
        }
        else
            System.out.println("성적 등록이 실패하였습니다.");
		
	}

	private int inputInt(String string) {
		System.out.print(string);
		return Integer.parseInt(scan.nextLine());
	}

	private String inputString(String string) {
		System.out.print(string);
		return scan.nextLine();
	}
	
	private Float inputFloat(String string) {
		System.out.print(string);
		return Float.parseFloat(scan.nextLine());
	}
}