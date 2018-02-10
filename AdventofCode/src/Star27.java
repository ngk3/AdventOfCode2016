import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Star27 {
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
		
	public ArrayList<Integer> generateKeys(String puzzle_input) {
		ArrayList<Integer> returning = new ArrayList<Integer>();
		HashMap<Integer, Integer> track_thousand = new HashMap<Integer, Integer>();
		HashMap<Integer, String> track_character = new HashMap<Integer, String>();
		int tracker = 0;
		while (returning.size() < 64) {
			String temp = getMD5(puzzle_input + tracker);
			if (checkTriple(temp)) {
				track_thousand.put(tracker, 1000);
				track_character.put(tracker, getTripleCharacter(temp));
			}
				
			for (Integer i : new HashSet<Integer>(track_thousand.keySet())) {
				if (checkFive(temp, track_character.get(i))) {
					returning.add(i);
					track_thousand.put(i, 0);
				}
				else
					track_thousand.put(i, track_thousand.get(i) - 1);
					
				if (track_thousand.get(i) == 0) {
					track_thousand.remove(i);
					track_character.remove(i);
				}
			}
			tracker++;
		}
		return returning;
	}
		
	public boolean checkTriple(String s) {return s.matches(".*(.)\\1{2}.*");}
		
	public String getTripleCharacter(String s) {
		for (int i = 0; i < s.length() - 2; i++) {
			if (s.charAt(i) == s.charAt(i+1) && s.charAt(i+1) == s.charAt(i+2))
				return "" + s.charAt(i);
		}
		return "";
	}
		
	public boolean checkFive(String s, String triple) { return s.matches(".*(" + triple + ")\\1{4}.*");}
		
	public void testMain(Object[] args) {		
		ArrayList<Integer> generated_keys = generateKeys("abc");
		Collections.sort(generated_keys);
		for (int i = 0; i < 64; i++) {
			System.out.println(generated_keys.get(i));
		}
	}
}
