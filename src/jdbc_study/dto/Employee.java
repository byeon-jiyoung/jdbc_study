package jdbc_study.dto;

public class Employee {
	private int empNo;
	private String empName;
	private String title;
	private Employee manager;
	private int salary;
	private Department dno;
	private byte[] pic; //사진은 byte배열로 받음
	
	public Employee(int empNo) {
		this.empNo = empNo;
	}
	
	public Employee(int empNo, String empName, String title, Employee manager, int salary, Department dno) {
		this.empNo = empNo;
		this.empName = empName;
		this.title = title;
		this.manager = manager;
		this.salary = salary;
		this.dno = dno;
	}
	
	public Employee(int empNo, String empName, String title, Employee manager, int salary, Department dno, byte[] pic) {
		this.empNo = empNo;
		this.empName = empName;
		this.title = title;
		this.manager = manager;
		this.salary = salary;
		this.dno = dno;
		this.pic = pic;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Department getDno() {
		return dno;
	}

	public void setDno(Department dno) {
		this.dno = dno;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %s", empNo, empName, title, manager.getEmpNo(), salary, dno.getDeptNo());
																											//Department에서 toString 오버라이딩 했으니까 dno는 그냥 둬도 됨
	}

	public Object[] toArray() { //toArray는 테이블에 담기 위해 사용함
			//int, String이 있으니까 Object로 해서 한번에 다 넣어주기 위해 Object사용
		return new Object[] {empNo, empName, title, manager.getEmpNo(), salary, dno.getDeptNo()};
	}
}
