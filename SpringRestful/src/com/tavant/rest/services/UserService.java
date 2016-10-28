package com.tavant.rest.services;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.tavant.dao.UserDAO;
import com.tavant.model.User;
import com.tavant.utils.ResponseUtils;
import com.tavant.utils.SessionInfo;

@Component
@Path("/user")
public class UserService {

	@Context
	HttpServletRequest request;
	
	@Inject
	SessionInfo sessionInfo;
	
	@GET
	@Path("/get-user/{id}")
	public Response getUserDetails(@PathParam("id") String id) {
		Session session = null;
		try {
			session = sessionInfo.getSession();
			User user = UserDAO.getUserById(session, id);
			if(user == null)
				return ResponseUtils.sendResponse(500, "User not found");
			
			return ResponseUtils.sendResponse(200, new Gson().toJson(user));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.sendResponse(500, "User not found");
		} finally {
			SessionInfo.closeSession(session);
		}
	}
	
	public static void main(String[] args) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet("http://localhost:8080/SpringRestful/rest/user/get-user/2");
		
		try {
			CloseableHttpResponse response = client.execute(get);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}