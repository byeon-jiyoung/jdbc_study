package jdbc_study;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbc_study.dao.EmployeeDao;
import jdbc_study.daoimpl.EmployeeDaoImpl;
import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	static final Logger log = LogManager.getLogger();
	static EmployeeDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception { //테스트 시작 전 제일 먼저 호출
		log.trace("setUpBeforeClass()");
		dao = new EmployeeDaoImpl();
	}
	
	/*
	그냥 공부할라고 넣은거니까 없애도 됨
	@AfterClass
	public static void tearDownAfterClass() throws Exception { //테스트 마무리 될 때 호출
		log.trace("tearDownAfterClass()");
	}
	@Before
	public void setUp() throws Exception { //메소드(test())가 호출되기 전에 호출
		log.trace("setUp()");
	}
	@After
	public void tearDown() throws Exception { //메소드(test())가 끝나면 호출
		log.trace("tearDown()");
	}
	*/
	
	@Test
	public void test01SelectEmployeeByAll() throws SQLException, FileNotFoundException, IOException {
		log.trace("testSelectEmployeeByAll()");
		
		List<Employee> list = dao.selectEmployeeByAll();
		//import java.util.List!!!!!!! awt아니다!!!
		Assert.assertNotEquals(0, list.size());
							  //list가 하나라도 있으면 읽어오게 되는거니까 0만 아니면 되는거다. 그래서 0을 적어주는거지
		File imgFile = null;
		
		for(Employee e : list) {
			if(e.getPic() != null) { //null이 아니면 이미지가 없는거
				imgFile = getPicFile(e);
				log.trace(imgFile.getAbsolutePath()); //이미지 경로 출력
			}
		}
		
	}//이렇게 하면 디비에 있는 사진을 파일로 만들 수 가 있는거다.
	
	@Test
	public void test02InsertEmployee() throws SQLException {
		log.trace("testInsertEmployee()");
		//fail("Not yet implemented");
		Employee newEmp = new Employee(1004, "김우빈", "사원", new Employee(1003), 1500000, new Department(1), getImage("김우빈.jpg"));
		
		int res = dao.insertEmployee(newEmp);  //제대로 추가되면 1이 리턴된다(디비버에서 insert하고 나서 Updates Rows에 1뜨는거랑 같음)
		log.trace("res = " + res);
		Assert.assertEquals(1, res); //같으면 녹색, 다르면 빨간색 뜬다
									 //res가 1이면 녹색이 나오는거
									 //Assert가 색있는 막대기 나오는거라고 생각하면 됨
	}

	@Test
	public void test03UpdateEmployee() throws SQLException {
		Employee newEmp = new Employee(1004, "우빈", "대리", new Employee(3426), 3500000, new Department(1), getImage("우빈.jpg"));
		int res = dao.updateEmployee(newEmp);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void test04DeleteEmployee() throws SQLException {
		Employee newEmp = new Employee(1004);
		int res = dao.deleteEmployee(newEmp);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void test05SelectEmployeeByNo() throws SQLException, FileNotFoundException, IOException {
		Employee newEmp = dao.selectEmployeeByNo(new Employee(1004));
		Assert.assertNotNull(newEmp);
		
		if (newEmp.getPic() != null) {
			File imgFile = getPicFile(newEmp);
			log.trace(imgFile.getAbsolutePath());
		}
	}
	

	private File getPicFile(Employee e) throws FileNotFoundException, IOException {
		File file = null;
		
		//디비에서 들고온 파일을 만들어 써야되니까
		file = new File(System.getProperty("user.dir")+"\\pics\\"+e.getEmpName()+".jpg"); //파일객체가 만들어진거
														//특수문자로 인식하기 때문에 \를 두번 적은거다
		
		try(FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(e.getPic());
						//(바이트배열)
		}
		return file;
	}
	
	
	private byte[] getImage(String fileName) {
		byte[] pic = null; //이미지 경로나 사이즈를 아는게 없으니까 일단 null로 선언해주는거
		
		//D:\workspace\workspace_java\jdbc_study
		String imgPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "images" + System.getProperty("file.separator") + fileName;
		
		File imgFile = new File(imgPath); //파일객체가 생성된거지 파일이 생성된건 아니다.
		
		try(InputStream is = new FileInputStream(imgFile);) {
			pic = new byte[is.available()]; //원래 반복문 돌려야 되는데 그냥 이렇게 적음
			is.read(pic);
		} catch (FileNotFoundException e) {
			System.out.println("해당 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pic;
	}
	

}
