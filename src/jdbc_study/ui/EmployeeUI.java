package jdbc_study.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import jdbc_study.dao.EmployeeDao;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;
import jdbc_study.ui.content.PanelEmployee;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class EmployeeUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;
	private JButton btnCancel;
	private PanelEmployee pContent;
	private EmployeeDao dao;
	
	private ErpManagementUI erpManagementUI;
	
	public void setDao(EmployeeDao dao) {
		this.dao = dao;
	}

	public EmployeeUI() {
		initComponents();
	}
	
	private void initComponents() {
		setTitle("사원 정보");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pContent = new PanelEmployee();
		contentPane.add(pContent, BorderLayout.CENTER);
		
		JPanel pBtns = new JPanel();
		contentPane.add(pBtns, BorderLayout.SOUTH);
		
		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}
		if (e.getSource() == btnAdd) {
			if(btnAdd.getText().equals("추가")) {
				actionPerformedBtnAdd(e);
			}else {
				actionPerformedBtnUpdate(e);
			}
		}
	}
	
	private void actionPerformedBtnUpdate(ActionEvent e) {
		Employee newEmp = pContent.getEmployee();
		int res;
		try {
			res = dao.updateEmployee(newEmp);
			if(res != -1) {
				JOptionPane.showMessageDialog(null, String.format("%s 사원이 수정되었습니다.", newEmp.getEmpName()));
				pContent.clearTextField();
				btnAdd.setText("추가");
			}
			erpManagementUI.refreshUI();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		Employee newEmp = pContent.getEmployee();
		int res;
		
		try {
			res = dao.insertEmployee(newEmp);
			if(res != -1) {
				JOptionPane.showMessageDialog(null, String.format("%s 사원이 추가되었습니다.", newEmp.getEmpName()));
				pContent.clearTextField();
			}
			erpManagementUI.refreshUI();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	protected void actionPerformedBtnCancel(ActionEvent e) {
		pContent.clearTextField();
	}
	
	public void setParent(ErpManagementUI erpManagementUI) {
		this.erpManagementUI = erpManagementUI;
	}
	
	public void setEmployee(Employee emp) {
		pContent.setEmployee(emp);
		pContent.getTfEmpNo().setEditable(false);
		btnAdd.setText("수정");
	}
}
