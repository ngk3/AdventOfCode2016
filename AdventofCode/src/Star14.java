import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Star14 {
	
	// Function used to get all ABA substrings in s
	public static List<String> getABA(String s) {
		ArrayList<String> aba_found = new ArrayList<String>();
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("(.)(.)\\1");
		java.util.regex.Matcher m = p.matcher(s);
		int start = 0;
		while (m.find(start)) {
			aba_found.add(m.group());
			// To ensure overlapping
			start = m.start() + 1;
		}
		return aba_found;
	}
		
	// Function used to get all BAB strings based on the list of ABA strings	
	public static List<String> getBAB(List<String> aba) {
		ArrayList<String> bab_needed = new ArrayList<String>();
		for (String s : aba)
			bab_needed.add(s.charAt(1) + "" + s.charAt(0) + s.charAt(1));
		return bab_needed;
	}
		
	// Function that checks if a BAB string is found in s from a given list of potential BABs
	public static boolean matchBAB(String s, List<String> bab) {
		for (String b : bab) {
			if (s.matches(".*" + b + ".*"))
				return true;
		}
		return false;
	}
		
	// Function used to check if an IP is valid (ABA outside of brackets, BAB inside brackets)
	public static boolean checkValidIP(String ip) {
		List<String> aba = new ArrayList<String>();
		String[] splitted = ip.split("[\\[\\]]");
		// Get all potential ABAs
		for (int i = 0; i < splitted.length; i += 2)
			aba.addAll(getABA(splitted[i]));
		// Get all potential BABs and check if the conditions match for a valid IP
		List<String> bab = getBAB(aba);
		for (int i = 1; i < splitted.length; i += 2) {
			if (matchBAB(splitted[i], bab))
				return true;
		}
		return false;
	}
	
	// Function used to read a file and check that the IP is correct based on the SSL rules
	public static int readFileSSL(String file_name) {
		int count_valid = 0;
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while((line = br.readLine()) != null) {
				if (checkValidIP(line))
					count_valid++;
			}	
			br.close();
				
		} catch (Exception e) {e.printStackTrace();}
		return count_valid;
	}
	
	public static void main(String[] argvs) {
		System.out.println(readFileSSL("Star13_input.txt"));
	}
}
