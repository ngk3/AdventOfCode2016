import java.io.*;

public class Star1 {
	
	// Function used to read the input file and determine the end location
	public static int readInputFile(String file_name) {
		// current_direction = 0 --> North, current_direction = 1 --> East
		// current_direction = 2 --> South, current_direction = 3 --> West
		int current_direction = 0;
		
		// Used to keep track of the coordinates
		int north = 0;
		int east = 0;
		
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
				
				// Get the amount of movement and change north and east accordingly
				int movement = Integer.parseInt(s.split("[LR]")[1]);
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
		
		// Return the distance (in blocks) from the start point to the headquarters
		return Math.abs(north) + Math.abs(east);
	}
	
	public static void main(String[] args) {
		System.out.println("Number of blocks away: " + readInputFile("Star1_input.txt"));
	}
}
