package jdbc_study.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jdbc_study.dao.DepartmentDao;
import jdbc_study.dao.EmployeeDao;
import jdbc_study.daoimpl.DepartmentDaoImpl;
import jdbc_study.daoimpl.EmployeeDaoImpl;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;
import jdbc_study.ui.content.PanelDepartment;
import jdbc_study.ui.content.PanelEmployee;

import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ErpManagementUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnDelete;
	private JButton btnUpdate;
	private JButton btnSearch;
	private JButton btnAdd;
	private JButton btnList;

	private DepartmentDao dao;

	private DepartmentUI frameDepartment;
	private DepartmentListUI frameDepartmentList;
	
	private JPanel pDep;
	private JPanel pEmp;
	
	private JButton btnAdd2;
	private JButton btnUpdate2;
	private JButton btnDelete2;
	private JButton btnSearch2;
	private JButton btnList2;
	
	private EmployeeDao dao2;
	
	private EmployeeUI frameEmployee;
	private EmployeeListUI frameEmployeeList;

	public ErpManagementUI() {
		dao = new DepartmentDaoImpl();
		dao2 = new EmployeeDaoImpl();
		initComponents();
	}

	private void initComponents() {
		setTitle("부서 관리 메뉴");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 10, 0));

		pDep = new JPanel();
		pDep.setBorder(new TitledBorder(null, "부서관리", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pDep);
		pDep.setLayout(new GridLayout(0, 5, 0, 0));

		btnAdd = new JButton("부서 추가");
//		btnAdd.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (frameDepartment == null) {
//					frameDepartment = new DepartmentUI();
//					frameDepartment.setParent(ErpManagementUI.this);
//					frameDepartment.setDao(dao);
//				}
//				frameDepartment.setVisible(true);				
//			}
//		});
		pDep.add(btnAdd);

		btnUpdate = new JButton("부서 수정");
		pDep.add(btnUpdate);

		btnDelete = new JButton("부서 삭제");
		pDep.add(btnDelete);

		btnSearch = new JButton("부서 검색");
		pDep.add(btnSearch);

		btnList = new JButton("부서 목록");
		pDep.add(btnList);
		
		btnList.addActionListener(this);
		btnSearch.addActionListener(this);
		btnDelete.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnAdd.addActionListener(this);

		pEmp = new JPanel();
		pEmp.setBorder(new TitledBorder(null, "사원관리", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pEmp);
		pEmp.setLayout(new GridLayout(0, 5, 0, 0));

		btnAdd2 = new JButton("사원 추가");
		btnAdd2.addActionListener(this);
		pEmp.add(btnAdd2);

		btnUpdate2 = new JButton("사원 수정");
		btnUpdate2.addActionListener(this);
		pEmp.add(btnUpdate2);

		btnDelete2 = new JButton("사원 삭제");
		btnDelete2.addActionListener(this);
		pEmp.add(btnDelete2);

		btnSearch2 = new JButton("사원 검색");
		btnSearch2.addActionListener(this);
		pEmp.add(btnSearch2);

		btnList2 = new JButton("사원 목록");
		btnList2.addActionListener(this);
		pEmp.add(btnList2);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch2) {
			actionPerformedBtnSearch2(e);
		}
		if (e.getSource() == btnUpdate2) {
			actionPerformedBtnUpdate2(e);
		}
		if (e.getSource() == btnAdd2) {
			actionPerformedBtnAdd2(e);
		}
		if (e.getSource() == btnList2) {
			actionPerformedBtnList2(e);
		}
		if (e.getSource() == btnDelete2) {
			actionPerformedBtnDelete2(e);
		}
		if (e.getSource() == btnList) {
			actionPerformedBtnList(e);
		}
		if (e.getSource() == btnAdd) {
			actionPerformedBtnAdd(e);
		}
		if (e.getSource() == btnSearch) {
			actionPerformedBtnSearch(e);
		}
		if (e.getSource() == btnUpdate) {
			actionPerformedBtnUpdate(e);
		}
		if (e.getSource() == btnDelete) {
			actionPerformedBtnDelete(e);
		}
	}

	protected void actionPerformedBtnDelete(ActionEvent e) {
		String deptNo = JOptionPane.showInputDialog("삭제할 부서번호를 입력하세요");

		try {
			int res = dao.deleteDepartment(new Department(Integer.parseInt(deptNo)));
			if (frameDepartment == null) {
				frameDepartment = new DepartmentUI();
				frameDepartment.setDao(dao);
			}
			if (res != -1)
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
			refreshUI();
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	protected void actionPerformedBtnUpdate(ActionEvent e) {
		String deptNo = JOptionPane.showInputDialog("수정할 부서번호를 입력하세요");
		Department selDept;
		try {
			selDept = dao.selectDepartmentByNo(new Department(Integer.parseInt(deptNo)));
			if (selDept == null) {
				JOptionPane.showMessageDialog(null, "수정할 부서가 존재하지 않습니다.");
				return;
			}
			if (frameDepartment == null) {
				frameDepartment = new DepartmentUI();
				frameDepartment.setParent(this);
				frameDepartment.setDao(dao);
			}
			frameDepartment.setDepartment(selDept);
			frameDepartment.setVisible(true);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	protected void actionPerformedBtnSearch(ActionEvent e) {
		String deptNo = JOptionPane.showInputDialog("검색할 부서번호를 입력하세요");
		Department selDept;
		try {
			selDept = dao.selectDepartmentByNo(new Department(Integer.parseInt(deptNo)));
			if (selDept == null) {
				JOptionPane.showMessageDialog(null, "해당 부서가 존재하지 않습니다.");
				return;
			}
			PanelDepartment pdept = new PanelDepartment();
			pdept.setDepartment(selDept);
			pdept.setTfAllEditable(false);
			JOptionPane.showMessageDialog(null, pdept, "부서 정보", JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	protected void actionPerformedBtnAdd(ActionEvent e) {
		if (frameDepartment == null) {
			frameDepartment = new DepartmentUI();
			frameDepartment.setParent(this);
			frameDepartment.setDao(dao);
		}
		frameDepartment.setVisible(true);
	}

	protected void actionPerformedBtnList(ActionEvent e) {
		if (frameDepartmentList == null) {
			frameDepartmentList = new DepartmentListUI();
		}
		frameDepartmentList.setDepartmentList(dao.selectDepartmentByAll());
		frameDepartmentList.reloadData();
		frameDepartmentList.setVisible(true);
	}

	
	
	public void refreshUI() {
		if (frameDepartmentList != null && frameDepartmentList.isVisible()) {
			frameDepartmentList.setDepartmentList(dao.selectDepartmentByAll());
			frameDepartmentList.reloadData();
		}
		
		if(frameEmployeeList != null && frameEmployeeList.isVisible()) {
			try {
				frameEmployeeList.setEmpList(dao2.selectEmployeeByAll());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frameEmployeeList.reloadData();
		}
	}
	
//	public void refreshUI2() throws SQLException {
//		if(frameEmployeeList != null && frameEmployeeList.isVisible()) {
//			frameEmployeeList.setEmpList(dao2.selectEmployeeByAll());
//			frameEmployeeList.reloadData();
//		}
//	}
	
	protected void actionPerformedBtnDelete2(ActionEvent e) {
		String empNo = JOptionPane.showInputDialog("삭제할 사원번호를 입력하세요");
		
		try {
			int res = dao2.deleteEmployee(new Employee(Integer.parseInt(empNo)));
			if(frameEmployee == null) {
				frameEmployee = new EmployeeUI();
				frameEmployee.setDao(dao2);
			}
			if(res != -1) {
				JOptionPane.showMessageDialog(null, "삭제되었습니다.");
				refreshUI();
			}
		}catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	protected void actionPerformedBtnList2(ActionEvent e) {
		if(frameEmployeeList == null) {
			frameEmployeeList = new EmployeeListUI();
		}
		try {
			frameEmployeeList.setEmpList(dao2.selectEmployeeByAll());
			frameEmployeeList.reloadData();
			frameEmployeeList.setVisible(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	protected void actionPerformedBtnAdd2(ActionEvent e) {
		if(frameEmployee == null) {
			frameEmployee = new EmployeeUI();
			frameEmployee.setParent(ErpManagementUI.this);
			frameEmployee.setDao(dao2);
		}
		frameEmployee.setVisible(true);
	}
	
	protected void actionPerformedBtnUpdate2(ActionEvent e) {
		String empNo = JOptionPane.showInputDialog("수정할 사원번호를 입력하세요.");
		Employee selEmp;
		
		try {
			selEmp = dao2.selectEmployeeByNo(new Employee(Integer.parseInt(empNo)));
			if(selEmp == null) {
				JOptionPane.showMessageDialog(null, "수정할 사원이 존재하지 않습니다.");
				return;
			}
			if(frameEmployee == null) {
				frameEmployee = new EmployeeUI();
				frameEmployee.setParent(this);
				frameEmployee.setDao(dao2);
			}
			frameEmployee.setEmployee(selEmp);
			frameEmployee.setVisible(true);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	protected void actionPerformedBtnSearch2(ActionEvent e) {
		String empNo = JOptionPane.showInputDialog("검색할 사원번호를 입력하세요.");
		Employee selEmp;
		
		try {
			selEmp = dao2.selectEmployeeByNo(new Employee(Integer.parseInt(empNo)));
			System.out.println(selEmp);
			if(selEmp == null) {
				JOptionPane.showMessageDialog(null, "해당 사원이 존재하지 않습니다.");
				return;
			}
			PanelEmployee pemp = new PanelEmployee();
			pemp.setEmployee(selEmp);
			pemp.setTfAllEditable(false);
			JOptionPane.showMessageDialog(null, pemp, "사원 정보", JOptionPane.INFORMATION_MESSAGE);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
