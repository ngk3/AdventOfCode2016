import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Star6 {

	// Tracks the number of possible triangles found
	private static int possible_listed_triangles = 0;
	
	// Function that reads the files and set the number of possible triangles found accordingly
	public static void read_file(String file_name) {
		try {
			// Read the input file
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			
			// Used to get the three triangles (vertical)
			int count = 0;
			ArrayList<String> triangle_data = new ArrayList<String>();
			
			// Go through each line and get the triangle data in series of 3
			while ((line = br.readLine()) != null) {
				// If 3 lines have been read from the input...
				if (count == 3) {
					// If the triangle can exist, increment possible_listed_triangles
					possible_listed_triangles += checkTriangle(triangle_data.get(0), triangle_data.get(3), triangle_data.get(6));
					possible_listed_triangles += checkTriangle(triangle_data.get(1), triangle_data.get(4), triangle_data.get(7));
					possible_listed_triangles += checkTriangle(triangle_data.get(2), triangle_data.get(5), triangle_data.get(8));
					
					// Reset the data afterwards
					count = 0;
					triangle_data.clear();
				}
				
				// Get the values of the triangle and store in triangle_data
				for(String s : line.trim().split("[ ]+")) 
					triangle_data.add(s);
				count++;
			}
			
			// Check the last triangles data
			possible_listed_triangles += checkTriangle(triangle_data.get(0), triangle_data.get(3), triangle_data.get(6));
			possible_listed_triangles += checkTriangle(triangle_data.get(1), triangle_data.get(4), triangle_data.get(7));
			possible_listed_triangles += checkTriangle(triangle_data.get(2), triangle_data.get(5), triangle_data.get(8));
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Function that takes side1, side2, and side3 and checks if a triangle is possible
	// through the sum of the two smaller sides being larger than the third side
	public static int checkTriangle(String side1, String side2, String side3) {
		// Initialize the array that will store the int representation 
		ArrayList<Integer> side_lengths = new ArrayList<Integer>();

		// Store the int version of the sides
		side_lengths.add(Integer.parseInt(side1));
		side_lengths.add(Integer.parseInt(side2));
		side_lengths.add(Integer.parseInt(side3));

		// Sort the sides and return if the triangle is possible
		Collections.sort(side_lengths);
		if (side_lengths.get(2) < (side_lengths.get(1) + side_lengths.get(0)))
			return 1;
		else
			return 0;
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
