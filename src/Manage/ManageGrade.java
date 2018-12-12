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
	DAOGrade daog = DAOGrade.sharedInstance();
	Grade gradee = new Grade();

	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���� ���  2.���� ����  3.�л��� ����ǥ ��ȸ  4.���� ");

			switch (chooseWork) {
			case 1: // ���� ���
				if(endGrading)
					System.out.println("����ó���� �̹� �������ϴ�.");
				else {
					System.out.println("���� ����� �����մϴ�.");
					this.giveGrade();
				}
				break;

			case 2: // ���� ����
				if(endGrading)
					System.out.println("����ó���� �̹� �������ϴ�.");
				else {
					System.out.println("���� ������ �����մϴ�.");
					this.fixGrade();
				}
				break;
				
			case 3: // �л��� ����ǥ ��ȸ
				System.out.println("�л��� ����ǥ ��ȸ�� �����մϴ�.");
				this.checkReport();
				break;
				
			case 4: // ����
				System.out.println("�����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}

	private void checkReport() { //�л��� ����ǥ ��ȸ
		// �л��� �й��� �Է¹ް� �л��� ��� ������ �����ش�
		DAOUser daou = DAOUser.sharedInstance();
		List<User> list = daou.getStudentList();
		for(User u : list) {
			System.out.println("�й� : " + u.getUserId());
		}
		String checkUserId = inputString("����ǥ�� ��ȸ�� �л��� �й� : ");
		gradee.setUserId(checkUserId);
		List<Grade> list2 = daog.getStudentReport(gradee);
		if (list2.size() == 0) {
			System.out.println("��ȸ�� ����Ʈ�� �����ϴ�.");
		}
		else {
			for (Grade u:list2) {
				System.out.println(u);
			}
		}
	}

	private void fixGrade() {
		// �м���ȣ�� �й� �Է��ϰ� ��������
		// ������ �����ϸ� ���� �����ϰ�
		String UserId = inputString("������ ������ �л��� �й� : ");
		gradee.setUserId(UserId);
		List<Grade> list2 = daog.getStudentReport(gradee);
		for (Grade u:list2) {
			System.out.println("�й� : " + u.getUserId() + ", �м���ȣ : "+ u.getClassIdNum() + ", �й� : "+u.getGrade());
		}
		String classIdNum = inputString("������ ������ �м� ��ȣ : ");
		Float newGrade = inputFloat("���� : ");
		
		gradee.setClassIdNum(classIdNum);
		gradee.setGrade(newGrade);
		
		boolean r = daog.modifyGrade(gradee);
        
		list2 = daog.getStudentReport(gradee);
		for (Grade u:list2) {
			System.out.println(u);
		}
		
        if (r) {
            System.out.println("���� ������ �Ϸ�Ǿ����ϴ�.");
        }
        else
            System.out.println("���� ������ �����Ͽ����ϴ�.");
	}

	private void giveGrade() {
		// �м���ȣ�� �й� �Է��ϰ� �������
		// ������ �����ϸ� ��� �Ұ��ϰ�
		String UserId = inputString("������ ����� �л��� �й� : ");
		gradee.setUserId(UserId);
		List<Grade> list2 = daog.getStudentReport(gradee);
		for (Grade u:list2) {
			System.out.println("�й� : " + u.getUserId() + ", �м���ȣ : "+ u.getClassIdNum() + ", �й� : "+u.getGrade());
		}
		String classIdNum = inputString("������ ����� �м� ��ȣ : ");
		System.out.print("���� : ");
		Float newGrade = scan.nextFloat();
		
		gradee.setClassIdNum(classIdNum);
		gradee.setGrade(newGrade);
		
		boolean r = daog.registerGrade(gradee);
        
		list2 = daog.getStudentReport(gradee);
		for (Grade u:list2) {
			System.out.println(u);
		}
		
        if (r) {
            System.out.println("���� ����� �Ϸ�Ǿ����ϴ�.");
        }
        else
            System.out.println("���� ����� �����Ͽ����ϴ�.");
		
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
		return Float.parseFloat(scan.nextLine());
	}
}