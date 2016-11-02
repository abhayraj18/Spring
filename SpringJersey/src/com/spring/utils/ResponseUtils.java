package com.spring.utils;

import javax.ws.rs.core.Response;

public class ResponseUtils {

	public static Response sendResponse(int statusCode, String errorMessage) {
		return Response.status(statusCode).entity(errorMessage).build();
	}
	
}