import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Star7 {
	
	/*
	 * For visualization:
	 * 	aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common letters are a (5), b (3), and then a tie between x, y, and z, which are listed alphabetically.
	 */
	
	public static int sum_sector_ids = 0; 
	
	public static void read_input(String file_name) {
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			
			while((line = br.readLine()) != null) {
				String[] splitted = line.split("[-\\[\\]]");
				String encrypt = "";
				for(int i = 0; i < splitted.length - 2; i++)
					encrypt += splitted[i];
				
				if (check_match(encrypt, splitted[splitted.length - 1]))
					sum_sector_ids += Integer.parseInt(splitted[splitted.length - 2]);
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static char[] determine_five_most_common_letters(String encrypt) {
		Integer[] five_most_common = new Integer[5];
		
		HashMap<Integer, Integer> characters = new HashMap<Integer, Integer>();
		for(int i = 0; i < 26; i++)
			characters.put(i, 0);
		
		for(int i = 0; i < encrypt.length(); i++)
			characters.put(Character.getNumericValue(encrypt.charAt(i)) - 10, characters.get(Character.getNumericValue(encrypt.charAt(i)) - 10) + 1);
		
		List<Integer> frequency = new ArrayList<Integer>(characters.values());
		Collections.sort(frequency);
		Collections.reverse(frequency);
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 26; j++) {
				if (characters.get(j) == frequency.get(i)) {
					five_most_common[i] = j;
					characters.put(j, -1);
					break;
				}
			}
		}
		
		char[] five_characters = new char[5];
		for (int i = 0; i < 5; i++)
			five_characters[i] = (char) (65 + five_most_common[i]);
		Arrays.sort(five_characters);
		return five_characters;
	}
	
	public static boolean check_match(String encrypt, String checksum) {
		char[] checksum_array = checksum.toUpperCase().toCharArray();
		Arrays.sort(checksum_array);
		int i = 0;
		for (char c : determine_five_most_common_letters(encrypt)) {
			if (!(checksum_array[i] == c))
				return false;
			i++;
		}
		return true;
	}
	
	public static int get_sum_sector_ids() {
		return sum_sector_ids;
	}
	
	public static void main(String[] argvs) {
		//System.out.println(Character.getNumericValue('a'));
		//System.out.println((char)65);
		/*for(int i = 0; i < 26; i++) {
			System.out.println((char)(65 + i));
		}*/
		// Characters are from 65 to 90
		
		read_input("Star7_input.txt");
		System.out.println(get_sum_sector_ids());
	}
}
	
