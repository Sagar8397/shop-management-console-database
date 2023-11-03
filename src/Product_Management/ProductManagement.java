package Product_Management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import db_operation.DBUtils;

public class ProductManagement {

	public static void productmanagement() throws IOException {

		Scanner sc = new Scanner(System.in);

		boolean CanIKeepRunningTheProgram = true;

		while(CanIKeepRunningTheProgram == true) {
			System.out.println(" -- WELCOME TO PRODUCT MANAGEMENT -- ");
			System.out.println("what would you like to do?");
			System.out.println("1. Add Product");
			System.out.println("2. Edit Product");
			System.out.println("3. Delete Product");
			System.out.println("4. Search Product");
			System.out.println("5. Quit");

			int OptionSelectedByUser = sc.nextInt();

			if(OptionSelectedByUser == ProductOptions.QUIT) {
				System.out.println("!!PROGRAM CLOSED!!");
				CanIKeepRunningTheProgram = false;
			}
			else if(OptionSelectedByUser == ProductOptions.ADD_PRODUCT) {
				Add_Product();
				System.out.println();
			}
			else if(OptionSelectedByUser == ProductOptions.EDIT_PRODUCT) {
				System.out.print("Enter the product name to edit product:");
				sc.nextLine();
				String ep = sc.nextLine();
				Edit_Product(ep);
				System.out.println("\n");
			}
			else if(OptionSelectedByUser == ProductOptions.SEARCH_PRODUCT) {
				System.out.print("Enter the product name to search product:");
				sc.nextLine();
				String sp = sc.nextLine();
				Search_Product(sp);
				System.out.println("\n");
			}
			else if(OptionSelectedByUser == ProductOptions.DELETE_PRODUCT) {
				System.out.print("Enter the product name to delete product:");
				sc.nextLine();
				String dp = sc.nextLine();
				Delete_Product(dp);
				System.out.println("\n");
			}	
		}
	}

	public static void Add_Product() {
		Scanner sc = new Scanner(System.in);
		Product p = new Product();

		System.out.print("Enter Product Name :");
		p.productname = sc.nextLine();

		System.out.print("Enter Product ID :");
		p.ProductID = sc.nextLine();

		System.out.print("Enter Price :");
		p.price = sc.nextLine();

		System.out.print("Enter Quantity :");
		p.Quantity = sc.nextLine();

		System.out.print("Enter Category :");
		p.category = sc.nextLine();

		System.out.println("\n");
		System.out.println("Product_Name is :"+p.productname);
		System.out.println("Product_ID is :"+p.ProductID);
		System.out.println("Price is : "+"Rupee"+p.price);
		System.out.println("Quantity is :"+p.Quantity);
		System.out.println("Category is :"+p.category);

		String Insertquery = "INSERT INTO Product(productname,ProductID,price,Quantity,category)VALUES('"+p.productname+"','"+p.ProductID+"','"+p.price+"','"+p.Quantity+"','"+p.category+"')";
		DBUtils.executeQuery(Insertquery);
		System.out.println("Product added successfully.");
	}
	public static void Edit_Product(String ProductName) {
		String query = "select * from Product Where productname ='"+ProductName+"'";

		ResultSet rs = DBUtils.executeQueryGetResult(query);
		try {
			if(rs.next()) {
				Scanner sc = new Scanner(System.in);

				System.out.print("New Product Name is :");
				String newproductname = sc.nextLine();

				System.out.print("New Product ID is :");
				String newProductID = sc.nextLine();

				System.out.print("New Price is :");
				String newprice = sc.nextLine();

				System.out.print("New Quantity is :");
				String newQuantity = sc.nextLine();

				System.out.print("New Category is :");
				String newcategory = sc.nextLine();

				String Query = "UPDATE Product SET productname = '"+newproductname+"' ,"
						+ "ProductID = '"+newProductID+"',"
						+ "price = '"+newprice+"',"
						+ "Quantity ='"+newQuantity+"',"
						+ "category ='"+newcategory+"'"
						+ "WHERE productname = '"+ProductName+"'";
				DBUtils.executeQuery(Query);

				System.out.println("Product updated successfully");

			}
			else {
				System.out.println("Product not found");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void Search_Product(String ProductName) {
		String query = "SELECT * FROM Product WHERE productname ='"+ProductName+"'";

		ResultSet rs = DBUtils.executeQueryGetResult(query);

		try {
			if(rs.next()) {
				System.out.println("Product Name is :"+rs.getString("ProductName"));
				System.out.println("Product ID is :"+rs.getString("ProductID"));
				System.out.println("Price is : "+"Rupee"+rs.getString("price"));
				System.out.println("Quantity is :"+rs.getString("Quantity"));
				System.out.println("Category is :"+rs.getString("category"));
			}
			else {
				System.out.println("Product not found");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void Delete_Product(String ProductName) {
		String Query = "DELETE FROM Product WHERE ProductName = '"+ProductName+"'";

		DBUtils.executeQuery(Query);
		
		int rowDeleted = DBUtils.getRowsDeleted();

		if(rowDeleted > 0) {
			System.out.println("Product " + ProductName + " has been deleted");
		}
		else {
			System.out.println("Product not found");
		}
	}
}
