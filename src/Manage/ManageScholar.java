package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.DAOUser;
import Database.User;

public class ManageScholar {
	int scholarStudentNum = 10;										// ���б� ���� �ο�
	DAOUser daou = DAOUser.sharedInstance();
	DAOGrade daog = DAOGrade.sharedInstance();
	User user = new User();
	Scanner scan = new Scanner(System.in);
	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���л� ��ȸ  2.���м��� �л� �� ����  3.���� ");

			switch (chooseWork) {
			case 1: // ���л� ��ȸ
				System.out.println("���л��� ��ȸ�մϴ�. ���л��� ������ �л� ��("+ scholarStudentNum +"��) ��ŭ ���� �˴ϴ�.");
				//if(this.gradingEnded()) // ����ó���� �����Ǿ����� Ȯ��
				this.inquiryScholarshipStudent();

				break;

			case 2: // ���л� ������ ����
				System.out.println("���л� ���� �л� ���� �����մϴ�.");
				this.scholarStudentNum = this.inputInt("> ���л� ���� �л� �� : ");
				System.out.println("���л� ���� �л� �� ���� �Ϸ�.");
				break;

			case 7: // ����
				System.out.println("�����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}

	private void inquiryScholarshipStudent() {	// �������� �����Ͽ� ���� �л� ����ŭ ���
		
		List<String> list = daog.getScholarList(scholarStudentNum);
		int count = 1;
		for(String u : list) {
			String temp[] = u.split("#");
			String uId = temp[0];
			String uName = temp[1];
			String uAvg = temp[2];

			System.out.println(count + ". �й� : " + uId + "    �̸� : " + uName + "    ��� ���� : "+uAvg);
			count++;
		}
	}

	private boolean gradingEnded() {
		if(!new ManageGrade().endGrading) // ����ó���� �̹� �����Ǿ�����
		{
			// �ּ��� ���� �ڵ� �̴� ���������� �ۼ��ϸ� �ȴ� ***
			// ������ null ������ Ȯ��
			// if ������ null�� ������ {
			System.out.println("������ ��� ���ԵǾ����� ����ó���� �������� �ʾҽ��ϴ�.");
			String yesOrNo = this.inputString("����ó���� �����Ͻðڽ��ϱ�? (ó�� : y ) ");
			if(yesOrNo.equals("y"))
				new ManageGrade().endGrading = true;
			//			} else
			//				System.out.println("������ ���Ե��� ���� ���� �����մϴ�.");
		}
		if(!new ManageGrade().endGrading)
			System.out.println("����ó���� �����Ǿ� ���� �ʽ��ϴ�.");
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
