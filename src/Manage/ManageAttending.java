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
		while(run) {
			System.out.println();System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.개설과목 관리  2.강의시간표 조회  3.강의계획서 조회  4.수강신청  5.수강취소  7.종료 ");

			switch (chooseWork) {
			case 1: // 개설 과목 등록
				System.out.println("개설 과목을 관리합니다.");
				new ManageSubject().run();
				break;

			case 2: // 강의시간표 조회
				System.out.println("강의시간표를 조회합니다.");
				this.inquiryTimetable();
				break;
				
			case 3: // 강의계획서 조회
				System.out.println("강의계획서를 조회합니다.");
				this.inquirySyllabus();
				break;
				
			case 4: // 수강신청
				System.out.println("수강 신청합니다.");
				this.attendClass();
				break;
				
			case 5: // 수강취소
				System.out.println("수강 취소합니다.");
				this.deleteClass();
				break;
				
			case 7: // 종료
				System.out.println("종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

    private void deleteClass() { //수강 취소
    	List<Grade> list = daog.getGradeList();
		for(Grade g : list) { //학수번호와 학번
			System.out.println("학수번호 : " + g.getClassIdNum() + " 학번 : " + g.getUserId());
		}
		String deleteUserId = this.inputString("수강 취소할 학생의 학번 : ");
        String deleteClassIdNum = this.inputString("수강 취소할 학수번호 : ");
        gradee.setClassIdNum(deleteClassIdNum);
        gradee.setUserId(deleteUserId);
        boolean r = daog.deleteGrade(gradee);
        
        if (r) {
        	list = daog.getGradeList();
        	for(Grade g : list) {
        		System.out.println("학수번호 : " + g.getClassIdNum() + " 학번 : " + g.getUserId());
        	}
            System.out.println("수강 취소가 완료되었습니다.");
        }
        else
            System.out.println("수강 취소가 실패하였습니다.");
    }

    private int inputInt(String string) {
        System.out.print(string);
        return Integer.parseInt(scan.nextLine());
    }

    private String inputString(String string) {
        System.out.print(string);
        return scan.nextLine();
    }

	private void attendClass() {
		String newAttenduserId = this.inputString("수강 신청할 학생의 학번 : ");
		String newAttendClassIdNum = this.inputString("수강 신청할 학수번호 : ");
		
		List<Grade> listg = daog.getUserGradeList(newAttenduserId); // 학번의 수강신청 내역
		List<Subject> SubjectofNew = daos.inquirySubjectList(newAttendClassIdNum); // 학수번호로 개설과목 한개 조회
		String classTime = null;
		String classStime = null, classEtime = null;
		if(SubjectofNew.size()>=1) {
			System.out.println("학수번호 사이즈 : "+SubjectofNew.size());
			classTime = SubjectofNew.get(0).getClassTime();
			classStime = classTime.split("-")[0];
			classEtime = classTime.split("-")[1];
		}
		

		boolean acceptAttend = true;
		for(Grade u : listg) { // 한 학번에 대하여 신청된 모든 학수번호에 대하여...
			// 입력받은 학수번호의 강의시간이 겹치는지 확인
			List<Subject> SubjectofOne = daos.inquirySubjectList(u.getClassIdNum());
			String CT = SubjectofOne.get(0).getClassTime();
			String ST = CT.split("-")[0];
			String ET = CT.split("-")[1];
			// 강의 시간이 겹치지 않는지 확인
			if(Integer.parseInt(classStime) != Integer.parseInt(ST)) {
				if(Integer.parseInt(classStime) < Integer.parseInt(ST)) {
					if(Integer.parseInt(classEtime) > Integer.parseInt(ST)) {
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
		
		if(!acceptAttend) {
			System.out.println("강의 시간이 겹칩니다.");
		} else {
			gradee.setClassIdNum(newAttendClassIdNum);
			gradee.setUserId(newAttenduserId);
			gradee.setGrade((float)0);
			daog.InsertGrade(gradee);
		}
	}

	private void inquirySyllabus() { //강의계획서 조회, 학수번호를 입력받아 과목명과 강의계획서를 출력한다.
		// 학수번호로 조회해야 한다. 왜냐하면 과목명으로는 다른 강의계획서가 존재할 수도 있다.
		String[] result = daos.getSyllabusAndSubjectName(this.inputString("강의계획서를 조회할 학수번호를 입력하세요 : ")).split("#");

		System.out.println("과목명 : " + result[0] + " 강의계획서 : " + result[1]);
	}

	private void inquiryTimetable() { //강의시간표 조회
		List<Subject> list = daos.getSubjectList();
		
		for(Subject u : list) {//학수번호, 강의시간, 강의실, 최대수강인원
			System.out.println("학수 번호 : " + u.getClassIdNum() + " 강의시간 : " + u.getClassTime() + " 강의실 : " + u.getClassRoom() +
					" 최대수강인원 : " + u.getAvailNum());
		}
	}
}
