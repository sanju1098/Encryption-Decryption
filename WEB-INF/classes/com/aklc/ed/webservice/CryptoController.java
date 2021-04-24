package com.aklc.ed.webservice;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.aklc.ed.crypto.AES;

@Path("/crypto")
public class CryptoController {

	@POST
	@Path("/encrypt")
	public Response encypt(@QueryParam("data") String data, @QueryParam("key") String key) {
		String result = null;
		try {
			result = AES.encrypt(data, key);
		} catch (Exception e) {
			result = "Error while encryption";
		}
		return Response.status(Status.OK).entity(result).build();
	}

	@POST
	@Path("/decrypt")
	public Response decypt(@QueryParam("data") String data, @QueryParam("key") String key) {
		String result = null;
		try {
			result = AES.decrypt(data, key);
		} catch (Exception e) {
			result = "Error while decryption";
		}
		return Response.status(Status.OK).entity(result).build();
	}

}
