package main;

import java.util.Scanner;

import Manage.Manage;

public class LogIn {
	Scanner scan = new Scanner(System.in);

	public void run() {
		String id = this.inputString("> ���̵� : ");
		String pw = this.inputString("> ��й�ȣ : ");

		// id�� �������̸� �����ڷ� ����
		if (id.equals("root") && pw.equals("admin")) {
			System.out.println("> �����ڷ� �α��� �մϴ�. ȯ���մϴ�.");
			new Manage().run();
		} else {
			System.out.println("�����ڰ� �ƴϸ� �α��� �� �� �����ϴ�. ���α׷� ����� �� �����ڷ� �α������ּ���.");
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
