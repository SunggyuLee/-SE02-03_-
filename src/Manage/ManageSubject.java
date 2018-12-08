package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOSubject;
import Database.Subject;

public class ManageSubject {
	String subjectName;
	String profName;
	Integer credit;
	Scanner scan = new Scanner(System.in);
	DAOSubject daos = DAOSubject.sharedInstance();
	Subject subject = new Subject();
	
	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���� ���� ���  2.���� ���� ����  3.���� ���� ����  7.���� ");

			switch (chooseWork) {
			case 1: // ���� ���� ���
				System.out.println("���� ������ ����մϴ�.");
				this.enrollSubject();
				break;

			case 2: // ���� ���� ����
				System.out.println("���� ������ �����մϴ�.");
				this.modifySubject();
				break;
				
			case 3: // ���� ���� ����
				System.out.println("���� ������ �����մϴ�.");
				this.deleteSubject();
				break;
				
			case 7: // ����
				System.out.println("�����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}
	
	private void deleteSubject() {
		List<Subject> list = daos.getSubjectList();
		
		for(Subject u : list) {
			System.out.println(u);
		}
	}
	
	private void modifySubject() {
		List<Subject> list = daos.getSubjectList();
		
		for(Subject u : list) {
			System.out.println(u);
		}
	}

	private void enrollSubject() {
		
		boolean r = daos.InsertSubject(subject);
		
		if(r)
			System.out.println("���������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("���������� �����Ͽ����ϴ�.");

		// ����� �� Ȯ�� �� select �غ�
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
