package jdbc_study;

import java.awt.EventQueue;

import jdbc_study.ui.ErpManagementUI;

public class ErpMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErpManagementUI frame = new ErpManagementUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//EmployeeDaoTest안에 getImage()함수에서 사용하는 이미지 경로 확인하려고 적은거
		System.out.println(System.getProperty("user.dir"));
		//System.out.println(System.getProperty("user.dir")+System.getProperty("file.separator"));
		//System.out.println("첫번째수정");
	}
}
