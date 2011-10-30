package br.com.lawbook.util;


/**
 * @author Edilson Luiz Ales Junior
 * @version 29OCT2011-02
 *  
 */
public class JavaUtil {
	
	public static void validateParameter(Object obj, String parameterName) {
		if (obj == null) throw new IllegalArgumentException("Paramenter " + parameterName + " required");
		
		if (obj instanceof String) {
			if (((String) obj).trim().equals("")) throw new IllegalArgumentException("Paramenter " + parameterName + " required");
		}
	}
}
