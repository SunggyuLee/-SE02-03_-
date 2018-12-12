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

	private void attendClass() {
		String newAttenduserId = this.inputString("> ���� ��û�� �л��� �й� : ");
		String newAttendClassIdNum = this.inputString("> ���� ��û�� �м� ��ȣ (ex.algorithm-1) : ");

		List<Grade> listg = daog.getUserGradeList(newAttenduserId); // �й��� ������û ����
		List<Subject> SubjectofNew = daos.inquirySubjectList(newAttendClassIdNum); // �м���ȣ�� �������� �Ѱ� ��ȸ
		String classTime = null;
		String classStime = null, classEtime = null;
		if (SubjectofNew.size() >= 1) {
			System.out.println("�м� ��ȣ ������ : " + SubjectofNew.size());
			classTime = SubjectofNew.get(0).getClassTime();
			classStime = classTime.split("-")[0];
			classEtime = classTime.split("-")[1];
		}

		boolean acceptAttend = true;
		for (Grade u : listg) { // �� �й��� ���Ͽ� ��û�� ��� �м���ȣ�� ���Ͽ�...
			// �Է¹��� �м���ȣ�� ���ǽð��� ��ġ���� Ȯ��
			List<Subject> SubjectofOne = daos.inquirySubjectList(u.getClassIdNum());
			String CT = SubjectofOne.get(0).getClassTime();
			String ST = CT.split("-")[0];
			String ET = CT.split("-")[1];
			// ���� �ð��� ��ġ�� �ʴ��� Ȯ��
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
			System.out.println("> ���� �ð��� ��Ĩ�ϴ�.");
		} else {
			gradee.setClassIdNum(newAttendClassIdNum);
			gradee.setUserId(newAttenduserId);
			gradee.setGrade((float) 0);
			daog.InsertGrade(gradee);
		}
	}

	private void deleteClass() { // ���� ���
		List<Grade> list = daog.getGradeList();
		for (Grade g : list) { // �м���ȣ�� �й�
			System.out.println("�м���ȣ : " + g.getClassIdNum() + " �й� : " + g.getUserId());
		}
		String deleteUserId = this.inputString("> ���� ����� �л��� �й� : ");
		String deleteClassIdNum = this.inputString("> ���� ����� �м� ��ȣ (ex.algorithm-1) : ");
		gradee.setClassIdNum(deleteClassIdNum);
		gradee.setUserId(deleteUserId);
		boolean r = daog.deleteGrade(gradee);

		if (r) {
			list = daog.getGradeList();
			for (Grade g : list) {
				System.out.println("�м���ȣ : " + g.getClassIdNum() + " �й� : " + g.getUserId());
			}
			System.out.println("> ���� ��Ұ� �Ϸ�Ǿ����ϴ�.");
		} else
			System.out.println("> ���� ��Ұ� �����Ͽ����ϴ�.");
	}

	private void inquirySyllabus() {
		List<Subject> list = daos.getSubjectList();
		
		System.out.println("-------------------- ���� ���� ��� --------------------");
		int count = 1;
		for (Subject u : list) {
			if(count % 6 == 0) {
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

	private void inquiryTimetable() {
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
