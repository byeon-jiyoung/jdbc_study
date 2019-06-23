package jdbc_study.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import jdbc_study.dto.Employee;

import javax.swing.JTable;

@SuppressWarnings("serial")
public class EmployeeListUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private List<Employee> empList;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					EmployeeListUI frame = new EmployeeListUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public EmployeeListUI() {
		initComponents();
	}
	
	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	private void initComponents() {
		setTitle("사원 목록");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "사원 목록", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
	
	public void reloadData() {
		table.setModel(new DefaultTableModel(getRows(), getColumnNames()));
	}

	private String[] getColumnNames() {
		return new String[] {"사원번호", "사원명", "직급", "직속상사", "급여", "부서번호"};
	}

	private Object[][] getRows() {
		Object[][] rows = new Object[empList.size()][];
		for(int i=0; i<empList.size(); i++) {
			rows[i] = empList.get(i).toArray();
		}
		return rows;
	}
}
