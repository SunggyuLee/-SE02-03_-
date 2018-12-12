package Manage;

import java.util.List;
import java.util.Scanner;

import Database.AllSubject;
import Database.DAOAllSubject;
import Database.User;

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
				System.out.println("> ������ ����� �����մϴ�.");
				this.enrollAllSubject();
				break;

			case 2: // ������ ����
				System.out.println("> ������ ������ �����մϴ�.");
				this.modifyAllSubject();
				break;
				
			case 3: // ������ ����
				System.out.println("> ������ ������ �����մϴ�.");
				this.deleteAllSubject();
				break;
				
			case 7: // ����
				System.out.println("> �����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}
	
	private void deleteAllSubject() {
		List<AllSubject> as_list = daoa.getAllSubjectList();
		
		System.out.println("-------------------------- ������ ��� --------------------------");
		int count = 1;
		for (AllSubject u : as_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "]" + u.getSubjectName(), " "));
			count++;
		}
		System.out.println();
		System.out.println();
		
		String subjectName = this.inputString("> ������ ������ : ");
		
		allSubject.setSubjectName(subjectName);
		
		boolean r = daoa.deleteAllSubject(allSubject);
		
		if(r)
			System.out.println("> ������ ������ �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("> ������ ������ �����Ͽ����ϴ�.");
	}
	
	private void modifyAllSubject() {
		List<AllSubject> as_list = daoa.getAllSubjectList();
		
		System.out.println("-------------------------- ������ ��� --------------------------");
		int count = 1;
		for (AllSubject u : as_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "]" + u.getSubjectName(), " "));
			count++;
		}
		System.out.println();
		System.out.println();
		
		String subjectName = this.inputString("> ������ ����� (ex.algorithm) : ");
		String profName = this.inputString("> ��米�� (ex.kimseongje) : ");
		Integer credit = this.inputInt("> ���� (ex.3) : ");
		
		allSubject.setSubjectName(subjectName);
		allSubject.setProfName(profName);
		allSubject.setCredit(credit);
		
		boolean r = daoa.modifyAllSubject(allSubject);
		
		if(r)
			System.out.println("> ������ ������ �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("> ������ ������ �����Ͽ����ϴ�.");
		
	}
	
	private void enrollAllSubject() {
		//ó���� ��ϵ� ����Ʈ �߰�
		List<AllSubject> as_list = daoa.getAllSubjectList();
		
		System.out.println("-------------------------- ������ ��� --------------------------");
		int count = 1;
		for (AllSubject u : as_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "]" + u.getSubjectName(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String subjectName = this.inputString("> ����� (ex.algorithm) : ");
		String profName = this.inputString("> ��米�� (ex.kimseongje) : ");
		Integer credit = this.inputInt("> ���� (ex.3) : ");

		allSubject.setSubjectName(subjectName);
		allSubject.setProfName(profName);
		allSubject.setCredit(credit);
		
		boolean r = daoa.InsertAllSubject(allSubject);
		
		if(r)
			System.out.println("> ������ ����� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("> ������ ����� �����Ͽ����ϴ�.");

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