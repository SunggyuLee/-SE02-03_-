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
	Scanner scan2 = new Scanner(System.in);
	DAOGrade daog = DAOGrade.sharedInstance();
	DAOUser daou = DAOUser.sharedInstance();
	Grade gradee = new Grade();

	public void run() {
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.성적 등록  2.성적 수정  3.학생별 성적표 조회  4.종료 ");

			switch (chooseWork) {
			case 1: // 성적 등록
				if (endGrading)
					System.out.println("> 성적 처리가 마감되었습니다.");
				else {
					System.out.println("> 성적 등록을 시작합니다.");
					this.giveGrade();
				}
				break;

			case 2: // 성적 수정
				if (endGrading)
					System.out.println("> 성적 처리가 마감되었습니다.");
				else {
					System.out.println("> 성적 수정을 시작합니다.");
					this.fixGrade();
				}
				break;

			case 3: // 학생별 성적표 조회
				System.out.println("> 학생별 성적표 조회를 시작합니다.");
				this.checkReport();
				break;

			case 4: // 종료
				System.out.println("> 종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

	private void checkReport() { // 학생별 성적표 조회
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- 학번 목록 --------------------------");
		int count = 1;
		for (User u : u_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getUserId(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String UserId = this.inputString("> 성적표를 조회할 학생의 학번 : ");
		gradee.setUserId(UserId);

		List<Grade> list2 = daog.getStudentReport(gradee);

		if (list2.size() == 0) {
			System.out.println("해당 학생의 성적표가 존재하지 않습니다. 다시 확인해주세요.");
			this.checkReport();
		}

		System.out.println("-------------------------- 성적표 --------------------------");
		int count2 = 1;
		for (Grade u : list2) {
			if (u.getGrade() == 0.0) {
				System.out.println(count2 + "| 처리 상태 : 미등록 | 학수 번호 : " + u.getClassIdNum() + " | 학점 : " + u.getGrade());
			} else {
				System.out.println(count2 + "| 처리 상태 : 등록 | 학수 번호 : " + u.getClassIdNum() + " | 학점 : " + u.getGrade());
			}
			count2++;
		}

	}

	private void fixGrade() { // 성적 수정
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- 학번 목록 --------------------------");
		int count = 1;
		for (User u : u_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getUserId(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String UserId = this.inputString("> 성적을 수정할 학생의 학번 : ");
		gradee.setUserId(UserId);

		List<Grade> list2 = daog.getStudentReport(gradee);

		if (list2.size() == 0) {
			System.out.println("해당 학생의 성적표가 존재하지 않습니다. 다시 확인해주세요.");
			this.fixGrade();
		}

		System.out.println("------------------------ 처리된 성적 목록 ------------------------");
		int count2 = 1;
		for (Grade u : list2) {
			if (u.getGrade() != 0.0) {
				System.out.println(count2 + "| 처리 상태 : 등록 | 학수 번호 : " + u.getClassIdNum() + " | 학점 : " + u.getGrade());
			}
			count2++;
		}
		System.out.println();

		String classIdNum = this.inputString("> 성적을 수정할 학수 번호 (ex.algorithm-1) : ");
		Float newGrade = this.inputFloat("> 성적 (ex.4.0) : ");

		if (!(newGrade > 0 && newGrade <= 4.5)) {
			System.out.println("> 성적은 1.0 이상 4.5 이하 이여야 합니다.");
			this.fixGrade();
		}

		gradee.setClassIdNum(classIdNum);
		gradee.setGrade(newGrade);

		boolean r = daog.modifyGrade(gradee);

		if (r) {
			System.out.println("> 성적 수정이 완료되었습니다.");
		} else
			System.out.println("> 성적 수정을 실패하였습니다.");

	}

	private void giveGrade() { // 성적 처리
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- 학번 목록 --------------------------");
		int count = 1;
		for (User u : u_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getUserId(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String UserId = this.inputString("> 성적을 등록할 학생의 학번 : ");
		gradee.setUserId(UserId);

		List<Grade> list2 = daog.getStudentReport(gradee);

		System.out.println("-------------------------- 성적 목록 --------------------------");
		int count2 = 1;
		for (Grade u : list2) {
			if (u.getGrade() == 0.0) {
				System.out.println(count2 + "| 처리 상태 : 미등록 | 학수 번호 : " + u.getClassIdNum() + " | 학점 : " + u.getGrade());
			} else {
				System.out.println(count2 + "| 처리 상태 : 등록 | 학수 번호 : " + u.getClassIdNum() + " | 학점 : " + u.getGrade());
			}
			count2++;
		}
		System.out.println();

		String classIdNum = this.inputString("> 성적을 등록할 학수 번호 (ex.algorithm-1) : ");
		Float newGrade = this.inputFloat("> 성적 (ex.4.0) : ");

		if (!(newGrade > 0 && newGrade <= 4.5)) {
			System.out.println("> 성적은 1.0 이상 4.5 이하 이여야 합니다.");
			this.giveGrade();
		}

		gradee.setClassIdNum(classIdNum);
		gradee.setGrade(newGrade);

		boolean r = daog.registerGrade(gradee);

		if (r) {
			System.out.println("> 성적 등록이 완료되었습니다.");
		} else
			System.out.println("> 성적 등록을 실패하였습니다.");

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
		return Float.parseFloat(scan2.nextLine());
	}
}