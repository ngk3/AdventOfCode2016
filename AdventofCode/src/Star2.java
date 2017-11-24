import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Star2 {

	// Function used to add all coordinates moving to next point into visited
	public static String addIntoHash(HashMap<String, Integer> visited, int north, int east, int current_direction, int movement) {
		
		// Go from (north, east) to the next appropriate coordinate and add all visited points into visited
		// Return any point that has been visited twice
		switch(current_direction) {
			case 0:		for (int i = 0; i < movement; i++) {
							String insert_string = (north + i) + "," + east;
							if (visited.containsKey(insert_string))
								return insert_string;
							visited.put(insert_string, 1);
						}
						break;
			case 1:		for (int i = 0; i < movement; i++) {
							String insert_string = north + "," + (east + i);
							if (visited.containsKey(insert_string))
								return insert_string;
							visited.put(insert_string, 1);
						}
						break;
			case 2:		for (int i = 0; i < movement; i++) {
							String insert_string = (north - i) + "," + east;
							if (visited.containsKey(insert_string))
								return insert_string;
							visited.put(insert_string, 1);
						}
						break;
			case 3:		for (int i = 0; i < movement; i++) {
							String insert_string = north + "," + (east - i);
							if (visited.containsKey(insert_string))
								return insert_string;
							visited.put(insert_string, 1);
						}
						break;
		}
		
		// Return null if no point has been crossed twice
		return null;
	}
	
	// Function used to read the input file and determine the end location
	public static int readInputFile(String file_name) {
		// current_direction = 0 --> North, current_direction = 1 --> East
		// current_direction = 2 --> South, current_direction = 3 --> West
		int current_direction = 0;
		
		// Used to keep track of the coordinates
		int north = 0;
		int east = 0;
		
		// Used to keep track of all the coordinates that have been visited
		HashMap<String, Integer> visited = new HashMap<String, Integer>();
		
		// Open the file and read through the movements
		String line = null;
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			line = br.readLine();

			// Split the input into separate directions and iterate through
			for (String s : line.split(", ")) {
				// Check which way you are turning and set the current_direction accordingly
				switch (s.charAt(0)) {
					case 'L': 	if (current_direction == 0)
									current_direction = 3;
								else
									current_direction -= 1;
							  	break;
					case 'R':	if (current_direction == 3)
									current_direction = 0;
								else
									current_direction += 1;
				  				break;	
				}

				
				int movement = Integer.parseInt(s.split("[LR]")[1]);
				
				// Add the visited coordinates into visited and check if any place has been crossed twice. Return result if so.
				String result = addIntoHash(visited, north, east, current_direction, movement);
				if (result != null) {
					br.close();
					return Math.abs(Integer.parseInt(result.split(",")[0])) + Math.abs(Integer.parseInt(result.split(",")[1]));
				}
				
				// Get the amount of movement and change north and east accordingly
				switch(current_direction) {
					case 0:		north += movement;
								break;
					case 1:		east += movement;
								break;
					case 2:		north -= movement;
								break;
					case 3:		east -= movement;
								break;
				}
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		// Returns -1 if no point has been found that has been crossed twice
		return -1;
	}
	
	public static void main(String[] args) {
		System.out.println("Number of blocks away: " + readInputFile("Star1_input.txt"));
	}
}
