package com.tavant.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class EmailUtils {

	public static void main(String[] args) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
		VelocityEngine ve = new VelocityEngine();
        ve.init();
        
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();

        map.put("name", "Cow");
        map.put("price", "$100.00");
        list.add( map );
 
        map = new HashMap<String, String>();
        map.put("name", "Eagle");
        map.put("price", "$59.99");
        list.add( map );

        map = new HashMap<String, String>();
        map.put("name", "Shark");
        map.put("price", "$3.99");
        list.add( map );
        
        VelocityContext context = new VelocityContext();
        context.put("petList", list);

        StringWriter writer = new StringWriter();
        ve.evaluate(context, writer, "", FileUtils.readFileToString(new File("C:\\Users\\abhay.jain\\git\\SpringRestful\\EmailTemplates\\test.tpl"), "UTF-8")); 
        
        System.out.println( writer.toString() );
	}
	
}