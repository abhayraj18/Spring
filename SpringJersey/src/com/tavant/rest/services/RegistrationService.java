package com.tavant.rest.services;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;

import com.tavant.dao.RegistrationDAO;
import com.tavant.model.User;
import com.tavant.utils.ResponseUtils;
import com.tavant.utils.SessionInfo;

@Controller
@Path("/register")
public class RegistrationService {

	@Context
	HttpServletRequest request;
	
	@Inject
	SessionInfo sessionInfo;
	
	@POST
	@Path("/register-user")
	public Response registerUser(User user) throws Exception {
		Session session = null;
		try {
			session = sessionInfo.getSession();
			boolean doesUserExist = RegistrationDAO.doesUserExist(session, user.getUserName());
			if(!doesUserExist)
				RegistrationDAO.createNewUser(session, user);
			else
				return ResponseUtils.sendResponse(500, "User already exists with given username");
		} finally {
			SessionInfo.closeSession(session);
		}
		return ResponseUtils.sendResponse(200, "Registration successful");
	}
	
}