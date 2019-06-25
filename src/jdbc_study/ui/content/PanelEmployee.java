package jdbc_study.ui.content;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class PanelEmployee extends JPanel implements ActionListener {

	private JTextField tfEmpNo;
	private JTextField tfEmpName;
	private JTextField tfTitle;
	private JTextField tfManager;
	private JTextField tfSalary;
	private JTextField tfDno;
	private JLabel lblImg;
	
	private String imgPath;
	private int imgWidth;
	private int imgHeight;
	private JButton btnAdd;
	
	private JFileChooser chooser;
	private String selectedFilePath;
	private File picsDir;
	
	public PanelEmployee() {
		imgPath = System.getProperty("user.dir") + "\\images\\";
		imgWidth = 90;
		imgHeight = 150;
		
		//System.out.println(imgPath);
		
		chooser = new JFileChooser(imgPath);
		
		initComponents();
		
		switchImage(imgPath + "noImg.jpg");
		
		picsDir = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "pics" + System.getProperty("file.separator"));

		if (!picsDir.exists()) {
			picsDir.mkdir();
		}
	}
	
	private void initComponents() {
		setBorder(new TitledBorder(null, "사원정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel pImg = new JPanel();
		add(pImg);
		pImg.setLayout(new BoxLayout(pImg, BoxLayout.Y_AXIS));
		
		lblImg = new JLabel();
		pImg.add(lblImg);
		lblImg.setSize(imgWidth, imgHeight);
		
		btnAdd = new JButton("사진 추가");
		btnAdd.addActionListener(this);
		pImg.add(btnAdd);
		
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
		
		JLabel lblTitle = new JLabel("직책");
		pContent.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfTitle = new JTextField();
		pContent.add(tfTitle);
		tfTitle.setColumns(10);
		
		JLabel lblDno = new JLabel("부서번호");
		pContent.add(lblDno);
		lblDno.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfDno = new JTextField();
		pContent.add(tfDno);
		tfDno.setColumns(10);
		
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
	}
	
	public void setEmployee(Employee emp) {
		tfEmpNo.setText(String.valueOf(emp.getEmpNo()));
		tfEmpName.setText(emp.getEmpName());
		tfTitle.setText(emp.getTitle());
		tfManager.setText(String.valueOf(emp.getManager().getEmpNo()));
		tfSalary.setText(String.valueOf(emp.getSalary()));
		tfDno.setText(String.valueOf(emp.getDno().getDeptNo()));
		
		if(emp.getPic() != null) {
			try {
				File imgFile = getPicFile(emp);
				switchImage(imgFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			switchImage(imgPath + "noImg.jpg");
		}
		btnAdd.setText("변경");
			
	}
	
	public Employee getEmployee() {
		int empNo = Integer.parseInt(tfEmpNo.getText().trim());
		String empName = tfEmpName.getText().trim();
		String title = tfTitle.getText().trim();
		Employee manager = new Employee(Integer.parseInt(tfManager.getText().trim()));
		int salary = Integer.parseInt(tfSalary.getText().trim());
		Department dno = new Department(Integer.parseInt(tfDno.getText().trim()));
		
		return new Employee(empNo, empName, title, manager, salary, dno, getImage());
	}
	
	public void clearTextField() {
		tfEmpNo.setText("");
		tfEmpName.setText("");
		tfTitle.setText("");
		tfManager.setText("");
		tfSalary.setText("");
		tfDno.setText("");
		switchImage(imgPath + "noImg.jpg");
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
		btnAdd.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			actionPerformedBtnAdd(e);
		}
	}
	
	protected void actionPerformedBtnAdd(ActionEvent e) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
		chooser.setFileFilter(filter);
		
		int ret = chooser.showOpenDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		selectedFilePath = chooser.getSelectedFile().getPath();
		
		switchImage(selectedFilePath);
		btnAdd.setText("변경");
		repaint();
		revalidate();
	}
	
	private void switchImage(String filePath) {
		Image tmpIcon = new ImageIcon(filePath).getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(tmpIcon);
		lblImg.setIcon(imageIcon);
	}
	
	private File getPicFile(Employee e) throws FileNotFoundException, IOException {
		File file = null;
		file = new File(picsDir, e.getEmpName() + ".jpg");
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(e.getPic());
		}
		return file;
	}
	
	private byte[] getImage() {
		byte[] pic = null;

		File imgFile = new File(selectedFilePath);

		try (InputStream is = new FileInputStream(imgFile);) {
			pic = new byte[is.available()];
			is.read(pic);
		} catch (FileNotFoundException e) {
			System.out.println("해당 파일을 찾을 수 없음");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}
}
