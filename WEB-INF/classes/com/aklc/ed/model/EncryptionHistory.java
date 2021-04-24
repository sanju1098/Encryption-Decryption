package com.aklc.ed.model;

import java.sql.Timestamp;

public class EncryptionHistory {

	private String email;
	private String encryption_type;
	private Timestamp entry_time;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryption_type() {
		return encryption_type;
	}

	public void setEncryption_type(String encryption_type) {
		this.encryption_type = encryption_type;
	}

	public Timestamp getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Timestamp entry_time) {
		this.entry_time = entry_time;
	}

}
