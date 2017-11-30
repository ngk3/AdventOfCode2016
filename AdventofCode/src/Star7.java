import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Integer[] determine_five_most_common_letters(String encrypt) {
		Integer[] five_most_common = new Integer[5];
		
		HashMap<Integer, Integer> characters = new HashMap<Integer, Integer>();
		for(int i = 0; i < 26; i++)
			characters.put(i, 0);
		
		for(int i = 0; i < encrypt.length(); i++)
			characters.put(Character.getNumericValue(encrypt.charAt(i)) - 10, characters.get(Character.getNumericValue(encrypt.charAt(i)) - 10) + 1);
		
		ArrayList<Integer> frequency = (ArrayList<Integer>) characters.values();
		Collections.sort(frequency);
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 26; j++) {
				if (characters.get(j) == frequency.get(i)) {
					five_most_common[i] = j;
					characters.put(j, -1);
				}
			}
		}
		
		return five_most_common;
	}
	
	public static boolean check_match(String encrypt, String checksum) {
		return true;
	}
	
	public static void main(String[] argvs) {
		System.out.println(Character.getNumericValue('a'));
		System.out.println((char)65);
		/*for(int i = 0; i < 26; i++) {
			System.out.println((char)(65 + i));
		}*/
		// Characters are from 65 to 90
	}
}
