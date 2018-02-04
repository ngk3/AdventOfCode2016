import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Star30 {

	// Function that reads the input file, generate the disk information and return this
	public HashMap<Integer, int[] > readFileandGetDisks(String file_name) {
		// Key=Disk#, Value=[sizeOfdisk, currentPosition]
		HashMap<Integer, int[]> disks = new HashMap<Integer, int[]>();
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] line_splitted = line.replace("#", "").replace(".","").split(" ");
				ArrayList<Integer> line_splitted_data = new ArrayList<Integer>();

				line_splitted_data.add(Integer.parseInt(line_splitted[3]));
				line_splitted_data.add(Integer.parseInt(line_splitted[11]));
				disks.put(Integer.parseInt(line_splitted[1]), new int[]{Integer.parseInt(line_splitted[3]), Integer.parseInt(line_splitted[11])});
			}
			br.close();
		}
		catch (Exception e) {e.printStackTrace();}
		
		// For Part 2, add this disk
		disks.put(disks.size() + 1, new int[]{11, 0});
		
		return disks;
	}

	// Function used to increase the positions of the disks and checks if the positions are valid
	public boolean increasePosition(HashMap<Integer, int[]> disks, ArrayList<Integer> positions) {
		for (int i = 0; i < disks.size(); i++) {
			positions.set(i, positions.get(i) + 1);
			if (disks.get(i+1)[0] == positions.get(i))
				positions.set(i, 0);
		}
		return checkPositions(positions);
	}

	// Function that checks if each value in positions is valid
	public boolean checkPositions(ArrayList<Integer> positions) {
		for (int i = 0; i < positions.size(); i++)
			if (positions.get(i) != 0)
				return false;
		return true;	
	}

	// Function used to find the time delay required for a capsule to appear given an input file
	public int findTimeDelayforCapsule(String file_name) {
		// Key=Disk#, Value=[sizeOfdisk, currentPosition]
		HashMap<Integer, int[]> disks = readFileandGetDisks(file_name);

		// Get the absolute position that will be seen at each disk at delay 0
		int delay = 0;
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for (Integer i : disks.keySet())  {
			Integer new_pos = disks.get(i)[1] + i;
			while(new_pos >= disks.get(i)[0])
				new_pos -= disks.get(i)[0];
			positions.add(new_pos);
		}

		// Check initially if delay=0 is valid
		if (checkPositions(positions))
			return delay;
		
		// Keep increasing positions until a valid delay is found
		while (!increasePosition(disks, positions)) {
			delay++;
		}

		// Return delay+1, the true time of the delay
		return delay+1;
	}

	public static void main (String[] argvs) {
		Star30 pgm = new Star30();
		System.out.println("Time delay to get capsule = " + pgm.findTimeDelayforCapsule("Star29_input.txt"));
	}
}
