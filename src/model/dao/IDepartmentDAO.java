package model.dao;

import java.util.List;

import model.entities.Department;

public interface IDepartmentDAO {

	public void insert(Department obj);

	public void update(Department obj);

	public void delete(Integer id);

	public Department findById(Integer id);

	public List<Department> findAll();
	
	
}
