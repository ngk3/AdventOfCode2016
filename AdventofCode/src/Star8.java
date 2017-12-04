import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Star8 {

	/*
	 * For visualization:
	 * 	aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common letters are a (5), b (3), and then a tie between x, y, and z, which are listed alphabetically.
	 */
	
	public static int sum_sector_ids = 0;
	
	// Function used to read the file and get the results
	public static void read_input(String file_name) {
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			
			while((line = br.readLine()) != null) {
				// Split the string up
				String[] splitted = line.split("[-\\[\\]]");
				String encrypt = "";
				for(int i = 0; i < splitted.length - 2; i++)
					encrypt += splitted[i];
				
				// Check if the room is real or a decoy and if the room is real...
				if (check_match(encrypt, splitted[splitted.length - 1])) {
					// Add into the sum of sector ids
					sum_sector_ids += Integer.parseInt(splitted[splitted.length - 2]);
					
					// Translate the String and check if it is the NORTHPOLE room
					String temp = "";
					for(int i = 0; i < splitted.length - 2; i++) {
						temp += translation(splitted[i], Integer.parseInt(splitted[splitted.length - 2]));
						temp += " ";
					}
					if (temp.contains("NORTH"))
						System.out.println(temp + "= " + splitted[splitted.length - 2]);
				}
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Function that rotates encrypt rotation letters forward with wrap-around
	public static String translation(String encrypt, int rotation) {
		// Get the numerical representation of each character in encrypt and rotate to its final destination
		Integer[] encrypt_to_int = new Integer[encrypt.length()];
		for (int i = 0; i < encrypt.length(); i++) {
			int new_value = Character.getNumericValue(encrypt.charAt(i)) - 10 + (rotation % 26);
			if (new_value > 25)
				new_value -= 26;
			encrypt_to_int[i] = new_value;
		}
		
		// Create the string from the rotation and return it 
		String result = "";
		for (int i = 0; i < encrypt.length(); i++)
			result += (char) (encrypt_to_int[i] + 65);
		return result;
	}
	
	// Function that finds the five most common letters in a String
	public static char[] determine_five_most_common_letters(String encrypt) {
		// characters represents each letter of the alphabet with A = 0 -> Z = 25
		HashMap<Integer, Integer> characters = new HashMap<Integer, Integer>();
		
		// Initialize characters to all have value of 0
		for(int i = 0; i < 26; i++)
			characters.put(i, 0);
		
		// Increment the frequency in characters (for Character.getNumericalValue(), A = 10 0> Z = 36)
		for(int i = 0; i < encrypt.length(); i++)
			characters.put(Character.getNumericValue(encrypt.charAt(i)) - 10, characters.get(Character.getNumericValue(encrypt.charAt(i)) - 10) + 1);
		
		// Get the frequencies, sort, and reverse
		List<Integer> frequency = new ArrayList<Integer>(characters.values());
		Collections.sort(frequency);
		Collections.reverse(frequency);
		
		// Get the five letters with the frequencies with ties being broken by alphabetical order
		Integer[] five_most_common = new Integer[5];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 26; j++) {
				if (characters.get(j) == frequency.get(i)) {
					five_most_common[i] = j;
					characters.put(j, -1);
					break;
				}
			}
		}
		
		// Translate the characters back into char, sort, and return
		char[] five_characters = new char[5];
		for (int i = 0; i < 5; i++)
			five_characters[i] = (char) (65 + five_most_common[i]);
		Arrays.sort(five_characters);
		return five_characters;
	}
	
	// Function used to check if the five most frequent letters in encrypt matches the letters in the checksum
	// Return true if so and false otherwise
	public static boolean check_match(String encrypt, String checksum) {
		// Translate the checksum into an array and make it all Upper Case
		char[] checksum_array = checksum.toUpperCase().toCharArray();
		
		// Sort the array as ties are broken by alphabetical position
		Arrays.sort(checksum_array);
		
		// Check that the letters match 
		int i = 0;
		for (char c : determine_five_most_common_letters(encrypt)) {
			if (!(checksum_array[i] == c))
				return false;
			i++;
		}
		return true;
	}
	
	// Function to return the sum of the sector ids found
	public static int get_sum_sector_ids() {return sum_sector_ids;}
	
	public static void main(String[] args) 
	{	
		read_input("Star7_input.txt");
	}
}
