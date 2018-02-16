import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Star21 {
	
	// Function that reads the file and get the number of items per floor
	public HashMap<Integer, Integer> readFileAndGetFloors(String file_name) {
		HashMap<Integer, Integer> floors = new HashMap<Integer, Integer>();
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			int counter = 1;
			
			while ((line = br.readLine()) != null) {
				floors.put(counter, line.replace(",", "").replace(" and ", "").replace(".", "").split("a ").length - 1);
				counter++;
			}
			
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		
		return floors;
	}

	// Function to get the total number of steps to move each object to the fourth floor 
	public int getNumStepsFourthFloor(HashMap<Integer, Integer> floors) {
		// To move each item up one floor takes a 2(num - 1) - 1 steps...
		int count = 0;
		for (int i = 1; i <= 3; i++) {
			int total_move = 0;
			for (int j = i; j >= 1; j--)
				total_move += floors.get(j);
			count += 2*(total_move - 1) - 1;
		}
		return count;
	}

	public static void main(String[] argvs) {
		Star21 pgm = new Star21();
		HashMap<Integer, Integer> floors = pgm.readFileAndGetFloors("Star21_input.txt");
		System.out.println("Number of steps to get all items to the 4th floor = " + pgm.getNumStepsFourthFloor(floors));
	}
}
