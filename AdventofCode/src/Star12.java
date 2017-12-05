import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Star12 {

	// Function used to decrypt the message with the frequency of the letters in positions of the input file
	public static String getMessage(String input_file) {
		// Used to track the frequencies of each letter
		Integer[] alphabet = new Integer[26];
		for (int i = 0; i < 26; i++)
			alphabet[i] = 0;
		ArrayList<Integer[]> frequency = new ArrayList<Integer[]>();
		for (int i = 0; i < 8; i++)
			frequency.add(alphabet.clone());


		try {
			// Go through the input file
			FileReader fr = new FileReader(input_file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while((line = br.readLine()) != null) {
				// Add to frequency based on position and letter
				for(int i = 0; i < line.length(); i++)
					frequency.get(i)[Character.getNumericValue(line.charAt(i)) - 10]++;
			}
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		
		// Return the string built from the lowest frequency in each index position
		String translation = "";
		for (int i = 0; i < 8; i++) {
			translation += get_lowest_frequency_letter(frequency.get(i));
		}
		
		return translation;
	}
	
	// Function that finds the lowestnumber and its position in an Integer array
	public static char get_lowest_frequency_letter(Integer[] freq) {
		int lowest = Integer.MAX_VALUE;
		int position = 0;
		for(int i = 0; i < 26; i++) {
			if (freq[i] < lowest) {
				lowest = freq[i];
				position = i;
			}
		}

		// Returns the character representation of the letter
		return (char) (65 + position);
	}
	
	public static void main(String[] argvs) {
		System.out.println(getMessage("star11_input.txt"));
	}
	
}
