package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.DAOSubject;
import Database.DAOUser;
import Database.Grade;
import Database.Subject;
import Database.User;

public class ManageAttending {
	String classIdNum;
	String userId;
	Float grade;
	Scanner scan = new Scanner(System.in);
	DAOSubject daos = DAOSubject.sharedInstance();
	DAOGrade daog = DAOGrade.sharedInstance();
	DAOUser daou = DAOUser.sharedInstance();
	Subject subject = new Subject();
	Grade gradee = new Grade();
	User user = new User();

	public void run() {
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.�������� ����  2.���ǽð�ǥ ��ȸ  3.���ǰ�ȹ�� ��ȸ  4.������û  5.�������  6.���� ");

			switch (chooseWork) {
			case 1: // ���� ���� ���
				System.out.println("> ���� ������ �����մϴ�.");
				new ManageSubject().run();
				break;

			case 2: // ���ǽð�ǥ ��ȸ
				System.out.println("> ���ǽð�ǥ�� ��ȸ�մϴ�.");
				this.inquiryTimetable();
				break;

			case 3: // ���ǰ�ȹ�� ��ȸ
				System.out.println("> ���ǰ�ȹ���� ��ȸ�մϴ�.");
				this.inquirySyllabus();
				break;

			case 4: // ������û
				System.out.println("> ���� ��û�մϴ�.");
				this.attendClass();
				break;

			case 5: // �������
				System.out.println("> ���� ����մϴ�.");
				this.deleteClass();
				break;

			case 6: // ����
				System.out.println("> �����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}

	private void attendClass() { // ���� ��û
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- �й� ��� --------------------------");
		int count = 1;
		for (User u : u_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "]" + u.getUserId(), " "));
			count++;
		}
		System.out.println();
		System.out.println();
		String newAttenduserId = this.inputString("> ���� ��û�� �л��� �й� : ");

		List<Grade> listg = daog.getUserGradeList(newAttenduserId);

		System.out.println("------------------------ ���� ���� ��û ��Ȳ ------------------------");
		int count2 = 1;
		for (Grade g : listg) {
			System.out.println(count2 + ". " + g.getClassIdNum());
			count2++;
		}
		System.out.println();

		List<Subject> s_list = daos.getSubjectList();

		System.out.println("-------------------------- ���� ���� ��� --------------------------");
		int count3 = 1;
		for (Subject u : s_list) {
			if (count3 % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count3 + "] " + u.getClassIdNum(), " "));
			count3++;
		}
		System.out.println();
		System.out.println();
		String newAttendClassIdNum = this.inputString("> ���� ��û�� �м� ��ȣ (ex.algorithm-1) : ");

		List<Subject> SubjectofNew = daos.inquirySubjectList(newAttendClassIdNum);
		String classTime = null;
		String classStime = null, classEtime = null;

		if (SubjectofNew.size() != 0) {
			classTime = SubjectofNew.get(0).getClassTime();
			classStime = classTime.split("-")[0];
			classEtime = classTime.split("-")[1];
		}

		boolean acceptAttend = true;
		for (Grade u : listg) {
			List<Subject> SubjectofOne = daos.inquirySubjectList(u.getClassIdNum());
			String CT = SubjectofOne.get(0).getClassTime();
			String ST = CT.split("-")[0];
			String ET = CT.split("-")[1];

			if (Integer.parseInt(classStime) != Integer.parseInt(ST)) {
				if (Integer.parseInt(classStime) < Integer.parseInt(ST)) {
					if (Integer.parseInt(classEtime) > Integer.parseInt(ST)) {
						acceptAttend = false;
						break;
					}
				} else {
					if (Integer.parseInt(classStime) < Integer.parseInt(ET)) {
						acceptAttend = false;
						break;
					}
				}
			} else {
				acceptAttend = false;
				break;
			}
		}

		if (!acceptAttend) {
			System.out.println("> ���� �ð��� �ߺ��Ǿ� ���� ��û�� �� �� �����ϴ�.");
		} else {
			gradee.setClassIdNum(newAttendClassIdNum);
			gradee.setUserId(newAttenduserId);
			gradee.setGrade((float) 0);
			daog.InsertGrade(gradee);
		}
		
	}

	private void deleteClass() { // ���� ���
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- �й� ��� --------------------------");
		int count = 1;
		for (User u : u_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "]" + u.getUserId(), " "));
			count++;
		}
		System.out.println();
		System.out.println();
		String deleteUserId = this.inputString("> ���� ����� �л��� �й� : ");

		List<Grade> listg = daog.getUserGradeList(deleteUserId);

		System.out.println("------------------------ ���� ���� ��û ��Ȳ ------------------------");
		int count2 = 1;
		for (Grade g : listg) {
			System.out.println(count2 + ". " + g.getClassIdNum());
			count2++;
		}
		System.out.println();
		
		String deleteClassIdNum = this.inputString("> ���� ����� �м� ��ȣ (ex.algorithm-1) : ");
		
		gradee.setClassIdNum(deleteClassIdNum);
		gradee.setUserId(deleteUserId);
		boolean r = daog.deleteGrade(gradee);

		if (r) {
			System.out.println("> ���� ��Ұ� �Ϸ�Ǿ����ϴ�.");
		} else
			System.out.println("> ���� ��Ҹ� �����Ͽ����ϴ�.");
		
	}

	private void inquirySyllabus() { // ���� ��ȹ�� ��ȸ
		List<Subject> list = daos.getSubjectList();

		System.out.println("-------------------- ���� ���� ��� --------------------");
		int count = 1;
		for (Subject u : list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getClassIdNum(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String search = this.inputString("> ���ǰ�ȹ���� ��ȸ�� �м� ��ȣ�� �Է��ϼ��� (ex.algorithm-1) : ");
		String[] result = daos.getSyllabusAndSubjectName(search).split("#");
		System.out.println("[ ����� : " + result[0] + " ]");
		System.out.println("���� ��ȹ ���� : " + result[1]);

	}

	private void inquiryTimetable() { // ���� �ð�ǥ ��ȸ
		List<Subject> list = daos.getSubjectList();

		System.out.println("-------------------------- ���� �ð�ǥ --------------------------");
		int count = 1;
		for (Subject u : list) {
			System.out.println(count + " | �м� ��ȣ : " + u.getClassIdNum() + " | ���ǽð� : " + u.getClassTime() + " | ���ǽ� : "
					+ u.getClassRoom() + " | �ִ�����ο� : " + u.getAvailNum());
			count++;
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
