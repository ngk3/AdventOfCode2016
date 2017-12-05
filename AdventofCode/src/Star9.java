import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Star9 {

	// Function used to get the 8-length passcode given an input string
	public static String getPassCode(String input) {
		String returning = "";
		int tracker = 0;
		while (returning.length() != 8) {
			String hashed = getMD5(input + tracker);
			if (hasFiveZeros(hashed))
				returning += hashed.charAt(5);
			tracker += 1;
		}
		return returning;
	}
		
	// Function used to check if the string has zeroas in the first 5 positions
	public static boolean hasFiveZeros(String input) {
		for (int i = 0; i < 5; i++) {
			if (input.charAt(i) != '0')
				return false;
		}
		return true;
	}
		
	// Function used to get the MD5 Hash of a String. Taken from "http://www.asjava.com/core-java/java-md5-example/"
	public static String getMD5(String input) {
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] messageDigest = md.digest(input.getBytes());
	        BigInteger number = new BigInteger(1, messageDigest);
	        String hashtext = number.toString(16);
	        // Now we need to zero pad it if you actually want the full 32 chars.
	        while (hashtext.length() < 32) {
	        	hashtext = "0" + hashtext;
	        }
	        return hashtext;
	    }
	    catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public static void main(String[] argvs) {
		System.out.println(getPassCode("cxdnnyjw"));
	}
}
