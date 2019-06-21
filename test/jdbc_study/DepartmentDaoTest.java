package jdbc_study;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbc_study.dao.DepartmentDao;
import jdbc_study.daoimpl.DepartmentDaoImpl;
import jdbc_study.dto.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //메소드의 호출순서를 고정하겠다.
				//이름순으로 정렬하는거
public class DepartmentDaoTest {
	static final Logger log = LogManager.getLogger();
	static DepartmentDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
					//test가 호출 되기 전에 먼저 호출되고 test를 호출하고, test가 끝나면 밑에 tearDownAfterClass 호출
		log.trace("setUpBeforeClass()");
		dao = new DepartmentDaoImpl(); //동적바인딩
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		log.trace("tearDownAfterClass()");
		dao = null; //null을 넣은 의미는 없다. 그냥 끝났으니까 집어넣은거
	}

	@Test
	public void test01SelectDepartmentByAll() {
				//01은 @FixMethodOrder이거 때문에 적어준거
		List<Department> lists = dao.selectDepartmentByAll();
		
		for(Department d : lists) {
			//System.out.println(d);
			log.trace(d);
		}
		
		Assert.assertNotEquals(0, lists.size());;
	}
	
	@Test
	public void test02SelectDepartmentByNo() throws SQLException {
		Department dto = new Department(1); //1번 부서를 검색해 달라
		Department selDept = dao.selectDepartmentByNo(dto);
		
		log.trace(selDept);
		
		Assert.assertNotNull(selDept);
	}
	
	@Test
	public void test03InsertDepartment() throws SQLException {
		Department newDept = new Department(5, "마케팅", 40);
		int res = dao.insertDepartment(newDept);
		Assert.assertNotEquals(-1, res);

		//공부하려고 이렇게 적은건데 그냥 위에처럼 간단히 적어도 된다
//		try {
//			int res = dao.insertDepartment(newDept);
//			if(res != -1) {
//				System.out.println("success");
//			}
//			Assert.assertNotEquals(-1, res);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	@Test
	public void test04UpdateDepartment() throws SQLException {
		Department newDept = new Department(5, "인사", 40);
		int res = dao.updateDepartment(newDept);
		Assert.assertNotEquals(-1, res);
	}
	
	@Test
	public void test05DeleteDepartment() throws SQLException {
		Department newDept = new Department(5);
		int res = dao.deleteDepartment(newDept);
		Assert.assertNotEquals(-1, res);
	}
}
