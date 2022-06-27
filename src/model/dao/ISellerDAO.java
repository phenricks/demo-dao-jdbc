package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface ISellerDAO {

	public void insert(Seller obj);

	public void update(Seller obj);

	public void delete(Integer id);

	public Seller findById(Integer id);

	public List<Seller> findAll();
	
	public List<Seller> findByDepartment(Department department);

}
