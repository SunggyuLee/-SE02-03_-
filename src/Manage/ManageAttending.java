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
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.개설과목 관리  2.강의시간표 조회  3.강의계획서 조회  4.수강신청  5.수강취소  6.종료 ");

			switch (chooseWork) {
			case 1: // 개설 과목 등록
				System.out.println("> 개설 과목을 관리합니다.");
				new ManageSubject().run();
				break;

			case 2: // 강의시간표 조회
				System.out.println("> 강의시간표를 조회합니다.");
				this.inquiryTimetable();
				break;

			case 3: // 강의계획서 조회
				System.out.println("> 강의계획서를 조회합니다.");
				this.inquirySyllabus();
				break;

			case 4: // 수강신청
				System.out.println("> 수강 신청합니다.");
				this.attendClass();
				break;

			case 5: // 수강취소
				System.out.println("> 수강 취소합니다.");
				this.deleteClass();
				break;

			case 6: // 종료
				System.out.println("> 종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

	private void attendClass() { // 수강 신청
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- 학번 목록 --------------------------");
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
		String newAttenduserId = this.inputString("> 수강 신청할 학생의 학번 : ");

		List<Grade> listg = daog.getUserGradeList(newAttenduserId);

		System.out.println("------------------------ 현재 수강 신청 현황 ------------------------");
		int count2 = 1;
		for (Grade g : listg) {
			System.out.println(count2 + ". " + g.getClassIdNum());
			count2++;
		}
		System.out.println();

		List<Subject> s_list = daos.getSubjectList();

		System.out.println("-------------------------- 개설 과목 목록 --------------------------");
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
		String newAttendClassIdNum = this.inputString("> 수강 신청할 학수 번호 (ex.algorithm-1) : ");

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
			System.out.println("> 강의 시간이 중복되어 수강 신청을 할 수 없습니다.");
		} else {
			gradee.setClassIdNum(newAttendClassIdNum);
			gradee.setUserId(newAttenduserId);
			gradee.setGrade((float) 0);
			daog.InsertGrade(gradee);
		}
		
	}

	private void deleteClass() { // 수강 취소
		List<User> u_list = daou.getStudentList();

		System.out.println("-------------------------- 학번 목록 --------------------------");
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
		String deleteUserId = this.inputString("> 수강 취소할 학생의 학번 : ");

		List<Grade> listg = daog.getUserGradeList(deleteUserId);

		System.out.println("------------------------ 현재 수강 신청 현황 ------------------------");
		int count2 = 1;
		for (Grade g : listg) {
			System.out.println(count2 + ". " + g.getClassIdNum());
			count2++;
		}
		System.out.println();
		
		String deleteClassIdNum = this.inputString("> 수강 취소할 학수 번호 (ex.algorithm-1) : ");
		
		gradee.setClassIdNum(deleteClassIdNum);
		gradee.setUserId(deleteUserId);
		boolean r = daog.deleteGrade(gradee);

		if (r) {
			System.out.println("> 수강 취소가 완료되었습니다.");
		} else
			System.out.println("> 수강 취소를 실패하였습니다.");
		
	}

	private void inquirySyllabus() { // 강의 계획서 조회
		List<Subject> list = daos.getSubjectList();

		System.out.println("-------------------- 개설 과목 목록 --------------------");
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

		String search = this.inputString("> 강의계획서를 조회할 학수 번호를 입력하세요 (ex.algorithm-1) : ");
		String[] result = daos.getSyllabusAndSubjectName(search).split("#");
		System.out.println("[ 과목명 : " + result[0] + " ]");
		System.out.println("강의 계획 내용 : " + result[1]);

	}

	private void inquiryTimetable() { // 강의 시간표 조회
		List<Subject> list = daos.getSubjectList();

		System.out.println("-------------------------- 강의 시간표 --------------------------");
		int count = 1;
		for (Subject u : list) {
			System.out.println(count + " | 학수 번호 : " + u.getClassIdNum() + " | 강의시간 : " + u.getClassTime() + " | 강의실 : "
					+ u.getClassRoom() + " | 최대수강인원 : " + u.getAvailNum());
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
