package Manage;

import java.util.List;
import java.util.Scanner;

import Database.AllSubject;
import Database.DAOAllSubject;

public class ManageAllSubject {
	String subjectName;
	String profName;
	Integer credit;
	Scanner scan = new Scanner(System.in);
	DAOAllSubject daoa = DAOAllSubject.sharedInstance();
	AllSubject allSubject = new AllSubject();
	
	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.������ ���  2.������ ����  3.������ ����  7.���� ");

			switch (chooseWork) {
			case 1: // ������ ���
				System.out.println("������ ����� �����մϴ�.");
				this.enrollAllSubject();
				break;

			case 2: // ������ ����
				System.out.println("������ ������ �����մϴ�.");
				this.modifyAllSubject();
				break;
				
			case 3: // ������ ����
				System.out.println("������ ������ �����մϴ�.");
				this.deleteAllSubject();
				break;
				
			case 7: // ����
				System.out.println("�����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}
	
	private void deleteAllSubject() {
		List<AllSubject> list = daoa.getAllSubjectList();
		
		for(AllSubject u : list) {
			System.out.println(u);
		}
	}
	
	private void modifyAllSubject() {
		List<AllSubject> list = daoa.getAllSubjectList();
		
		for(AllSubject u : list) {
			System.out.println(u);
		}
	}
	
	private void enrollAllSubject() {
		boolean r = daoa.InsertAllSubject(allSubject);
		
		if(r)
			System.out.println("���� ���� ����� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("���� ���� ����� �����Ͽ����ϴ�.");

		// ����� �� Ȯ�� �� select �غ�
		List<AllSubject> list = daoa.getAllSubjectList();
		
		for(AllSubject u : list) {
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