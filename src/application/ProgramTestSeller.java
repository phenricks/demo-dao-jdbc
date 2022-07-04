package application;

import java.util.Date;
import java.util.List;

import factory.DAOFactory;
import model.dao.impl.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class ProgramTestSeller {

	public static void main(String[] args) {

		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		
		System.out.println("#====TEST Seller: (FINDBYID):");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("\n#====TEST Seller: (findByDepartment):\n");
		Department dep = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dep);

		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n#====TEST Seller: (FINDALL):\n");
		list = sellerDao.findAll();

		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n#====TEST Seller: (Insert):\n");
		Seller newSeller = new Seller(null, "Pedro", "pedro@teste.com", new Date(), 5000.00, dep);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! ID: = " + newSeller.getId());

		System.out.println("\n#====TEST Seller: (UPDATE):\n");
		seller = sellerDao.findById(7);
		seller.setBaseSalary(10000.0);
		sellerDao.update(seller);

		System.out.println("\n#====TEST Seller: (DELETE):\n");
		sellerDao.delete(newSeller.getId()); // Deletando o novo insert na mesma run

	}

}
