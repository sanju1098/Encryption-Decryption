package com.aklc.ed.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.aklc.ed.model.EncryptionHistory;
import com.aklc.ed.util.DBConnection;

public class EncryptionHistoryDAO {

	public void write(EncryptionHistory model) throws Exception {
		Connection con = null;
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			st.execute(("insert into encryptionhistory values ('" + model.getEmail() + "', '"
					+ model.getEncryption_type() + "', '" + model.getEntry_time() + "') "));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			con.close();
		}
	}

	public List<EncryptionHistory> read(String email) throws Exception {
		Connection con = null;
		List<EncryptionHistory> result = new ArrayList<EncryptionHistory>();
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from encryptionhistory where email='" + email + "' ");
			while (rs.next()) {
				String type = rs.getString("encryption_type");
				Timestamp entry_time = rs.getTimestamp("entry_time");
				EncryptionHistory model = new EncryptionHistory();
				model.setEmail(email);
				model.setEncryption_type(type);
				model.setEntry_time(entry_time);
				result.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return result;
	}

}
