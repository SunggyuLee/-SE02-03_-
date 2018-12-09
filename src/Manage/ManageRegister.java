package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOUser;
import Database.User;

public class ManageRegister {
	String userId;
	String pwd;
	String name;
	String birth;
	String addr;
	String phoneNum;
	Scanner scan = new Scanner(System.in);
	DAOUser daou = DAOUser.sharedInstance();
	User user = new User();
	
	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���� ���  2.���� �⺻  3.���� ����  4.���� ����  7.���� ");

			switch (chooseWork) {
			case 1: // ���� ���
				System.out.println("������ ����մϴ�.");
				this.enrollStudent();
				break;

			case 2: // ���� �⺻
				System.out.println("������ ��ȸ�մϴ�.");
				this.inquiryStudent();
				break;
				
			case 3: // ���� ����
				System.out.println("������ �����մϴ�.");
				this.modifyStudent();
				break;
				
			case 4: // ���� ����
				System.out.println("������ �����մϴ�.");
				this.deleteStudent();
				break;
				
			case 7: // ����
				System.out.println("�����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}
	
	private void deleteStudent() {
		List<User> list = daou.getUserList();
		
		for(User u : list) {
			System.out.println(u);
		}
		
		String userId = this.inputString("������ �й� : ");
		
		user.setUserId(userId);
		
		boolean r = daou.deleteUser(user);
		
		if(r)
			System.out.println("���������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("���������� �����Ͽ����ϴ�.");
	}
	
	private void modifyStudent() {
		List<User> list = daou.getUserList();
		
		for(User u : list) {
			System.out.println(u);
		}
		
		String userId = this.inputString("������ �й� : ");
		String pwd = this.inputString("��й�ȣ : ");
		String name = this.inputString("���� (ex. �̼���) : ");
		String birth = this.inputString("������� (ex. 19940216) : ");
		String addr = this.inputString("�ּ� : ");
		String phoneNum = this.inputString("��ȭ��ȣ : ");
		
		user.setUserId(userId);
		user.setPwd(pwd);
		user.setName(name);
		user.setBirth(birth);
		user.setAddr(addr);
		user.setPhoneNum(phoneNum);
		
		boolean r = daou.modifyUser(user);
		
		if(r)
			System.out.println("���������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("���������� �����Ͽ����ϴ�.");
	}
	
	private void inquiryStudent() {
		List<User> list = daou.getUserList();
		
		for(User u : list) {
			System.out.println(u);
		}
		String userId = this.inputString("��ȸ�� �й� : ");
		user.setUserId(userId);
		list = daou.inquiryStudent(userId);
		for(User u : list) {
			System.out.println(u);
		}
	}
	
	private void enrollStudent() {
		String userId = this.inputString("���̵� : ");
		String pwd = this.inputString("��й�ȣ : ");
		String name = this.inputString("���� (ex. �̼���) : ");
		String birth = this.inputString("������� (ex. 19940216) : ");
		String addr = this.inputString("�ּ� : ");
		String phoneNum = this.inputString("��ȭ��ȣ : ");
		// �߰�~
		user.setUserId(userId);
		user.setPwd(pwd);
		user.setName(name);
		user.setBirth(birth);
		user.setAddr(addr);
		user.setPhoneNum(phoneNum);
		
		boolean r = daou.InsertUser(user);
		
		if(r)
			System.out.println("��������� �Ϸ�Ǿ����ϴ�.");
		else
			System.out.println("��������� �����Ͽ����ϴ�.");

		// ����� �� Ȯ�� �� select �غ�
		List<User> list = daou.getUserList();
		
		for(User u : list) {
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
