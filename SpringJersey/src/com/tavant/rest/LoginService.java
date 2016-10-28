package com.tavant.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.tavant.dao.LoginDAO;
import com.tavant.pojo.LoginInfo;
import com.tavant.utils.ResponseUtils;
import com.tavant.utils.SessionInfo;

@Component
@Path("/login")
public class LoginService {
	
	@Inject
	SessionInfo sessionInfo;
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/doLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doLogin(LoginInfo loginInfo){
		Session session = null;
		JSONObject json = new JSONObject();
		try {
			session = sessionInfo.getSession();
			boolean isAuthenticated = LoginDAO.authenticateUser(session, loginInfo);
			json.put("message", "Invalid credentials");
			
			if(!isAuthenticated)
				return ResponseUtils.sendResponse(500, json.toString());
			
			json.put("message", "User authenticated successfully");
			return ResponseUtils.sendResponse(200, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.sendResponse(500, json.toString());
		} finally {
			if(session != null)
				session.close();
		}
	}

}