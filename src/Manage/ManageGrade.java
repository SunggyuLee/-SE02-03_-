package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.Grade;

public class ManageGrade {
	public static boolean endGrading = false;
	String classIdNum;
	String userId;
	Float grade;
	Scanner scan = new Scanner(System.in);
	DAOGrade daog = DAOGrade.sharedInstance();

	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.성적 등록  2.성적 수정  3.학생별 성적표 조회  7.종료 ");

			switch (chooseWork) {
			case 1: // 성적 등록
				if(endGrading)
					System.out.println("성적처리가 이미 끝났습니다.");
				else {
					System.out.println("성적 등록을 시작합니다.");
					this.giveGrade();
				}
				break;

			case 2: // 성적 수정
				if(endGrading)
					System.out.println("성적처리가 이미 끝났습니다.");
				else {
					System.out.println("성적 수정을 시작합니다.");
					this.fixGrade();
				}
				break;
				
			case 3: // 학생별 성적표 조회
				System.out.println("학생별 성적표 조회를 시작합니다.");
				this.checkReport();
				break;
				
			case 7: // 종료
				System.out.println("종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

	private void checkReport() { //학생별 성적표 조회
		// 학생의 학번을 입력받고 학생의 모든 성적을 보여준다
		String checkUserId = inputString("성적표를 조회할 학생의 학번 : ");
		Grade grade = new Grade();
		grade.setUserId(checkUserId);
		List<Grade> list = daog.getStudentReport(grade);
		if (list.size() == 0) {
			System.out.println("조회할 리스트가 없습니다.");
		}
		else {
			for (Grade u:
					list) {
				System.out.println(u);
			}
		}
	}

	private void fixGrade() {
		// 학수번호와 학번 입력하고 성적수정
		// 성적이 존재하면 수정 가능하게
	}

	private void giveGrade() {
		// 학수번호와 학번 입력하고 성적등록
		// 성적이 존재하면 등록 불가하게
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