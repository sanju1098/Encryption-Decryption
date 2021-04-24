package com.aklc.ed.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.aklc.ed.model.User;
import com.aklc.ed.util.DBConnection;

public class UserDAO {

	public void createAccount(User user) throws Exception {

		Connection con = null;
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			st.execute("insert into user values ('" + user.getEmail() + "' ,'" + user.getPassword() + "' , '"
					+ user.getFirstname() + "', '" + user.getLastname() + "' ,'" + user.getGender() + "', '"
					+ user.getMobile() + "', '" + user.getAddress() + "') ");
			System.out.println("A new account is created");

		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
			throw e;
		} finally {
			con.close();
		}
	}

	public boolean login(String email, String password) throws Exception {
		Connection con = null;
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					"select count(*) from user where email='" + email + "' and password = '" + password + "' ");

			rs.next();
			int check = rs.getInt(1);

			if (check > 0)
				return true;
			else
				return false;

		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
			throw e;
		} finally {
			con.close();
		}
	}

	public void editProfile(User user) throws Exception {

		Connection con = null;
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			st.execute("update user set firstname='" + user.getFirstname() + "', lastname='" + user.getLastname()
					+ "', gender='" + user.getGender() + "', mobile='" + user.getMobile() + "', address='"
					+ user.getAddress() + "' where email='" + user.getEmail() + "' ");
			System.out.println("A record is Updated");
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
			throw e;
		} finally {
			con.close();
		}

	}

	public boolean changePassword(String email, String currentPassword, String newPassword) throws Exception {
		Connection con = null;
		try {
			con = DBConnection.connect();
			boolean result = login(email, currentPassword);
			if (result) {
				Statement st = con.createStatement();
				st.execute("update user set password='" + newPassword + "' where email='" + email + "' ");
				System.out.println("Password changed Successfully");
				return true;
			} else {
				System.out.println("The current password is wrong");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
			throw e;
		} finally {
			con.close();
		}

	}

	public void deleteProfile(String email) throws Exception {
		Connection con = null;
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			st.execute("delete from user where email='" + email + "' ");
			System.out.println("A record is Deleted");
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
			throw e;
		} finally {
			con.close();
		}

	}

	public String getSecretKet(String email) throws Exception {
		Connection con = null;
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select password from user where email='" + email + "' ");
			rs.next();
			String password = rs.getString("password");
			return password;

		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
			throw e;
		} finally {
			con.close();
		}

	}

	public User getUserInformation(String email) throws Exception {
		Connection con = null;
		User model = new User();
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from user where email='" + email + "' ");
			rs.next();
			model.setEmail(rs.getString("email"));
			model.setFirstname(rs.getString("firstname"));
			model.setLastname(rs.getString("lastname"));
			model.setGender(rs.getString("gender"));
			model.setMobile(rs.getString("mobile"));
			model.setAddress(rs.getString("address"));
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.getMessage());
			throw e;
		} finally {
			con.close();
		}
		return model;
	}

}
