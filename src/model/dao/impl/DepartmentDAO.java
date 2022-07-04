package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.IDepartmentDAO;
import model.entities.Department;

public class DepartmentDAO implements IDepartmentDAO {

	private Connection conn;

	public DepartmentDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement ps = null;

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO department ");
		query.append("(Name) ");
		query.append("VALUES (?)");

		try {
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, obj.getName());

			int rowAffected = ps.executeUpdate();

			if (rowAffected > 0)
				System.out.println("Inserted!");
			else
				throw new DbException("Unexpected error!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement ps = null;

		StringBuilder query = new StringBuilder();
		query.append("UPDATE department ");
		query.append("SET Name = ?");
		query.append("WHERE Id = ?");

		try {
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getId());

			int rowAffected = ps.executeUpdate();

			if (rowAffected > 0)
				System.out.println("Updated!");
			else
				throw new DbException("Unexpected error!");

		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void delete(Integer id) {
		PreparedStatement ps = null;

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM department ");
		query.append("WHERE Id = ? ");

		try {
			ps = conn.prepareStatement(query.toString());
			ps.setInt(1, id);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0)
				System.out.println("Deleted!");
			else
				throw new DbException("Unexpected error!");
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public Department findById(Integer id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT Id, Name ");
		query.append("FROM department ");
		query.append("WHERE Id = ?");

		try {
			ps = conn.prepareStatement(query.toString());
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Department obj = instantiateDepartment(rs);
				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public List<Department> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuilder query = new StringBuilder();

		query.append("SELECT Id, Name ");
		query.append("FROM department");
		
		try {
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();

			List<Department> list = new ArrayList<>();

			while (rs.next()) {
				Department obj = instantiateDepartment(rs);
				list.add(obj);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}


	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

}
