
public class Star31 {
	
	// Function used to reverse the string order and return the reversed string
	public String reverse(String s) {
		String reverse_string = "";
		for (int i = 1; i <= s.length(); i++)
			reverse_string += s.charAt(s.length() - i);		
		return reverse_string;
	}

	// Function used to inverse a binary string and return the inversed string
	public String inverse(String s) {
		String inverse_string = "";
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == '0')
				inverse_string += '1';
			else 
				inverse_string += '0';
		return inverse_string;
	}

	// Function to fill the disk to fill_length size
	public String fill_disk(String puz_input, int fill_length) {
		// While the length is not > fill_length, concatenate a "0" and the inverse of the reverse of the current string
		while (puz_input.length() < fill_length)
			puz_input += "0" + inverse(reverse(puz_input));
		// Return the string only up to fill_length size
		return puz_input.substring(0, fill_length);
	}

	// Function to generate the checksum from the filled disk	
	public String getCheckSum(String disk) {
		// Keep going until a checksum is found to be odd
		while (disk.length() % 2 == 0) {
			// Create the checksum value from the current disk based on the ruleset
			String checksum = "";
			for(int i = 0; i < disk.length(); i+=2) {
				if (disk.charAt(i) == disk.charAt(i+1))
					checksum += "1";
				else
					checksum += "0";
			}
			// Set the new current disk to be the checksum value
			disk = checksum;
		}
		return disk;
	}

	public static void main(String[] argvs) {
		Star31 pgm = new Star31();
		System.out.println("Dragon Checksum = " + pgm.getCheckSum(pgm.fill_disk("11101000110010100", 272)));
	}
}
