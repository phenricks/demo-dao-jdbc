package application;

import java.util.Date;
import java.util.List;

import factory.DAOFactory;
import model.dao.impl.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

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
		dep.setId(3);
		Seller updateSeller = new Seller(7, "Pedro H S", "pedro@teste.com", new Date(), 9000.00, dep);
		sellerDao.update(updateSeller);

		System.out.println("\n#====TEST Seller: (DELETE):\n");
		sellerDao.delete(8);

	}

}
