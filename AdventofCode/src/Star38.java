
public class Star38 {

	// Function that runs the fake white elephant given the number of elfs participating
	public int runFakeWhiteElephant(int num_elfs) {
		// The pattern found here is that from the previous power of 3, the elf# that wins increases by 1 until elf# = previous power of 3.
		// Then the elf# increases by 2

		// Condition if num_elfs == 1 or 2
		if (num_elfs == 1 || num_elfs == 2)
			return 1;

		// Get the closest previous power of 3
		int closest_power3 = 3;
		while (closest_power3 * 3 < num_elfs)
			closest_power3 *= 3;
		
		// Check if num_elfs is a power of 3 and return num_elf if so
		if (closest_power3 * 3 == num_elfs)
			return closest_power3 * 3;
		
		// Find the winner based on the pattern previously mentioned
		int tracker = closest_power3 + 1;
		int last_elf = 1;
		
		while(tracker < num_elfs) {
			tracker++;
			if (last_elf > closest_power3)
				last_elf += 2;
			else
				last_elf++;
		}
		
		return last_elf;
		
	}

	public static void main(String[] argvs) {
		Star38 pgm = new Star38();
		System.out.println("Elf winner = " + pgm.runFakeWhiteElephant(3004953));
	}
}
