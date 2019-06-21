package jdbc_study.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc_study.dao.EmployeeDao;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;
import jdbc_study.jdbc.MySQLjdbcUtil;

public class EmployeeDaoImpl implements EmployeeDao {
	static final Logger log = LogManager.getLogger(); //이거는 그냥 log에 찍어보기 위해서 사용(디버깅용도)
	
	@Override
	public List<Employee> selectEmployeeByAll() throws SQLException {
		String sql = "select empno, empname, title, manager, salary, dno, pic from employee";
		
		//여러개를 담아야 되니까 list가 필요 (각각의 사원이 employee니까)
		List<Employee> lists = new ArrayList<Employee>();
		try(Connection conn = MySQLjdbcUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()) {
			log.trace(pstmt); //sql문장이 제대로 출력되는지 확인을 해봐야 나중에 개고생을 안함
			
			while(rs.next()) { //next가 없다는 말은 더 이상 데이터가 없다는 말
				lists.add(getEmployee(rs));
							//(직접 만든 메소드)
			};
		}
		
		return lists;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {
		return new Employee(rs.getInt("empno"), 
							rs.getString("empName"), 
							rs.getString("title"), 
							new Employee(rs.getInt("manager")), 
							rs.getInt("salary"), 
							new Department(rs.getInt("dno")), 
							rs.getBytes("pic"));
	}

	@Override
	public Employee selectEmployeeByNo(Employee employee) throws SQLException {
		String sql = "select empno, empname, title, manager, salary, dno, pic from employee where empno=?";
		
		Employee selEmp = null;
		
		try(Connection conn = MySQLjdbcUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpNo());
			log.trace(pstmt);
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					selEmp = getEmployee(rs);
				}	
			}
		}
		return null;
	}

	@Override
	public int insertEmployee(Employee employee) throws SQLException {
		log.trace("insertEmployee()");
		
		//String sql = "insert into employee (empno, empname, title, manager, salary, dno, pic) values(?, ?, ?, ?, ?, ?, ?)";
		String sql = "insert into employee values(?, ?, ?, ?, ?, ?, ?)";
		
		//★insert는 ResultSet 쓸 필요 없음. select할 때만 사용한다!!!!!!!★
		//★Connection - 디비연결, PreparedStatement - 디비 sql넘기는거, ResultSet - 상태반환
		//★ResultSet은 디비에서 커서의 역할과 같다고 보면 된다.
		try(Connection conn = MySQLjdbcUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) { //이렇게 적어줘서 자동 close호출됨
			pstmt.setInt(1, employee.getEmpNo());
			pstmt.setString(2, employee.getEmpName());
			pstmt.setString(3, employee.getTitle());
			pstmt.setInt(4, employee.getManager().getEmpNo());
										//여기까지 Employee를 의미함
			pstmt.setInt(5, employee.getSalary());
			pstmt.setInt(6, employee.getDno().getDeptNo());
										//여기까지 Department를 의미함
			pstmt.setBytes(7, employee.getPic());
			log.trace(pstmt);
			return pstmt.executeUpdate(); //(0이면 데이터 없는거)
		}
	}

	@Override
	public int deleteEmployee(Employee employee) throws SQLException {
		String sql = "delete from employee where empno=?";
		
		try(Connection conn = MySQLjdbcUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpNo());
			return pstmt.executeUpdate();
		}
	}

	@Override
	public int updateEmployee(Employee employee) throws SQLException {
		String sql = "update employee set empname=?, title=?, manager=?, salary=?, dno=?, pic=? where empno=?";
		
		try(Connection conn = MySQLjdbcUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, employee.getEmpName());
			pstmt.setString(2, employee.getTitle());
			pstmt.setInt(3, employee.getManager().getEmpNo());
			pstmt.setInt(4, employee.getSalary());
			pstmt.setInt(5, employee.getDno().getDeptNo());
			pstmt.setBytes(6, employee.getPic());
			pstmt.setInt(7, employee.getEmpNo());
			
			log.trace(pstmt);
			return pstmt.executeUpdate();
		}
	}

}
