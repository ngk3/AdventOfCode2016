import java.util.HashMap;

public class Star37 {
	
	// Function that gets the next valid elf to be removed from intherunning
	public int getNextExistingIndex(int current_index, HashMap<Integer, Integer> intherunning, int num_elfs) {
		// The new current index of the elf to be returned
		int new_index = current_index;
		
		// Check if the index needs to loop
		if (current_index > num_elfs) {
			// Find next valid index, taking in condition if new_index is a valid index
			new_index = 1;
			if (intherunning.containsKey(1))
				return 1;
			else {
				new_index = 2;
				while (!intherunning.containsKey(new_index))
					new_index++;
			}
			return new_index;
		}
		else {
			// Find next valid index, taking in condition if a loop happens
			new_index++;
			while (!intherunning.containsKey(new_index) && new_index <= num_elfs)
				new_index++;
			if (new_index > num_elfs)
				return getNextExistingIndex(new_index, intherunning, num_elfs);
			return new_index;
		}
	}

	// Function that runs the fake white elephant given the number of elfs participating
	public int runFakeWhiteElephant(int num_elfs) {
		// Initializes the initial elfs
		HashMap<Integer, Integer> intherunning = new HashMap<Integer, Integer>();
		for (int i = 1; i <= num_elfs; i++)
			intherunning.put(i, i);
		
		// Keep removing from intherunning (every other, looping) until one elf is left
		int j = 2;
		while (intherunning.size() > 1) {
			intherunning.remove(j);
			j = getNextExistingIndex(j, intherunning, num_elfs);
			j = getNextExistingIndex(j, intherunning, num_elfs);
		}
		
		// Get the elf number that is left and return it
		int returning = 0;
		for (Integer i : intherunning.keySet())
			returning = i;
		return returning;
	}

	public static void main(String[] argvs) {
		Star37 pgm = new Star37();
		System.out.println("Elf winner = " + pgm.runFakeWhiteElephant(3004953));
	}
}
