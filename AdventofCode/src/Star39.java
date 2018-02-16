import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class Star39 {

	// Function that takes in the filename and returns a Hashset of the IP blacklist strings
	public HashSet<String> readFileandGetBlackList(String filename) {
		HashSet<String> blacklist = new HashSet<String>();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null)
				blacklist.add(line);
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		return blacklist;
	}

	// Function that gets the lowest non-blocked IP address given the input file
	public long getLowestNonBlockedIP(String filename) {
		// Get the blacklist and the lowest upper boundary of ips in the blacklist
		HashSet<String> blacklist = readFileandGetBlackList(filename);
		long lower_boundary = 4294967295L;
		long lowest_high_boundary = 0;
		for (String s : blacklist) {
			String[] s_splitted = s.split("-");
			if (Long.parseLong(s_splitted[0]) < lower_boundary) {
				lower_boundary = Long.parseLong(s_splitted[0]);
				lowest_high_boundary = Long.parseLong(s_splitted[1]);
			}
		}
		
		// Check case that the lowest ip is 0
		if (lower_boundary != 0)
			return 0;
		
		// Continously keep getting ip boundaries that include lowest_high_boundary until one is not found
		while (true) {
			boolean changed = false;
			for (String s : blacklist) {
				if (Long.parseLong(s.split("-")[0]) <= lowest_high_boundary + 1 && Long.parseLong(s.split("-")[1]) > lowest_high_boundary) {
					lowest_high_boundary = Long.parseLong(s.split("-")[1]);
					changed = true;
				}
			}
			
			// Return +1 because the ip boundaries are inclusive
			if (!changed)
				return lowest_high_boundary + 1;
		}
		
	}

	public static void main(String[] argvs) {
		Star39 pgm = new Star39();
		System.out.println("Lowest non-blocked IP = " + pgm.getLowestNonBlockedIP("Star39_input.txt"));
	}
}
