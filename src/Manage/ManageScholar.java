package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.DAOUser;
import Database.User;

public class ManageScholar {
	int scholarStudentNum = 10;										// 장학금 선출 인원
	DAOUser daou = DAOUser.sharedInstance();
	DAOGrade daog = DAOGrade.sharedInstance();
	User user = new User();
	Scanner scan = new Scanner(System.in);
	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.장학생 조회  2.장학선발 학생 수 수정  3.종료 ");

			switch (chooseWork) {
			case 1: // 장학생 조회
				System.out.println("장학생을 조회합니다. 장학생은 지정된 학생 수("+ scholarStudentNum +"명) 만큼 선발 됩니다.");
				//if(this.gradingEnded()) // 성적처리가 마감되었는지 확인
				this.inquiryScholarshipStudent();

				break;

			case 2: // 장학생 지급율 수정
				System.out.println("장학생 선발 학생 수를 수정합니다.");
				this.scholarStudentNum = this.inputInt("> 장학생 선발 학생 수 : ");
				System.out.println("장학생 선발 학생 수 수정 완료.");
				break;

			case 7: // 종료
				System.out.println("종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

	private void inquiryScholarshipStudent() {	// 성적으로 정렬하여 선발 학생 수만큼 출력
		
		List<String> list = daog.getScholarList(scholarStudentNum);
		int count = 1;
		for(String u : list) {
			String temp[] = u.split("#");
			String uId = temp[0];
			String uName = temp[1];
			String uAvg = temp[2];

			System.out.println(count + ". 학번 : " + uId + "    이름 : " + uName + "    평균 학점 : "+uAvg);
			count++;
		}
	}

	private boolean gradingEnded() {
		if(!new ManageGrade().endGrading) // 성적처리가 이미 마감되었으면
		{
			// 주석은 수도 코드 이니 지우지말고 작성하면 된다 ***
			// 성적에 null 없는지 확인
			// if 성적에 null이 없으면 {
			System.out.println("성적이 모두 기입되었지만 성적처리를 마감하지 않았습니다.");
			String yesOrNo = this.inputString("성적처리를 마감하시겠습니까? (처리 : y ) ");
			if(yesOrNo.equals("y"))
				new ManageGrade().endGrading = true;
			//			} else
			//				System.out.println("성적이 기입되지 않은 것이 존재합니다.");
		}
		if(!new ManageGrade().endGrading)
			System.out.println("성적처리가 마감되어 있지 않습니다.");
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
