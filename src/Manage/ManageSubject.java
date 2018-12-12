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
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.개설 과목 등록  2.개설 과목 수정  3.개설 과목 삭제  7.종료 ");

			switch (chooseWork) {
			case 1: // 개설 과목 등록
				System.out.println("개설 과목을 등록합니다.");
				this.enrollSubject();
				break;

			case 2: // 개설 과목 수정
				System.out.println("개설 과목을 수정합니다.");
				this.modifySubject();
				break;

			case 3: // 개설 과목 삭제
				System.out.println("개설 과목을 삭제합니다.");
				this.deleteSubject();
				break;

			case 7: // 종료
				System.out.println("종료합니다.");
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

		String classIdNum = this.inputString("삭제할 학수번호 : ");

		subject.setClassIdNum(classIdNum);

		boolean r = daos.deleteSubject(subject);

		if (r)
			System.out.println("교과목삭제가 완료되었습니다.");
		else
			System.out.println("교과목삭제가 실패하였습니다.");
	}

	private void modifySubject() {
		List<Subject> list = daos.getSubjectList();

		for (Subject u : list) {
			System.out.println(u);
		}

		String classIdNum = this.inputString("학수번호 (ex.algorithm-1) : ");
		String classTime = this.inputString("강의시간 (ex.13-15) : ");
		String classRoom = this.inputString("강의실 (ex.101) : ");
		String syllabus = this.inputString("강의계획서 : ");
		Integer availNum = this.inputInt("최대 수강 인원 (ex.30) : ");

		subject.setClassIdNum(classIdNum);
		subject.setClassTime(classTime);
		subject.setClassRoom(classRoom);
		subject.setSyllabus(syllabus);
		subject.setAvailNum(availNum);

		boolean r = daos.modifySubject(subject);

		if (r)
			System.out.println("교과목수정이 완료되었습니다.");
		else
			System.out.println("교과목수정이 실패하였습니다.");

		list = daos.getSubjectList();

		for (Subject u : list) {
			System.out.println(u);
		}
	}

	private void enrollSubject() {
		String subjectName = this.inputString("과목명 (ex.algorithm) : ");
		String classNum = this.inputString("분반 (ex.1) : ");
		String classTime = this.inputString("강의시간 (ex.13-15) : ");
		String classRoom = this.inputString("강의실 (ex.101) : ");
		String syllabus = this.inputString("강의계획서 : ");
		Integer availNum = this.inputInt("최대 수강 인원 (ex.30) : ");

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
				System.out.println("교과목 등록이 완료되었습니다.");
			else
				System.out.println("교과목 등록이 실패하였습니다.");

			List<Subject> list = daos.getSubjectList();

			for (Subject u : list) {
				System.out.println(u);
			}
		} else {
			System.out.println("해당 강의시간과 강의실에 이미 다른수업이 있습니다. ");
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
