package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.DAOUser;
import Database.User;

public class ManageScholar {
	static int scholarStudentNum = 10; // ���б� ���� �ο�
	DAOUser daou = DAOUser.sharedInstance();
	DAOGrade daog = DAOGrade.sharedInstance();
	User user = new User();
	Scanner scan = new Scanner(System.in);

	public void run() {
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���л� ��ȸ  2.���м��� �л� �� ����  3.���� ");

			switch (chooseWork) {
			case 1: // ���л� ��ȸ
				System.out.println("> ���л��� ��ȸ�մϴ�. ���л��� ������ �л� ��(" + scholarStudentNum + "��) ��ŭ ���� �˴ϴ�.");
				if (this.gradingEnded()) { // ��� �л��� ���� ó���� �����Ǿ� ���� ��
					System.out.println("> ���� ���б� ���� ��� �л��Դϴ�.");
					this.inquiryScholarshipStudent();

				} else { // ��� �л��� ���� ó���� �������� �ʾ��� ��
					System.out.println("> ��� �л����� ����ó���� �������� ���� ������ ���б� ���� ��� �л��Դϴ�.");
					this.inquiryScholarshipStudent();
				}

				break;

			case 2: // ���л� ������ ����
				System.out.println("> ���л� ���� �л� ���� �����մϴ�.");
				this.scholarStudentNum = this.inputInt("> ���л� ���� �л� �� : ");
				System.out.println("> ���л� ���� �л� �� ������ �Ϸ�Ǿ����ϴ�.");
				break;

			case 3: // ����
				System.out.println("> �����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}

	private void inquiryScholarshipStudent() { // �������� �����Ͽ� ���� �л� ����ŭ ���
		List<String> list = daog.getScholarList(scholarStudentNum);

		System.out.println("-------------------------- ��� --------------------------");
		int count = 1;
		for (String u : list) {
			String temp[] = u.split("#");
			String uId = temp[0];
			String uName = temp[1];
			String uAvg = temp[2];

			System.out.println(count + "| �й� : " + uId + " | �̸� : " + uName + " | ��� ���� : " + uAvg);
			count++;
		}

		// �ٽ��ѹ� ���̺��� Ȯ���ؼ� ���� ������ �� �ȵǾ��ִٸ� ���������� �������� �ʾҴٰ� �˷��ְ�.
		// �����Ǿ����� ���� ���� �Ϸ�Ǿ��ٰ� ����Ѵ�.
	}

	private boolean gradingEnded() {
		if (!new ManageGrade().endGrading) { 		// ����ó�� ������ ���� �ʾ��� ��
			boolean f = daog.checkGradeFinish(); 	// ���� ������ �ϳ��� �ȵȰ� �ִٸ� false ��ȯ
			if (f) { 								// ��� �л��� ���� ������ �� ������ ��
				System.out.println("> ������ ��� ó�� �Ǿ����ϴ�.");
				String yesOrNo = this.inputString("> ���� ó���� �����Ͻðڽ��ϱ�? (ó�� : Y/N) ");
				if (yesOrNo.equals("Y"))
					new ManageGrade().endGrading = true;
			}
		}
		if (!new ManageGrade().endGrading)
			System.out.println("> ���� ó���� �������� �ʾҽ��ϴ�. ���� ó�� �Ϸ� �� ������ �ؾ��մϴ�.");
		return new ManageGrade().endGrading;
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
