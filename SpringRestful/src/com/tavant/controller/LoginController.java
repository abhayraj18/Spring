package com.tavant.controller;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tavant.dao.LoginDAO;
import com.tavant.errorhandling.ErrorHandler;
import com.tavant.pojo.LoginInfo;
import com.tavant.utils.SessionInfo;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	SessionInfo sessionInfo;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public String doLogin(@RequestBody LoginInfo loginInfo) throws Exception {
		Session session = null;
		try {
			session = sessionInfo.getSession();
			boolean isAuthenticated = LoginDAO.authenticateUser(session, loginInfo);
			JSONObject json = new JSONObject();
			json.put("message", "User authenticated successfully");
			
			if(isAuthenticated)
				return json.toString();
			else
				//throw new ErrorHandler("Invalid credentials");
				throw new Exception("Invalid credentials");
		}/* catch (Exception e) {
			e.printStackTrace();
			//throw new ErrorHandler(e.getMessage());
			throw e;
		} */finally {
			SessionInfo.closeSession(session);
		}
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
	
	public static void main(String[] args) {
		for(int i = 0; i < 1000; i++){
			Thread t = new Thread(new LoginThread());
			t.start();
		}
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost("http://localhost:8080/SpringRestful/spring-rest/login/doLogin");
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setPassword("abhay");
		loginInfo.setUserName("jain");
		
		/*JSONObject json = new JSONObject();
		json.put("userName", "abhay");
		json.put("password", "abhay");*/
		StringEntity entity = null;
		entity = new StringEntity(new Gson().toJson(loginInfo), "utf-8");
		entity.setContentType("application/json");
		post.setEntity(entity);
		try {
			CloseableHttpResponse response = client.execute(post);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}