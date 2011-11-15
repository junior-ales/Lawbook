package br.com.lawbook.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author Edilson Luiz Ales Junior
 * @version 14NOV2011-03
 *  
 */
public class JavaUtil {
	
	public static void validateParameter(Object obj, String parameterName) throws IllegalArgumentException {
		if (obj == null) throw new IllegalArgumentException("Paramenter " + parameterName + " required");
		
		if (obj instanceof String) {
			if (((String) obj).trim().equals("")) throw new IllegalArgumentException("Paramenter " + parameterName + " required");
		}
	}
	
	public static String encode(String pass) throws NoSuchAlgorithmException, IllegalArgumentException {
		validateParameter(pass, "JavaUtil: encode: pass");

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(pass.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
}
