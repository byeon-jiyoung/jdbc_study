package jdbc_study;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.mysql.jdbc.log.LogUtils;

import jdbc_study.dao.DepartmentDao;
import jdbc_study.dao.DeptEmpTransactionDao;
import jdbc_study.dao.EmployeeDao;
import jdbc_study.daoimpl.DepartmentDaoImpl;
import jdbc_study.daoimpl.DeptEmpTransactionDaoImpl;
import jdbc_study.daoimpl.EmployeeDaoImpl;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //test를 순서대로 실행하기위해

public class DeptEmpTransactionDaoTest {
	//밑에 메소드들이 static 이라서 static은 static만 접근가능
	static final Logger log = LogManager.getLogger(); 
	static DeptEmpTransactionDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println();
		log.trace("start DeptEmpTransactionDaoTest");
		dao = new DeptEmpTransactionDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println();
		log.trace("Stop DeptEmpTransactionDaoTest");
		dao = null;
	}

	@Before
	public void setUp() throws Exception {
		System.out.println();
	}

	@Test //department 실패
	public void test1trInsertEmpAndDept() {
		log.trace("Department fail");
		Department dept = new Department(1, "수정", 8);
		Employee emp = new Employee(1004, "이종석", "사원", new Employee(1003), 150000, dept);
		
		int res = dao.trInsertEmpAndDept(emp, dept);
		log.trace(String.format("res %d", res));
		Assert.assertNotEquals(2, res);
	}
	
	@Test //department 성공 employee 실패
	public void test2trInsertEmpAndDept() {
		log.trace("test2trInsertEmpAndDept()");
		Department dept = new Department(6, "마케팅", 8);
		Employee emp = new Employee(1003, "공유", "과장", new Employee(4377), 150000, dept);
		
		int res = dao.trInsertEmpAndDept(emp, dept);
		log.trace(String.format("res %d", res));
		Assert.assertNotEquals(2, res);
	}
	
	@Test //department 성공 employee 성공
	public void test3trInsertEmpAndDept() throws SQLException {
		log.trace("Department, Employee success");
		Department dept = new Department(7, "총무", 17);
		Employee emp = new Employee(1005, "김우빈", "사원", new Employee(1003), 150000, dept);
		
		int res = dao.trInsertEmpAndDept(emp, dept);
		log.trace(String.format("res %d", res));
		System.out.println(res);
		Assert.assertEquals(2, res);
		
		EmployeeDao empDao = new EmployeeDaoImpl();
		DepartmentDao deptDao = new DepartmentDaoImpl();
		
		Employee newEmp = empDao.selectEmployeeByNo(emp);
		Department newDept = deptDao.selectDepartmentByNo(dept);
		log.trace(newEmp);
		log.trace(newDept);
		
		empDao.deleteEmployee(emp);
		deptDao.deleteDepartment(dept);
	}

}
