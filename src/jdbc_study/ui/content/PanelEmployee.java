package jdbc_study.ui.content;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PanelEmployee extends JPanel {

	private JTextField tfEmpNo;
	private JTextField tfEmpName;
	private JTextField tfTitle;
	private JTextField tfManager;
	private JTextField tfSalary;
	private JTextField tfDno;

	public PanelEmployee() {
		initComponents();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "사원정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 5));
		
		JLabel lblEmpNo = new JLabel("사원번호");
		lblEmpNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblEmpNo);
		
		tfEmpNo = new JTextField();
		add(tfEmpNo);
		tfEmpNo.setColumns(10);
		
		JLabel lblEmpName = new JLabel("사원명");
		lblEmpName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblEmpName);
		
		tfEmpName = new JTextField();
		tfEmpName.setColumns(10);
		add(tfEmpName);
		
		JLabel lblTitle = new JLabel("직급");
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitle);
		
		tfTitle = new JTextField();
		tfTitle.setColumns(10);
		add(tfTitle);
		
		JLabel lblManager = new JLabel("직속상사");
		lblManager.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblManager);
		
		tfManager = new JTextField();
		tfManager.setColumns(10);
		add(tfManager);
		
		JLabel lblSalary = new JLabel("급여");
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblSalary);
		
		tfSalary = new JTextField();
		tfSalary.setColumns(10);
		add(tfSalary);
		
		JLabel lblDno = new JLabel("부서번호");
		lblDno.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDno);
		
		tfDno = new JTextField();
		tfDno.setColumns(10);
		add(tfDno);
	}
	
	public void setEmployee(Employee emp) {
		tfEmpNo.setText(String.valueOf(emp.getEmpNo()));
		tfEmpName.setText(emp.getEmpName());
		tfTitle.setText(emp.getTitle());
		tfManager.setText(String.valueOf(emp.getManager().getEmpNo()));
		tfSalary.setText(String.valueOf(emp.getSalary()));
		tfDno.setText(String.valueOf(emp.getDno().getDeptNo()));
	}
	
	public Employee getEmployee() {
		int empNo = Integer.parseInt(tfEmpNo.getText().trim());
		String empName = tfEmpName.getText().trim();
		String title = tfTitle.getText().trim();
		int manager = Integer.parseInt(tfManager.getText().trim());
		int salary = Integer.parseInt(tfSalary.getText().trim());
		int dno = Integer.parseInt(tfDno.getText().trim());
		
		return new Employee(empNo, empName, title, new Employee(manager), salary, new Department(dno));
	}
	
	public void clearTextField() {
		tfEmpNo.setText("");
		tfEmpName.setText("");
		tfTitle.setText("");
		tfManager.setText("");
		tfSalary.setText("");
		tfDno.setText("");
	}
	
	public JTextField getTfEmpNo() {
		return tfEmpNo;
	}
	
	public void setTfAllEditable(boolean isEditable) {
		tfEmpNo.setEditable(isEditable);
		tfEmpName.setEditable(isEditable);
		tfTitle.setEditable(isEditable);
		tfManager.setEditable(isEditable);
		tfSalary.setEditable(isEditable);
		tfDno.setEditable(isEditable);
	}
}
