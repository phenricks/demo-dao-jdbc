package application;

import java.util.List;

import factory.DAOFactory;
import model.dao.impl.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		
		System.out.println("#====TEST Seller: (findByID):");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("\n#====TEST Seller: (findByDepartment):\n");
		Department dep = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dep);

		for (Seller obj : list) {
			System.out.println(obj);
		}

	}

}
