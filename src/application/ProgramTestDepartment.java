package application;

import java.util.List;

import factory.DAOFactory;
import model.dao.impl.DepartmentDAO;
import model.entities.Department;

public class ProgramTestDepartment {

	public static void main(String[] args) {

		DepartmentDAO departmentDao = DAOFactory.createDepartmentDAO();

		System.out.printf("%n#====TEST Department: (FINDALL):%n");
		List<Department> departments = departmentDao.findAll();
		for (Department department : departments) {
			System.out.println(department);
		}

		System.out.printf("%n#====TEST Department: (FINDBYID):%n");
		Department department = departmentDao.findById(2);
		System.out.println(department);

		System.out.printf("%n#====TEST Department: (INSERT):%n");
		department.setName("Teste");
		departmentDao.insert(department);

		System.out.printf("%n#====TEST Department: (UPDATE):%n");
		department = departmentDao.findById(5);
		department.setName("Cars");
		departmentDao.update(department);

		System.out.printf("%n#====TEST Department: (DELETE):%n");
		departmentDao.delete(6);
	}

}
