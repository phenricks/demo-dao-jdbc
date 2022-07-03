package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ISellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAO implements ISellerDAO {

	private Connection conn;

	public SellerDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		ResultSet rs = null;

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO ");
		query.append("seller ");
		query.append("(Name, ");
		query.append("Email, ");
		query.append("BirthDate, ");
		query.append("BaseSalary, ");
		query.append("DepartmentId )");
		query.append("VALUES ");
		query.append("(?, ?, ?, ?, ? )");
		try {
			st = conn.prepareStatement(query.toString(),Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DbException("Unexpected error!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement ps = null;

		StringBuilder query = new StringBuilder();
		query.append("UPDATE seller ");
		query.append("SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? ");
		query.append("WHERE Id = ?");

		try {
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			ps.setInt(6, obj.getId());

			int rowAffected = ps.executeUpdate();

			if (rowAffected > 0)
				System.out.println("Update successfully!");
			else
				throw new DbException("Update failed!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void delete(Integer id) {
		PreparedStatement ps = null;

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM seller ");
		query.append("WHERE id = ?");

		try {
			ps = conn.prepareStatement(query.toString());
			ps.setInt(1, id);

			int rowAffected = ps.executeUpdate();

			if (rowAffected > 0)
				System.out.println("Delete successfully!");
			else
				throw new DbException("Delete failed!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT seller.*, ");
		query.append("department.Name as DepName ");
		query.append("FROM seller ");
		query.append("INNER JOIN department ");
		query.append("ON seller.DepartmentId = department.Id ");
		query.append("WHERE seller.Id = ? ");

		try {
			st = conn.prepareStatement(query.toString());
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT seller.*, ");
		query.append("department.Name AS DepName ");
		query.append("FROM seller INNER JOIN department ");
		query.append("ON seller.DepartmentId = department.Id ");
		query.append("ORDER BY Name");

		try {
			st = conn.prepareStatement(query.toString());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();


			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}

			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT seller.*, ");
		query.append("department.Name as DepName ");
		query.append("FROM seller ");
		query.append("INNER JOIN department ");
		query.append("ON seller.DepartmentId = department.Id ");
		query.append("WHERE DepartmentId = ? ");
		query.append("ORDER BY Name");

		try {
			st = conn.prepareStatement(query.toString());
			st.setInt(1, department.getId());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);

		return obj;
	}


	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

}
