package com.tavant.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class ResponseUtils {

	public static Response sendResponse(int statusCode, String errorMessage) {
		return Response.status(statusCode).entity(errorMessage).build();
	}
	
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
        ve.evaluate(context, writer, "", FileUtils.readFileToString(new File("C:\\Users\\abhay.jain\\Workspace\\SpringRestful\\EmailTemplates\\test.tpl"), "UTF-8")); 
        
        System.out.println( writer.toString() );
        
        try{

		    KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		    SecretKey myDesKey = keygenerator.generateKey();

		    Cipher desCipher;

		    // Create the cipher
		    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		    // Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);

		    //sensitive information
		    byte[] text = "No body can see me".getBytes();
		    
		    System.out.println("Text [Byte Format] : " + text);
		    System.out.println("Text : " + new String(text));

		    // Encrypt the text
		    byte[] textEncrypted = desCipher.doFinal(text);

		    System.out.println("Text Encryted : " + textEncrypted);

		    // Initialize the same cipher for decryption
		    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);

		    // Decrypt the text
		    byte[] textDecrypted = desCipher.doFinal(textEncrypted);

		    System.out.println("Text Decryted : " + new String(textDecrypted));

		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(NoSuchPaddingException e){
			e.printStackTrace();
		}catch(InvalidKeyException e){
			e.printStackTrace();
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		}
	}

}