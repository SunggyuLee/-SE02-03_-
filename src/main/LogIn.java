package main;

import java.util.Scanner;

import Manage.Manage;

public class LogIn {
	Scanner scan = new Scanner(System.in);

	public void run() {
		String id = this.inputString("> 아이디 : ");
		String pw = this.inputString("> 비밀번호 : ");

		// id가 관리자이면 관리자로 실행
		if (id.equals("root") && pw.equals("admin")) {
			System.out.println("> 관리자로 로그인 합니다. 환영합니다.");
			new Manage().run();
		} else {
			System.out.println("관리자가 아니면 로그인 할 수 없습니다. 프로그램 재실행 후 관리자로 로그인해주세요.");
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
