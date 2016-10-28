package com.tavant.controller;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.tavant.pojo.LoginInfo;

public class LoginThread implements Runnable {

	@Override
	public void run() {
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