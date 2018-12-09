package Manage;

import java.util.List;
import java.util.Scanner;

import Database.AllSubject;
import Database.DAOSubject;
import Database.Subject;

public class ManageSubject {
	String classIdNum;
	String subjectName;
	String classNum;
	String classTime;
	String classRoom;
	String syllabus;
	Integer availNum;
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
		
		String subjectName = this.inputString("������ �й� : ");
		
		subject.setSubjectName(subjectName);
		
		boolean r = daos.deleteSubject(subject);
		
		if(r)
			System.out.println("����������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("����������� �����Ͽ����ϴ�.");
	}
	
	private void modifySubject() {
		List<Subject> list = daos.getSubjectList();
		
		for(Subject u : list) {
			System.out.println(u);
		}
		
		String classIdNum = this.inputString("�м���ȣ : ");
		String subjectName = this.inputString("����� : ");
		String classNum = this.inputString("�й� : ");
		String classTime = this.inputString("���ǽð� : ");
		String classRoom = this.inputString("���ǽ� : ");
		String syllabus = this.inputString("���ǰ�ȹ�� : ");
		Integer availNum = this.inputInt("�ִ� ���� �ο� : ");

		subject.setClassIdNum(classIdNum);
		subject.setSubjectName(subjectName);
		subject.setClassNum(classNum);
		subject.setClassTime(classTime);
		subject.setClassRoom(classRoom);
		subject.setSyllabus(syllabus);
		subject.setAvailNum(availNum);
		
		boolean r = daos.modifySubject(subject);
		
		if(r)
			System.out.println("����������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("����������� �����Ͽ����ϴ�.");
		
		list = daos.getSubjectList();
		
		for(Subject u : list) {
			System.out.println(u);
		}
	}

	private void enrollSubject() {
		String classIdNum = this.inputString("�м���ȣ : ");
		String subjectName = this.inputString("����� : ");
		String classNum = this.inputString("�й� : ");
		String classTime = this.inputString("���ǽð� : ");
		String classRoom = this.inputString("���ǽ� : ");
		String syllabus = this.inputString("���ǰ�ȹ�� : ");
		Integer availNum = this.inputInt("�ִ� ���� �ο� : ");

		subject.setClassIdNum(classIdNum);
		subject.setSubjectName(subjectName);
		subject.setClassNum(classNum);
		subject.setClassTime(classTime);
		subject.setClassRoom(classRoom);
		subject.setSyllabus(syllabus);
		subject.setAvailNum(availNum);
		
		boolean r = daos.InsertSubject(subject);
		
		if(r)
			System.out.println("������ ����� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("������ ����� �����Ͽ����ϴ�.");

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
