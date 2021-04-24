package com.aklc.ed.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.aklc.ed.model.DecryptionHistory;
import com.aklc.ed.util.DBConnection;

public class DecryptionHistoryDAO {

	public void write(DecryptionHistory model) throws Exception {
		Connection con = null;
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			st.execute(("insert into decryptionhistory values ('" + model.getEmail() + "', '"
					+ model.getDecryption_type() + "', '" + model.getEntry_time() + "') "));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			con.close();
		}
	}

	public List<DecryptionHistory> read(String email) throws Exception {
		Connection con = null;
		List<DecryptionHistory> result = new ArrayList<DecryptionHistory>();
		try {
			con = DBConnection.connect();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from decryptionhistory where email='" + email + "' ");
			while (rs.next()) {
				String type = rs.getString("decryption_type");
				Timestamp entry_time = rs.getTimestamp("entry_time");
				DecryptionHistory model = new DecryptionHistory();
				model.setEmail(email);
				model.setDecryption_type(type);
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
