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
		
		String subjectName = this.inputString("������ �й� : ");
		
		allSubject.setSubjectName(subjectName);
		
		boolean r = daoa.deleteAllSubject(allSubject);
		
		if(r)
			System.out.println("����������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("����������� �����Ͽ����ϴ�.");
	}
	
	private void modifyAllSubject() {
		List<AllSubject> list = daoa.getAllSubjectList();
		//ó���� ��ü�� �ƴ� ������ ����Ʈ �߰�
		for(AllSubject u : list) {
			System.out.println("������ : " + u.getSubjectName());
		}
		
		String subjectName = this.inputString("������ ����� : ");
		String profName = this.inputString("��米�� : ");
		Integer credit = this.inputInt("���� : ");
		
		allSubject.setSubjectName(subjectName);
		allSubject.setProfName(profName);
		allSubject.setCredit(credit);
		
		boolean r = daoa.modifyAllSubject(allSubject);
		
		if(r)
			System.out.println("����������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("����������� �����Ͽ����ϴ�.");
		
		list = daoa.getAllSubjectList();
		
		for(AllSubject u : list) {
			System.out.println(u);
		}
	}
	
	private void enrollAllSubject() {
		//ó���� ��ϵ� ����Ʈ �߰�
		List<AllSubject> allSubjectList = daoa.getAllSubjectList();
		for (AllSubject a:
			 allSubjectList) {
			System.out.println("����� : " + a.getSubjectName());
		}

		String subjectName = this.inputString("����� : ");
		String profName = this.inputString("��米�� : ");
		Integer credit = this.inputInt("���� : ");

		allSubject.setSubjectName(subjectName);
		allSubject.setProfName(profName);
		allSubject.setCredit(credit);
		
		boolean r = daoa.InsertAllSubject(allSubject);
		
		if(r)
			System.out.println("������ ����� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("������ ����� �����Ͽ����ϴ�.");

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