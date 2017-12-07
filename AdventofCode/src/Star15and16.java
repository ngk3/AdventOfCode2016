import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Star15and16 {
	
	// Representing the screen (Coordinates => on/off)
	private static HashMap<String, Boolean> screen;
		
	// Function used to initially initialize the screen to be all false
	public static void initializeScreen() {
		screen = new HashMap<String, Boolean>();
		for (int i = 0; i < 50; i++)
			for (int j = 0; j < 6; j++)
				screen.put(i + " " + j, false);
	}
	
	// Function used to turn a AxB rectangle of lights on starting from the top-left corner 
	public static void rect(int a, int b) {
		for (int i = 0; i < a; i++)
			for (int j = 0; j < b; j++)
				screen.put(i + " " + j, true);
	}

	// Function used to rotate the row pos num places forward with wraparound
	public static void rotateRow(int pos, int num) {
		// row stores the new placements
		HashMap<Integer, Boolean> row = new HashMap<Integer, Boolean>();
		for (int i = 0; i < 50; i++) {
			// Check if wraparound and account for it if so. Otherwise, just set the value num spaces forward
			if (i + num >= 50)
				row.put((i+num) - 50, screen.get(i + " " + pos));
			else
				row.put(i+num, screen.get(i + " " + pos));
		}
		// Set the screen
		for (int i = 0; i < 50; i++)
			screen.put(i + " " + pos, row.get(i));
	}
	
	// Function used to rotate the column pos num places forward with wraparound
	public static void rotateCol(int pos, int num) {
		// col stores the new placements
		HashMap<Integer, Boolean> col = new HashMap<Integer, Boolean>();
		for (int i = 0; i < 6; i++) {
			// Check if wraparound and account for it if so. Otherwise, just set the value num spaces forward
			if (i + num >= 6)
				col.put((i+num) - 6, screen.get(pos + " " + i));
			else
				col.put(i+num, screen.get(pos + " " + i));
		}
		// Set the screen
		for (int i = 0; i < 6; i++)
			screen.put(pos + " " + i, col.get(i));
	}
	
	// Function used to read a file and follow the instructions, changing screen
	public static void readFileAndFollowInstructions(String file_name) {
		initializeScreen();
		try {
			// Open the file and read through each instruction, performing each one as necessary
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while ((line= br.readLine()) != null) {
				String[] splitted = line.split(" ");
				
				if (splitted[0].equals("rect")) {
					String[] ab = splitted[1].split("x");
					rect(Integer.parseInt(ab[0]), Integer.parseInt(ab[1]));
				}
				else {
					if (splitted[1].equals("row"))
						rotateRow(Integer.parseInt(splitted[2].split("y=")[1]), Integer.parseInt(splitted[4]));
					else
						rotateCol(Integer.parseInt(splitted[2].split("x=")[1]), Integer.parseInt(splitted[4]));
				}
			}
			
			br.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	// Function used to get the number of lights that are on
	public static int getLights() {
		int count = 0;
		for (int i = 0; i < 50; i++)
			for (int j = 0; j < 6; j++)
				if (screen.get(i + " " + j))
					count++;
		return count;
	}
	
	// Function used to print the screen
	public static void print() {
		for (int i = 0; i < 6; i++) {
			String temp = "";
			for (int j = 0; j < 50; j++) {
				boolean value = screen.get(j + " " + i);
				if (value) 
					temp += "#";
				else
					temp += ".";
			}
			System.out.println(temp);
		}
		System.out.println();
	}
	
	public static void main(String[] argvs) {
		readFileAndFollowInstructions("Star15_input.txt");
		System.out.println(getLights());
		print();
	}
}
