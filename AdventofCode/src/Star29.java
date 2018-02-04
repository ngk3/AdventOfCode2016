import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Star29 {

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

		return disks;
	}

	// Function that checks if the delay is valid in passing through all the disks
	public boolean checkValidDelay(int delay, HashMap<Integer, int[]> disks) {
		// Go through every disk
		for (Integer i : disks.keySet()) {
			// get the position that the disk will be in after time delay and check if the position is valid
			Integer new_pos = disks.get(i)[1] + delay + i;
			while(new_pos >= disks.get(i)[0])
				new_pos -= disks.get(i)[0];
			if (new_pos != 0)
				return false;
		}
		return true;
	}

	// Function used to find the time delay required for a capsule to appear given an input file
	public int findTimeDelayforCapsule(String file_name) {
		HashMap<Integer, int[]> disks = readFileandGetDisks(file_name);
		// Find the valid delay
		int delay = 0;
		while (!checkValidDelay(delay, disks)) {
			delay++;
		}
		return delay;
	}

	public static void main (String[] argvs) {
		Star29 pgm = new Star29();
		System.out.println("Time delay to get capsule = " + pgm.findTimeDelayforCapsule("Star29_input.txt"));
	}
}


