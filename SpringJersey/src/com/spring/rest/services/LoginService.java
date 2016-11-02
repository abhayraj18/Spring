package com.spring.rest.services;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.spring.dao.LoginDAO;
import com.spring.pojo.LoginInfo;
import com.spring.utils.ResponseUtils;
import com.spring.utils.SessionInfo;

@Component
@Path("/login")
public class LoginService {
	
	@Context
	HttpServletRequest request;
	
	@Inject
	SessionInfo sessionInfo;
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/do-login")
	public Response doLogin(LoginInfo loginInfo) throws Exception {
		Session session = null;
		try {
			session = sessionInfo.getSession();
			boolean isAuthenticated = LoginDAO.authenticateUser(session, loginInfo);
			JSONObject json = new JSONObject();
			json.put("message", "User authenticated successfully");
			
			if(isAuthenticated)
				return ResponseUtils.sendResponse(200, "User authenticated successfully");
			else
				return ResponseUtils.sendResponse(500, "Invalid credentials");
		} finally {
			SessionInfo.closeSession(session);
		}
	}

}