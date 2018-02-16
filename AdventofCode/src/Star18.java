import java.io.BufferedReader;
import java.io.FileReader;

public class Star18 {

	// Recursive function to get the length of a decompressed String
	public long getDecompressedString(int start, int end, String compressed_string) {
		long length_decompressd = 0;
		for(int i = start; i < end; i++) {
			// If a marker is found,
			if (compressed_string.charAt(i) == '(') {
				// Get the char# where ')' is found
				int endchar = i+1;
				while (compressed_string.charAt(endchar) != ')')
					endchar++;
				String[] marker_splitted = compressed_string.substring(i+1, endchar).split("x");
				
				// Recursively call getDecompressedString but with different indices
				length_decompressd += (Integer.parseInt(marker_splitted[1]) * (getDecompressedString(endchar+1, endchar + 1 + Integer.parseInt(marker_splitted[0]), compressed_string)));
				
				// Move i to the end of the marker and indicated duplicated characters
				i = endchar + Integer.parseInt(marker_splitted[0]);
			}
			// Otherwise, just add the character to the decompressed String
			else
				length_decompressd++;
		}
		return length_decompressd;
	}
		
	// Function that gets the decompressed length of the String in file file_name
	public long getDecompressedLength(String file_name) {
		// Get the compressed String
		String compressed_string = "";
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			compressed_string = br.readLine();
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		
		// Return the decompressed length of the String
		return getDecompressedString(0, compressed_string.length(), compressed_string);
	}

	
	public static void main(String[] argvs) {
		Star18 pgm = new Star18();
		System.out.println("Decompressed length = " + pgm.getDecompressedLength("Star17_input.txt"));
	}
}
