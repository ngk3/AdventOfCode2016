import java.io.BufferedReader;
import java.io.FileReader;

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
	
	public static String[] determine_five_most_common_letters(String encrypt) {
		//String[] characters = new String[];
		return new String[10];
	}
	
	public static boolean check_match(String encrypt, String checksum) {
		return true;
	}
	
	public static void main(String[] argvs) {
		System.out.println(Character.getNumericValue('z'));
		System.out.println((char)65);
		for(int i = 0; i < 26; i++) {
			System.out.println((char)(65 + i));
		}
		// Characters are from 65 to 90
	}
}
