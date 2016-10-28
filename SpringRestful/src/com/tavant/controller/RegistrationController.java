package com.tavant.controller;

import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tavant.dao.RegistrationDAO;
import com.tavant.errorhandling.ErrorHandler;
import com.tavant.model.User;
import com.tavant.utils.SessionInfo;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	SessionInfo sessionInfo;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	@ResponseBody
	public String registerUser(@RequestBody User user) throws Exception {
		Session session = null;
		try {
			session = sessionInfo.getSession();
			boolean doesUserExist = RegistrationDAO.doesUserExist(session, user.getUserName());
			if(!doesUserExist)
				RegistrationDAO.createNewUser(session, user);
			else
				throw new Exception("User already exists with given username");
		}/* catch (Exception e) {
			e.printStackTrace();
			throw e;
		} */finally {
			SessionInfo.closeSession(session);
		}
		JSONObject json = new JSONObject();
		json.put("message", "Registration successful");
		return json.toString();
	}
	
	@ExceptionHandler(ErrorHandler.class)
	public ResponseEntity<ErrorHandler> exceptionHandler(Exception ex) {
		ex.printStackTrace();
		ErrorHandler error = new ErrorHandler();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getMessage());
		error.setStackTrace(ex.getStackTrace());
		return new ResponseEntity<ErrorHandler>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}