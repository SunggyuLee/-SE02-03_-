package Manage;

import java.util.Scanner;

public class Manage {
	Scanner scan = new Scanner(System.in);

	public void run() {
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.학적 관리  2.교과 관리  3.수강 관리  4.성적 관리  5.장학 관리  6.로그아웃 ");

			switch (chooseWork) {
			case 1: // 학적 관리
				System.out.println("> 학적 관리를 시작합니다.");
				new ManageRegister().run();
				break;

			case 2: // 교과 관리
				System.out.println("> 교과 관리를 시작합니다.");
				new ManageAllSubject().run();
				break;

			case 3: // 수강 관리
				System.out.println("> 수강 관리를 시작합니다.");
				new ManageAttending().run();
				break;

			case 4: // 성적 관리
				System.out.println("> 성적 관리를 시작합니다.");
				new ManageGrade().run();
				break;

			case 5: // 장학 관리
				System.out.println("> 장학 관리를 시작합니다.");
				new ManageScholar().run();
				break;

			case 6: // 로그아웃
				System.out.println("> 종료합니다.");
				run = false;
				break;
			default:
			}
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
