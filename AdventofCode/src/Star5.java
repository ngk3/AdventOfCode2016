import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Star5 {

	// Tracks the number of possible triangles found
	private static int possible_listed_triangles = 0;
	
	// Function that reads the files and set the number of possible triangles found accordingly
	public static void read_file(String file_name) {
		try {
			// Read the input file
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while ((line = br.readLine()) != null) {
				// If the triangle can exist, increment possible_listed_triangles
				if (checkTriangle(line))
					possible_listed_triangles++;
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Function that takes a string of the form "side1 side2 side3" and checks if a triangle is possible
	// through the sum of the two smaller sides being larger than the third side
	public static boolean checkTriangle(String data) {
		// Get the sides of the triangle and initialize the array that will store the int representation 
		String[] sides = data.trim().split("[ ]+");
		ArrayList<Integer> side_lengths = new ArrayList<Integer>();

		// Store the int version of the sides
		for (String s : sides)
			side_lengths.add(Integer.parseInt(s));
		
		// Sort the sides and return if the triangle is possible
		Collections.sort(side_lengths);
		return side_lengths.get(2) < (side_lengths.get(1) + side_lengths.get(0));
	}
	
	// Function used to get the number of possible triangles
	public static int get_possible_triangles() {
		return possible_listed_triangles;
	}
	
	public static void main(String[] argvs) {
		read_file("Star5_input.txt");
		System.out.println(get_possible_triangles());
	}
	
}
