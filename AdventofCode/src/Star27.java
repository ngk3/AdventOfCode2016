import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Star27 {	
	
	//Function used to get the MD5 Hash of a String. Taken from "http://www.asjava.com/core-java/java-md5-example/"
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

	// Function that gets the key_num hash index given a salt and the key_num
	public int getKeyIndex(String salt, int key_num) {
		HashMap<Integer, String> triples = new HashMap<Integer, String>();
		int countKeys = 0;
		int tracker = 0;
		
		while (true) {		
			String hash = getMD5(salt + tracker);
			
			// if a quintuple is found, check if any hashes are valid. Return when key_num hash is found
			if (checkFive(hash)) {
				String quin_char = getFiveCharacter(hash);
				ArrayList<Integer> sortedKeysList = new ArrayList<Integer>(triples.keySet());
				Collections.sort(sortedKeysList);
				for (Integer i : sortedKeysList) {
					if (triples.get(i).equals(quin_char)) {
						if (countKeys == key_num - 1)
							return i;
						countKeys++;
						triples.remove(i);
					}
				}
				triples.put(tracker, quin_char);
			}
			
			// If a triple is found, put it into triples
			else if (checkTriple(hash)) {
				triples.put(tracker, getTripleCharacter(hash));
			}
			
			// Remove any hash that has not been found and has passed 1000 mark
			for (Integer i : new HashSet<Integer>(triples.keySet())) {
				if (i + 1000 < tracker)
					triples.remove(i);
			}
			tracker++;
		}
	}

	// Function that checks if the String has a triple in it
	public boolean checkTriple(String s) {return s.matches(".*(.)\\1{2}.*");}

	// Function that gets the tripled character in the string
	public String getTripleCharacter(String s) {
		for (int i = 0; i < s.length() - 2; i++) {
			if (s.charAt(i) == s.charAt(i+1) && s.charAt(i+1) == s.charAt(i+2))
					return "" + s.charAt(i);
		}
		return "";
	}

	//Function that checks if the String has a quintuple in it
	public boolean checkFive(String s) { return s.matches(".*(.)\\1{4}.*");}

	//Function that gets the quintupled character in the string
	public String getFiveCharacter(String s) {
		for (int i = 0; i < s.length() - 4; i++) {
			if (s.charAt(i) == s.charAt(i+1) && s.charAt(i+1) == s.charAt(i+2) && s.charAt(i+2) == s.charAt(i+3) && s.charAt(i+3) == s.charAt(i+4))
					return "" + s.charAt(i);
		}
		return "";
	}

	public static void main(String[] argvs) {
		Star27 pgm = new Star27();
		System.out.println("Index to get the 64th key = " + pgm.getKeyIndex("yjdafjpo", 64));
	}
	
}
