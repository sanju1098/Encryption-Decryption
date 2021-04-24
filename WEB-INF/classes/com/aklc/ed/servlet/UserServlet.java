package com.aklc.ed.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aklc.ed.dao.UserDAO;
import com.aklc.ed.model.User;
import com.aklc.ed.notification.SendSMS;

public class UserServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String req_type = req.getParameter("req_type");
		if (req_type.equals("register")) {

			// Step 1: Read the email, password, gender etc
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String fname = req.getParameter("fname");
			String lname = req.getParameter("lname");
			String gender = req.getParameter("gender");
			String mobile = req.getParameter("mobile");
			String address = req.getParameter("address");

			// Step 2: Write that entry to the database
			UserDAO dao = new UserDAO();
			User u = new User();
			u.setEmail(email);
			u.setPassword(password);
			u.setFirstname(fname);
			u.setLastname(lname);
			u.setGender(gender);
			u.setMobile(mobile);
			u.setAddress(address);

			try {
				dao.createAccount(u);
				SendSMS.trigger("Welcome to ED Portal. Your account is created", u.getMobile());
				resp.sendRedirect("register.jsp?msg=Registration Successful");
			} catch (Exception e) {
				resp.sendRedirect("register.jsp?msg=Registration Failed");
			}

		} else if (req_type.equals("login")) {

			String email = req.getParameter("email");
			String password = req.getParameter("password");
			UserDAO dao = new UserDAO();
			boolean result = false;
			try {
				result = dao.login(email, password);
				if (result) {
					User model = dao.getUserInformation(email);
					req.getSession().setAttribute("model", model);
					req.getSession().setAttribute("loggedin", "yes");
					SendSMS.trigger("You have been logged to ED portal", model.getMobile());
					resp.sendRedirect("welcome.jsp?msg=Welcome");
				} else {					
					resp.sendRedirect("login.jsp?msg=Invalid Credentials");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (req_type.equals("logout")) {
			req.getSession().invalidate();
			resp.sendRedirect("login.jsp?msg=Logged out");

		} else if (req_type.equals("updateprofile")) {

			// Step 1: Read the email, password, gender etc
			String email = req.getParameter("email");
			String fname = req.getParameter("fname");
			String lname = req.getParameter("lname");
			String gender = req.getParameter("gender");
			String mobile = req.getParameter("mobile");
			String address = req.getParameter("address");

			System.out.println(email);
			System.out.println(fname);
			System.out.println(lname);
			System.out.println(gender);
			System.out.println(mobile);
			System.out.println(address);

			// Step 2: Write that entry to the database
			UserDAO dao = new UserDAO();
			User u = new User();
			u.setEmail(email);
			u.setFirstname(fname);
			u.setLastname(lname);
			u.setGender(gender);
			u.setMobile(mobile);
			u.setAddress(address);

			try {
				dao.editProfile(u);
				req.getSession().removeAttribute("model");
				req.getSession().setAttribute("model", u);
				resp.sendRedirect("updateprofile.jsp?msg=Profile Updated");
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect("updateprofile.jsp?msg=Error while updating your profile: " + e.getMessage());
			}

		} else if (req_type.equals("deleteprofile")) {

			User model = (User) req.getSession().getAttribute("model");
			String email = model.getEmail();

			try {
				UserDAO dao = new UserDAO();
				dao.deleteProfile(email);
				resp.sendRedirect("login.jsp?msg=Deleted your profile");
			} catch (Exception e) {
				resp.sendRedirect("login.jsp?msg=Error while deleting profile");
			}

		} else if (req_type.equals("changepassword")) {
			String email = req.getParameter("email");
			String currentPassword = req.getParameter("currentPassword");
			String newPassword = req.getParameter("newPassword");
			UserDAO dao = new UserDAO();
			boolean result = false;
			try {
				System.out.println("Email: " + email);
				System.out.println("Current Password: " + currentPassword);
				System.out.println("New Password: " + newPassword);

				result = dao.changePassword(email, currentPassword, newPassword);

				System.out.println("Result = " + result);

				if (result) {
					resp.sendRedirect("changepassword.jsp?msg=Password Change Successful");
				} else {
					resp.sendRedirect("changepassword.jsp?msg=Current Password is wrong");
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect("changepassword.jsp?msg=Error while changing password: " + e.getMessage());

			}

		}

	}
}
