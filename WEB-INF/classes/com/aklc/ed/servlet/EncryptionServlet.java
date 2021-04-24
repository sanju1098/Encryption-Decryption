package com.aklc.ed.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aklc.ed.crypto.AES;
import com.aklc.ed.dao.EncryptionHistoryDAO;
import com.aklc.ed.dao.UserDAO;
import com.aklc.ed.model.EncryptionHistory;
import com.aklc.ed.model.User;
import com.aklc.ed.notification.SendSMS;
import com.oreilly.servlet.MultipartRequest;

public class EncryptionServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String req_type = req.getParameter("req_type");
		User uModel = (User) req.getSession().getAttribute("model");
		UserDAO uDao = new UserDAO();
		EncryptionHistoryDAO eDao = new EncryptionHistoryDAO();

		if (req_type.equals("plaintext")) {
			try {
				String text = req.getParameter("text");
				String secretKey = uDao.getSecretKet(uModel.getEmail());

				EncryptionHistory eModel = new EncryptionHistory();
				eModel.setEmail(uModel.getEmail());
				eModel.setEncryption_type("PLAIN TEXT");
				eModel.setEntry_time(new Timestamp(System.currentTimeMillis()));
				eDao.write(eModel);

				String encText = AES.encrypt(text, secretKey);
				SendSMS.trigger("Encryption for your plain text is successful", uModel.getMobile());
				resp.sendRedirect("encryption.jsp?encText=" + encText);

			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect("encryption.jsp?msg=Something went wrong: " + e.getMessage());
			}
		} else if (req_type.equals("textfile")) {
			try {
				MultipartRequest m = new MultipartRequest(req, "C:/upload");

				File folder = new File("C:/upload");
				File[] files = folder.listFiles();
				File f1 = files[0];
				String outputFileName = String.valueOf(System.currentTimeMillis());
				File f2 = new File("C:\\encrypt\\" + outputFileName + ".txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f1)));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f2)));

				String curLine = "";
				String secret = uDao.getSecretKet(uModel.getEmail());
				while ((curLine = br.readLine()) != null) {
					String encryptedLine = AES.encrypt(curLine, secret);
					bw.write(encryptedLine);
					bw.newLine();
				}

				br.close();
				bw.close();

				f1.delete();

				EncryptionHistory ehModel = new EncryptionHistory();
				ehModel.setEmail(uModel.getEmail());
				ehModel.setEntry_time(new Timestamp(System.currentTimeMillis()));
				ehModel.setEncryption_type("TEXT FILE");

				eDao.write(ehModel);

				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				String filename = outputFileName + ".txt";
				String filepath = "C:/encrypt/";
				resp.setContentType("APPLICATION/OCTET-STREAM");
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

				FileInputStream fileInputStream = new FileInputStream(filepath + filename);

				int i;
				while ((i = fileInputStream.read()) != -1) {
					out.write(i);
				}
				fileInputStream.close();
				out.close();
				SendSMS.trigger("Encryption for your text file is successful", uModel.getMobile());
				

			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect("encryption.jsp?msg=Something went wrong");
			}
		}

	}

}
