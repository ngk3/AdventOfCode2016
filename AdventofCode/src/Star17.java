import java.io.BufferedReader;
import java.io.FileReader;

public class Star17 {
		
	// Function that gets the decompressed length of the String in file file_name
	public int getDecompressedLength(String file_name) {
		// Get the compressed String
		String compressed_string = "";
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			compressed_string = br.readLine();
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		
		// Go through each character in the compressed String
		String decompressed_string = "";
		for(int i = 0; i < compressed_string.length(); i++) {
			// If a marker is found,
			if (compressed_string.charAt(i) == '(') {
				// Get the char# where ')' is found
				int endchar = i+1;
				while (compressed_string.charAt(endchar) != ')')
					endchar++;
				String[] marker_splitted = compressed_string.substring(i+1, endchar).split("x");
				
				// Find the String to duplicate-add into decompressed_string and add it the indicated number of times
				String dup = compressed_string.substring(endchar+1, endchar + 1 + Integer.parseInt(marker_splitted[0]));
				for (int j = 0; j < Integer.parseInt(marker_splitted[1]); j++)
					decompressed_string += dup;
				
				// Move i to the end of the marker and indicated duplicated characters
				i = endchar + Integer.parseInt(marker_splitted[0]);
			}
			// Otherwise, just add the character to the decompressed String
			else
				decompressed_string += compressed_string.charAt(i);
		}
		
		// Return the length of the decompressed String
		return decompressed_string.length();
	}
		
	public static void main(String[] argvs) {
		Star17 pgm = new Star17();
		System.out.println("Decompressed length = " + pgm.getDecompressedLength("Star17_input.txt"));
	}
}
