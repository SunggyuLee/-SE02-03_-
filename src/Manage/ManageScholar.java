package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOUser;
import Database.User;

public class ManageScholar {
	static int scholarPercent = 10;
	DAOUser daou = DAOUser.sharedInstance();
	User user = new User();
	Scanner scan = new Scanner(System.in);
	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���л� ��ȸ  2.���� ������ ����  7.���� ");

			switch (chooseWork) {
			case 1: // ���л� ��ȸ
				// percent ��ŭ ���л����μ� ���
				System.out.println("���л��� ��ȸ�մϴ�.");
				if(this.gradingEnded()) // ����ó���� �����Ǿ����� Ȯ��
					this.inquiryScholarshipStudent();
				break;

			case 2: // ���л� ������ ����
				System.out.println("���л��� �������� �����մϴ�.");
				this.scholarPercent = this.inputInt("���л� ���޷� : ");
				System.out.println("���л� �������� �����Ͽ����ϴ�.");
				break;
				
			case 7: // ����
				System.out.println("�����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}
	
	private void inquiryScholarshipStudent() {
		// �������� �����Ͽ� ��������ŭ ���
		List<User> list = daou.getUserList();
		
		for(User u : list) {
			System.out.println(u);
		}
		String userId = this.inputString("��ȸ�� �й� : ");
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
