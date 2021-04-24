package com.aklc.ed.notification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMS {
	public static String trigger(String msg, String num) {
		try {
			// Construct data
			String apiKey = "apikey=" + "";
			String message = "&message=" + msg;
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=" + "91" + num;

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();

			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
	}
	
	public static void main (String arg[]) throws Exception 
	{
		String result = trigger("Sample SMS", "9742024066");
		System.out.println(result);
	}
}
