package jdbc_study.ui.content;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class PanelEmployee extends JPanel implements ActionListener {

	private JTextField tfEmpNo;
	private JTextField tfEmpName;
	private JTextField tfTitle;
	private JTextField tfManager;
	private JTextField tfSalary;
	private JTextField tfDno;
	private JButton btnAdd;

	private JFileChooser chooser;
	private JLabel lblImg;
	
	public PanelEmployee() {
		chooser = new JFileChooser();
		initComponents();
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "사원정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 5));
		
		JPanel pImg = new JPanel();
		add(pImg);
		
		btnAdd = new JButton("사진 추가");
		btnAdd.addActionListener(this);
		pImg.setLayout(new BorderLayout(0, 0));
		
		lblImg = new JLabel();
		pImg.add(lblImg);
		pImg.add(btnAdd, BorderLayout.SOUTH);
		
		JPanel pContent = new JPanel();
		add(pContent);
		pContent.setLayout(new GridLayout(0, 2, 5, 10));
		
		JLabel lblEmpNo = new JLabel("사원번호");
		pContent.add(lblEmpNo);
		lblEmpNo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfEmpNo = new JTextField();
		pContent.add(tfEmpNo);
		tfEmpNo.setColumns(10);
		
		JLabel lblEmpName = new JLabel("사원명");
		pContent.add(lblEmpName);
		lblEmpName.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfEmpName = new JTextField();
		pContent.add(tfEmpName);
		tfEmpName.setColumns(10);
		
		JLabel lblTitle = new JLabel("직급");
		pContent.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfTitle = new JTextField();
		pContent.add(tfTitle);
		tfTitle.setColumns(10);
		
		JLabel lblManager = new JLabel("직속상사");
		pContent.add(lblManager);
		lblManager.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfManager = new JTextField();
		pContent.add(tfManager);
		tfManager.setColumns(10);
		
		JLabel lblSalary = new JLabel("급여");
		pContent.add(lblSalary);
		lblSalary.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfSalary = new JTextField();
		pContent.add(tfSalary);
		tfSalary.setColumns(10);
		
		JLabel lblDno = new JLabel("부서번호");
		pContent.add(lblDno);
		lblDno.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfDno = new JTextField();
		pContent.add(tfDno);
		tfDno.setColumns(10);
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
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			actionPerformedBtnAdd(e);
		}
	}
	
	protected void actionPerformedBtnAdd(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("GIF Images", "gif");
		chooser.setFileFilter(filter);
		
		int ret = chooser.showOpenDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String selectedFilePath = chooser.getSelectedFile().getPath();
		
		lblImg.setIcon(new ImageIcon(selectedFilePath));
	}
}
