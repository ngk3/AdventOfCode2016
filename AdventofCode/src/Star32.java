import java.util.ArrayList;

public class Star32 {  
	
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

	//Function to fill the disk to fill_length size
	public String fill_disk(String puz_input, int fill_length) {
		// Get the initial setup of the disk
		puz_input += "0" + inverse(reverse(puz_input));
		// Each Iteration is essentially puz_input + "0" + puz_input but with the second puz_input having a "1" in the middle and not a "0"
		while(puz_input.length() < fill_length) {
			String puz_input_addition = puz_input.substring(0, (puz_input.length() / 2)) + 
					'1' + puz_input.substring((puz_input.length() / 2) + 1, puz_input.length());
			puz_input += "0" + puz_input_addition;
		}
		// Return a string of fill_length size
		return puz_input.substring(0, fill_length);
	}

	// Function that gets the checksum of the disk
	public String getCheckSum(String disk) {
		// Note: This function uses a drastic amount of memory dependent on the size of the initial filled disk
		// Create an ArrayList storing each character of the disk	
		ArrayList<Character> check = new ArrayList<Character>(disk.length());
		for (int i = 0; i < disk.length(); i++) {
			check.add(disk.charAt(i));
		}
		
		// Iterate until the new checksum is found to be odd
		while (check.size() % 2 == 0) {
			// Create the new checksum ArrayList based on the ruleset
			ArrayList<Character> checksum = new ArrayList<Character>(disk.length() / 2);
			for(int i = 0; i < check.size(); i+=2) {
				if (check.get(i) == check.get(i+1)) {
					checksum.add('1');
				}
				else {
					checksum.add('0');
				}
			}
			check = checksum;
		}
		
		// Generate the checksum String to be returned
		String result = "";
		for (Character c : check) {
			result += c;
		}
		return result;
	}

	public static void main(String[] argvs) {
		Star32 pgm = new Star32();
		System.out.println(pgm.getCheckSum(pgm.fill_disk("11101000110010100", 35651584)));
	}
}
