package application;

import factory.DAOFactory;
import model.dao.impl.SellerDAO;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		
		System.out.println("#====TEST Seller: (findByID):");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

	}

}
