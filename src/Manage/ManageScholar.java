package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.DAOUser;
import Database.User;

public class ManageScholar {
	static int scholarStudentNum = 10; // 장학금 선출 인원
	DAOUser daou = DAOUser.sharedInstance();
	DAOGrade daog = DAOGrade.sharedInstance();
	User user = new User();
	Scanner scan = new Scanner(System.in);

	public void run() {
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.장학생 조회  2.장학선발 학생 수 수정  3.종료 ");

			switch (chooseWork) {
			case 1: // 장학생 조회
				System.out.println("> 장학생을 조회합니다. 장학생은 지정된 학생 수(" + scholarStudentNum + "명) 만큼 선발 됩니다.");
				if (this.gradingEnded()) { // 모든 학생의 성적 처리가 마감되어 있을 때
					System.out.println("> 최종 장학금 선발 대상 학생입니다.");
					this.inquiryScholarshipStudent();

				} else { // 모든 학생의 성적 처리가 마감되지 않았을 때
					System.out.println("> 모든 학생들의 성적처리가 마감되지 않은 상태의 장학금 선발 대상 학생입니다.");
					this.inquiryScholarshipStudent();
				}

				break;

			case 2: // 장학생 지급율 수정
				System.out.println("> 장학생 선발 학생 수를 수정합니다.");
				this.scholarStudentNum = this.inputInt("> 장학생 선발 학생 수 : ");
				System.out.println("> 장학생 선발 학생 수 수정이 완료되었습니다.");
				break;

			case 3: // 종료
				System.out.println("> 종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

	private void inquiryScholarshipStudent() { // 성적으로 정렬하여 선발 학생 수만큼 출력
		List<String> list = daog.getScholarList(scholarStudentNum);

		System.out.println("-------------------------- 목록 --------------------------");
		int count = 1;
		for (String u : list) {
			String temp[] = u.split("#");
			String uId = temp[0];
			String uName = temp[1];
			String uAvg = temp[2];

			System.out.println(count + "| 학번 : " + uId + " | 이름 : " + uName + " | 평균 학점 : " + uAvg);
			count++;
		}

		// 다시한번 테이블을 확인해서 성적 기입이 다 안되어있다면 성적기입이 마감되지 않았다고 알려주고.
		// 마감되었따면 장학 선발 완료되었다고 출력한다.
	}

	private boolean gradingEnded() {
		if (!new ManageGrade().endGrading) { 		// 성적처리 마감이 되지 않았을 때
			boolean f = daog.checkGradeFinish(); 	// 성적 기입이 하나라도 안된게 있다면 false 반환
			if (f) { 								// 모든 학생의 성적 기입이 된 상태일 때
				System.out.println("> 성적이 모두 처리 되었습니다.");
				String yesOrNo = this.inputString("> 성적 처리를 마감하시겠습니까? (처리 : Y/N) ");
				if (yesOrNo.equals("Y"))
					new ManageGrade().endGrading = true;
			}
		}
		if (!new ManageGrade().endGrading)
			System.out.println("> 성적 처리가 마감되지 않았습니다. 성적 처리 완료 후 마감을 해야합니다.");
		return new ManageGrade().endGrading;
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
