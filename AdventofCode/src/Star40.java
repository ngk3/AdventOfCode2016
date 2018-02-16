import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class Star40 {

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

	// Function that looks for the lowest non blocked ip above lowest_start
	public long getLowestNonBlockedIP(HashSet<String> blacklist, long lowest_start) {
		// Get the lowest upper boundary of ips in the blacklist
		long lower_boundary = 4294967295L;
		long lowest_high_boundary = 0;
		for (String s : blacklist) {
			String[] s_splitted = s.split("-");
			if (Long.parseLong(s_splitted[0]) < lower_boundary && Long.parseLong(s_splitted[0]) >= lowest_start) {
				lower_boundary = Long.parseLong(s_splitted[0]);
				lowest_high_boundary = Long.parseLong(s_splitted[1]);
			}
		}		
			
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

	//Function that gets the total number of allowed ips
	public long getNumAllowedIPs(String filename) {
		// Get the blacklist and initiate the range of allowed values
		HashSet<String> blacklist = readFileandGetBlackList(filename);
		
		// Continuously get the range of values allowed until the end is reached
		long begin_value = 0;
		long count = 0;
		while (begin_value < 4294967295L) {
			// Get the lowest allowed value based on begin_value
			begin_value = getLowestNonBlockedIP(blacklist, begin_value);
			
			// Get the range of allowed values based on begin_value
			long lower_boundary = 4294967295L;
			for (String s : blacklist) {
				long check_value = Long.parseLong(s.split("-")[0]);
				if (check_value < lower_boundary && check_value > begin_value)
					lower_boundary = check_value;
			}
			count += lower_boundary - begin_value;
		}
		
		// Add 1 for inclusion
		return count + 1;
	}

	public static void main(String[] argvs) {
		Star40 pgm = new Star40();
		System.out.println("Total num of allowed IPs =  " + pgm.getNumAllowedIPs("Star39_input.txt"));
	}
}
