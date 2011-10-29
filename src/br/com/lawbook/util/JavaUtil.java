package br.com.lawbook.util;

/**
 * @author Edilson Luiz Ales Junior
 * @version 28OCT2011-01
 *  
 */
public class JavaUtil {

	public static void validateParameter(Object obj) {
		if (obj == null) throw new IllegalArgumentException("Paramenter required");
		
		if (obj instanceof String) {
			if (((String) obj).trim().equals("")) throw new IllegalArgumentException("Paramenter required");
		}
	}
}
