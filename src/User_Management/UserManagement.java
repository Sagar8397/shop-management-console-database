package User_Management;

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

public class UserManagement {

	public static void Usermanagement()throws IOException  {

		Scanner sc = new Scanner(System.in);

		boolean CanIKeepRunningTheProgram = true;

		while(CanIKeepRunningTheProgram == true) {
			System.out.println(" -- WELCOME TO THE USER MANAGEMENT -- ");
			System.out.println("\n");

			System.out.println("What would you like to do?");
			System.out.println("1. Add User");
			System.out.println("2. Edit User");
			System.out.println("3. Delete User");
			System.out.println("4. Search User");
			System.out.println("5. Quit");

			int OptionSelectedByUser = sc.nextInt();

			if(OptionSelectedByUser == UserOptions.QUIT) {
				System.out.println("!! Program Closed !!");
				CanIKeepRunningTheProgram = false;
			}
			else if(OptionSelectedByUser == UserOptions.ADD_USER) {
				AddUser();
				System.out.println();
			}
			else if(OptionSelectedByUser == UserOptions.SEARCH_USER) {
				System.out.print("Enter user name to search user :");
				sc.nextLine();
				String sn = sc.nextLine();
				Search_User(sn);
				System.out.println("\n");
			}
			else if(OptionSelectedByUser == UserOptions.DELETE_USER) {
				System.out.print("Enter user name to delete user :");
				sc.nextLine();
				String su = sc.nextLine();
				Delete_User(su);
				System.out.println("\n");
			}
			else if(OptionSelectedByUser == UserOptions.EDIT_USER) {
				System.out.print("Enter user name to edit user :");
				sc.nextLine();
				String eu = sc.nextLine();
				Edit_User(eu);
				System.out.println("\n");
			}
		}
	}
	public static void AddUser() {
		Scanner sc = new Scanner(System.in);
		User u = new User();

		System.out.println("Enter user name :");
		u.Username = sc.nextLine();

		System.out.println("Enter login name :");
		u.LoginName = sc.nextLine();

		System.out.println("Enter password :");
		u.Password = sc.nextLine();

		System.out.println("Enter confirm password :");
		u.ConfirmPassword = sc.nextLine();

		System.out.println("Enter user role :");
		u.UserRole = sc.nextLine();

		System.out.println("\n");
		System.out.println("Username is :"+u.Username);
		System.out.println("Loginname is :"+u.LoginName);
		System.out.println("Password is :"+u.Password);
		System.out.println("ConfirmPassword is :"+u.ConfirmPassword);
		System.out.println("UserRole is :"+u.UserRole);

		String query = "INSERT INTO User(Username,LoginName,Password,ConfirmPassword,UserRole)VALUES('"+u.Username+"','"+u.LoginName+"','"+u.Password+"','"+u.ConfirmPassword+"','"+u.UserRole+"')";

		DBUtils.executeQuery(query);
		
		System.out.println("User added successfully.");
	}
	public static void Edit_User(String Username) {
		String Query = "select * from User where Username = '"+Username+"'";

		ResultSet rs = DBUtils.executeQueryGetResult(Query);
		try {

			if(rs.next()) {
				Scanner sc = new Scanner(System.in);

				System.out.print("New user name is :");
				String newUsername = sc.nextLine();

				System.out.print("New login name is :");
				String newLoginName = sc.nextLine();

				System.out.print("New password is :");
				String newpassword = sc.nextLine();

				System.out.print("New conform password is :");
				String newConform_Password = sc.nextLine();

				System.out.print("New user role is :");
				String newUserRole = sc.nextLine();

				String query = "UPDATE User SET Username = '"+newUsername+"', "
					    + "LoginName = '" + newLoginName + "', " 
					    + "Password = '"+newpassword+"', "
					    + "ConfirmPassword = '"+newConform_Password+"',"
					    + "UserRole = '"+newUserRole+"'"
					    + "WHERE Username = '"+Username+"'";

				DBUtils.executeQuery(query);

				System.out.println("User updated successfully.");

			}
			else {
				System.out.println(" !! USER NOT FOUND !! ");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}
	public static void Search_User(String Username) {
		String query = "SELECT * FROM User WHERE Username = '"+Username+"'";

		ResultSet rs = DBUtils.executeQueryGetResult(query);

		try {
			if(rs.next()) {
				System.out.println("User name is :"+rs.getString("Username"));
				System.out.println("Login name is :"+rs.getString("LoginName"));
				System.out.println("Password is :"+rs.getString("Password"));
				System.out.println("Conform password is :"+rs.getString("ConfirmPassword"));
				System.out.println("User role is :"+rs.getString("UserRole"));
			}else {
				System.out.println(" !! USER NOT FOUND !! ");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}
	public static void Delete_User(String Username) {

		String query = "DELETE FROM User WHERE Username = '"+Username+"'";

		DBUtils.executeQuery(query);

		int rowsDeleted = DBUtils.getRowsDeleted();
		
		if(rowsDeleted > 0) {
			System.out.println("User " + Username + " has been deleted");
		}
		else {
	        System.out.println(" !! USER NOT FOUND !! ");
	    }
	}

	public static boolean ValidateUserAndPassword(String LoginName,String password) throws SQLException {
		String query = " Select * from User where LoginName='"+LoginName+"' and password = '"+password+"' ";
		
		ResultSet rs = DBUtils.executeQueryGetResult(query);
		
		try {
			if(rs.next()) {
				return true;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
