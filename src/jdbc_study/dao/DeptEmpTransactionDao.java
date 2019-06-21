package jdbc_study.dao;

import jdbc_study.dto.Department;
import jdbc_study.dto.Employee;

public interface DeptEmpTransactionDao {
	int trInsertEmpAndDept(Employee emp, Department dept);
}
//새로 추가할 때 마다 commit을 해주는게 유지보수 차원에서 좋다.
//다 하고 한번에 commmit을 하면 나중에 수정을 하고 싶을 때 싹 다 없어짐