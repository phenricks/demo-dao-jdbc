package factory;

import db.DB;
import model.dao.impl.DepartmentDAO;
import model.dao.impl.SellerDAO;

public class DAOFactory {

	public static SellerDAO createSellerDAO() {
		return new SellerDAO(DB.getConnection());
	}

	public static DepartmentDAO createDepartmentDAO() {
		return new DepartmentDAO(DB.getConnection());
	}
}
