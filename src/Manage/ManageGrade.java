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
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���� ���  2.���� ����  3.�л��� ����ǥ ��ȸ  4.���� ");

			switch (chooseWork) {
			case 1: // ���� ���
				if (endGrading)
					System.out.println("> ���� ó���� �����Ǿ����ϴ�.");
				else {
					System.out.println("> ���� ����� �����մϴ�.");
					this.giveGrade();
				}
				break;

			case 2: // ���� ����
				if (endGrading)
					System.out.println("> ���� ó���� �����Ǿ����ϴ�.");
				else {
					System.out.println("> ���� ������ �����մϴ�.");
					this.fixGrade();
				}
				break;

			case 3: // �л��� ����ǥ ��ȸ
				System.out.println("> �л��� ����ǥ ��ȸ�� �����մϴ�.");
				this.checkReport();
				break;

			case 4: // ����
				System.out.println("> �����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}

	private void checkReport() { // �л��� ����ǥ ��ȸ
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- �й� ��� --------------------------");
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

		String UserId = this.inputString("> ����ǥ�� ��ȸ�� �л��� �й� : ");
		gradee.setUserId(UserId);

		List<Grade> list2 = daog.getStudentReport(gradee);

		if (list2.size() == 0) {
			System.out.println("�ش� �л��� ����ǥ�� �������� �ʽ��ϴ�. �ٽ� Ȯ�����ּ���.");
			this.checkReport();
		}

		System.out.println("-------------------------- ����ǥ --------------------------");
		int count2 = 1;
		for (Grade u : list2) {
			if (u.getGrade() == 0.0) {
				System.out.println(count2 + "| ó�� ���� : �̵�� | �м� ��ȣ : " + u.getClassIdNum() + " | ���� : " + u.getGrade());
			} else {
				System.out.println(count2 + "| ó�� ���� : ��� | �м� ��ȣ : " + u.getClassIdNum() + " | ���� : " + u.getGrade());
			}
			count2++;
		}

	}

	private void fixGrade() { // ���� ����
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- �й� ��� --------------------------");
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

		String UserId = this.inputString("> ������ ������ �л��� �й� : ");
		gradee.setUserId(UserId);

		List<Grade> list2 = daog.getStudentReport(gradee);

		if (list2.size() == 0) {
			System.out.println("�ش� �л��� ����ǥ�� �������� �ʽ��ϴ�. �ٽ� Ȯ�����ּ���.");
			this.fixGrade();
		}

		System.out.println("------------------------ ó���� ���� ��� ------------------------");
		int count2 = 1;
		for (Grade u : list2) {
			if (u.getGrade() != 0.0) {
				System.out.println(count2 + "| ó�� ���� : ��� | �м� ��ȣ : " + u.getClassIdNum() + " | ���� : " + u.getGrade());
			}
			count2++;
		}
		System.out.println();

		String classIdNum = this.inputString("> ������ ������ �м� ��ȣ (ex.algorithm-1) : ");
		Float newGrade = this.inputFloat("> ���� (ex.4.0) : ");

		if (!(newGrade > 0 && newGrade <= 4.5)) {
			System.out.println("> ������ 1.0 �̻� 4.5 ���� �̿��� �մϴ�.");
			this.fixGrade();
		}

		gradee.setClassIdNum(classIdNum);
		gradee.setGrade(newGrade);

		boolean r = daog.modifyGrade(gradee);

		if (r) {
			System.out.println("> ���� ������ �Ϸ�Ǿ����ϴ�.");
		} else
			System.out.println("> ���� ������ �����Ͽ����ϴ�.");

	}

	private void giveGrade() { // ���� ó��
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- �й� ��� --------------------------");
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

		String UserId = this.inputString("> ������ ����� �л��� �й� : ");
		gradee.setUserId(UserId);

		List<Grade> list2 = daog.getStudentReport(gradee);

		System.out.println("-------------------------- ���� ��� --------------------------");
		int count2 = 1;
		for (Grade u : list2) {
			if (u.getGrade() == 0.0) {
				System.out.println(count2 + "| ó�� ���� : �̵�� | �м� ��ȣ : " + u.getClassIdNum() + " | ���� : " + u.getGrade());
			} else {
				System.out.println(count2 + "| ó�� ���� : ��� | �м� ��ȣ : " + u.getClassIdNum() + " | ���� : " + u.getGrade());
			}
			count2++;
		}
		System.out.println();

		String classIdNum = this.inputString("> ������ ����� �м� ��ȣ (ex.algorithm-1) : ");
		Float newGrade = this.inputFloat("> ���� (ex.4.0) : ");

		if (!(newGrade > 0 && newGrade <= 4.5)) {
			System.out.println("> ������ 1.0 �̻� 4.5 ���� �̿��� �մϴ�.");
			this.giveGrade();
		}

		gradee.setClassIdNum(classIdNum);
		gradee.setGrade(newGrade);

		boolean r = daog.registerGrade(gradee);

		if (r) {
			System.out.println("> ���� ����� �Ϸ�Ǿ����ϴ�.");
		} else
			System.out.println("> ���� ����� �����Ͽ����ϴ�.");

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