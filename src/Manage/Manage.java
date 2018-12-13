package Manage;

import java.util.Scanner;

public class Manage {
	Scanner scan = new Scanner(System.in);

	public void run() {
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���� ����  2.���� ����  3.���� ����  4.���� ����  5.���� ����  6.�α׾ƿ� ");

			switch (chooseWork) {
			case 1: // ���� ����
				System.out.println("> ���� ������ �����մϴ�.");
				new ManageRegister().run();
				break;

			case 2: // ���� ����
				System.out.println("> ���� ������ �����մϴ�.");
				new ManageAllSubject().run();
				break;

			case 3: // ���� ����
				System.out.println("> ���� ������ �����մϴ�.");
				new ManageAttending().run();
				break;

			case 4: // ���� ����
				System.out.println("> ���� ������ �����մϴ�.");
				new ManageGrade().run();
				break;

			case 5: // ���� ����
				System.out.println("> ���� ������ �����մϴ�.");
				new ManageScholar().run();
				break;

			case 6: // �α׾ƿ�
				System.out.println("> �����մϴ�.");
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
