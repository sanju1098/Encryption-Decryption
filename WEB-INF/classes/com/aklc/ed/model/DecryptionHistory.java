package com.aklc.ed.model;

import java.sql.Timestamp;

public class DecryptionHistory {

	private String email;
	private String decryption_type;
	private Timestamp entry_time;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDecryption_type() {
		return decryption_type;
	}

	public void setDecryption_type(String decryption_type) {
		this.decryption_type = decryption_type;
	}

	public Timestamp getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Timestamp entry_time) {
		this.entry_time = entry_time;
	}

}
