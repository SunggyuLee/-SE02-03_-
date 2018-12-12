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
		while (run) {
			System.out.println();
			System.out.println();
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

		for (Subject u : list) {
			System.out.println(u);
		}

		String classIdNum = this.inputString("������ �м���ȣ : ");

		subject.setClassIdNum(classIdNum);

		boolean r = daos.deleteSubject(subject);

		if (r)
			System.out.println("����������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("����������� �����Ͽ����ϴ�.");
	}

	private void modifySubject() {
		List<Subject> list = daos.getSubjectList();

		for (Subject u : list) {
			System.out.println(u);
		}

		String classIdNum = this.inputString("�м���ȣ (ex.algorithm-1) : ");
		String classTime = this.inputString("���ǽð� (ex.13-15) : ");
		String classRoom = this.inputString("���ǽ� (ex.101) : ");
		String syllabus = this.inputString("���ǰ�ȹ�� : ");
		Integer availNum = this.inputInt("�ִ� ���� �ο� (ex.30) : ");

		subject.setClassIdNum(classIdNum);
		subject.setClassTime(classTime);
		subject.setClassRoom(classRoom);
		subject.setSyllabus(syllabus);
		subject.setAvailNum(availNum);

		boolean r = daos.modifySubject(subject);

		if (r)
			System.out.println("����������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("����������� �����Ͽ����ϴ�.");

		list = daos.getSubjectList();

		for (Subject u : list) {
			System.out.println(u);
		}
	}

	private void enrollSubject() {
		String subjectName = this.inputString("����� (ex.algorithm) : ");
		String classNum = this.inputString("�й� (ex.1) : ");
		String classTime = this.inputString("���ǽð� (ex.13-15) : ");
		String classRoom = this.inputString("���ǽ� (ex.101) : ");
		String syllabus = this.inputString("���ǰ�ȹ�� : ");
		Integer availNum = this.inputInt("�ִ� ���� �ο� (ex.30) : ");

		subject.setClassIdNum(subjectName + "-" + classNum);
		subject.setSubjectName(subjectName);
		subject.setClassNum(classNum);
		subject.setClassTime(classTime);
		subject.setClassRoom(classRoom);
		subject.setSyllabus(syllabus);
		subject.setAvailNum(availNum);

		List<Subject> s_list = daos.checkDuplicationForclass(subject);
		boolean r1 = true;

		for (Subject s : s_list) {
			String time = s.getClassTime();
			int start_time = Integer.parseInt(time.split("-")[0]);
			int end_time = Integer.parseInt(time.split("-")[1]);

			int cur_start = Integer.parseInt(classTime.split("-")[0]);
			int cur_end = Integer.parseInt(classTime.split("-")[1]);

			if (start_time != cur_start) {
				if (start_time < cur_start) {
					if (end_time > cur_start) {
						r1 = false;
					}
				} else {
					if (start_time < cur_end) {
						r1 = false;
					}
				}
			} else {
				r1 = false;
			}
		}

		if (r1) {
			boolean r2 = daos.InsertSubject(subject);

			if (r2)
				System.out.println("������ ����� �Ϸ�Ǿ����ϴ�.");
			else
				System.out.println("������ ����� �����Ͽ����ϴ�.");

			List<Subject> list = daos.getSubjectList();

			for (Subject u : list) {
				System.out.println(u);
			}
		} else {
			System.out.println("�ش� ���ǽð��� ���ǽǿ� �̹� �ٸ������� �ֽ��ϴ�. ");
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
