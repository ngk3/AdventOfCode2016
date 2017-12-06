import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Star13 {
	
	// Function used to check if a string is ABBA
	public static boolean checkABBA(String s) {
		// Regex used to find ABBA
		Pattern p = Pattern.compile("(.)(.)\\2\\1");
		Matcher m = p.matcher(s);
		while (m.find()) {
			// Ensures that AAAA is not found
			HashSet<String> unique = new HashSet<String>(Arrays.asList(m.group().split("")));
			if (unique.size() == 2)
				return true;
		}
		return false;
	}
		
	// Function used to check if an IP is valid (ABBA outside of brackets, no ABBA inside brackets)
	public static boolean checkValidIP(String ip) {
		boolean found_valid = false;
		// Splits the string and go through each part (brackets every other one starting at i == 1)
		String[] splitted = ip.split("[\\[\\]]");
		for (int i = 0; i < splitted.length; i++) {
			// Tentatively is valid IP unless a ABBA inside bracket is found
			if (i%2 == 0 && !found_valid)
				found_valid = checkABBA(splitted[i]);
			// If there is ABBA inside brackets, IP is invalid
			else if (i%2 !=0 && checkABBA(splitted[i]))
				return false;
		}
		return found_valid;
	}
		
	// Function used to read a file and check that the IP is correct based on the TLS rules	
	public static int readFileTLS(String file_name) {
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
		System.out.println(readFileTLS("Star13_input.txt"));
	}
}
