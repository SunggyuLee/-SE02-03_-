package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.DAOSubject;
import Database.Grade;
import Database.Subject;

public class ManageAttending {
	String classIdNum;
	String userId;
	Float grade;
	Scanner scan = new Scanner(System.in);
	DAOSubject daos = DAOSubject.sharedInstance();
	DAOGrade daog = DAOGrade.sharedInstance();
	
	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1. �������� ����  2.���ǽð�ǥ ��ȸ  3.���ǰ�ȹ�� ��ȸ  4.������û  5.�������  7.���� ");

			switch (chooseWork) {
			case 1: // ���� ���� ���
				System.out.println("���� ������ �����մϴ�.");
				new ManageSubject().run();
				break;

			case 2: // ���ǽð�ǥ ��ȸ
				System.out.println("���ǽð�ǥ�� ��ȸ�մϴ�.");
				this.inquiryTimetable();
				break;
				
			case 3: // ���ǰ�ȹ�� ��ȸ
				System.out.println("���ǰ�ȹ���� ��ȸ�մϴ�.");
				this.inquirySyllabus();
				break;
				
			case 4: // ������û
				System.out.println("���� ��û�մϴ�.");
				this.attendClass();
				break;
				
			case 5: // �������
				System.out.println("���� ����մϴ�.");
				this.deleteClass();
				break;
				
			case 7: // ����
				System.out.println("�����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}
	
	private void deleteClass() {
	}

	private void attendClass() {
	}

	private void inquirySyllabus() {
		// �м���ȣ�� ��ȸ�ؾ� �Ѵ�. �ֳ��ϸ� ��������δ� �ٸ� ���ǰ�ȹ���� ������ ���� �ִ�.
		
	}

	private void inquiryTimetable() {
		List<Subject> list = daos.getSubjectList();
		
		for(Subject u : list) {
			System.out.println(u);
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
