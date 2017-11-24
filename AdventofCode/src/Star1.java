import java.io.*;
import java.lang.Math.*;

public class Star1 {
	
	public static int readInputFile(String file_name) {
		String[] directions = {"North", "East", "South", "West"};
		int current_direction = 0;
		int north = 0;
		int east = 0;
		
		String line = null;
		
		try {
			FileReader fr = new FileReader(file_name);
			
			BufferedReader br = new BufferedReader(fr);
			line = br.readLine();
	
			for (String s : line.split(", ")) {
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
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Math.abs(north) + Math.abs(east);
	}
	
	public static void main(String[] args) {
		System.out.println("Number of blocks away: " + readInputFile("Star1_input.txt"));
	}
}
