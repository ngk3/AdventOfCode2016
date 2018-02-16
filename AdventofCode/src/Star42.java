import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Star42 {
	
	// Function that swaps the letter at index x and index y in input
	public void swapPosition(ArrayList<String> input, int x, int y) {
		String temp = input.get(x);
		input.set(x, input.get(y));
		input.set(y, temp);
	}

	// Function that swaps the letters x and y in input
	public void swapPosition(ArrayList<String> input, String x, String y) {
		swapPosition(input, input.indexOf(x), input.indexOf(y));
	}

	// Function that left rotates the letters in input by x
	public ArrayList<String> rotateLeft(ArrayList<String> input, int x) {
		// Adjust the size of x as necessarily 
		x = x % input.size();
		
		// Get the new indices for each letter
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int i = 0; i < input.size(); i++) {
			if (i - x < 0)
				index.add(input.size() + (i - x));
			else
				index.add(i - x);
		}
		
		// Build the new ArrayList and return it
		ArrayList<String> returning = new ArrayList<String>();
		for (int i = 0; i < input.size(); i++)
			returning.add(input.get(index.indexOf(i)));
		return returning;
	}

	// Function that right rotates the letters in input by x
	public ArrayList<String> rotateRight(ArrayList<String> input, int x) {
		// Adjust the size of x as necessary 
		x = x % input.size();
		
		// Get the new indices for each letter
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int i = 0; i < input.size(); i++) {
			if (i + x >= input.size())
				index.add(i + x - input.size());
			else
				index.add(i + x);
		}
		
		// Build the new ArrayList and return it
		ArrayList<String> returning = new ArrayList<String>();
		for (int i = 0; i < input.size(); i++)
			returning.add(input.get(index.indexOf(i)));
		return returning;
	}

	// Function that reverses the letters between index x and index y, including x and y
	public void reverse(ArrayList<String> input, int x, int y) {
		while (x < y) {
			String temp = input.get(x);
			input.set(x, input.get(y));
			input.set(y, temp);
			x++;
			y--;
		}
	}

	// Function that removes the letter at index x and insert it into index y
	public ArrayList<String> move(ArrayList<String> input, int x, int y) {
		String temp = input.get(x);
		input.remove(x);
		
		// Build a new ArrayList with the letter inserted
		ArrayList<String> returning = new ArrayList<String>();
		int tracker = 0;
		for (String s : input) {
			if (tracker == y) {
				returning.add(temp);
				tracker = -1;
			}
			// Need to add s no matter what
			returning.add(s);
			if (tracker != -1)
				tracker++;
		}
		
		// Take into account if index is at the end
		if (tracker != -1)
			returning.add(temp);
		return returning;
	}

	// Function that reads the file and get the reversed operations
	public ArrayList<String[]> readFileandGetReversedOperations(String filename) {
		ArrayList<String[]> operations = new ArrayList<String[]>();
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while ((line = br.readLine()) != null) {
				operations.add(line.split(" "));
			}
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		
		ArrayList<String[]> reversed_operations = new ArrayList<String[]>();
		for (int i = operations.size() - 1; i >= 0; i--)
			reversed_operations.add(operations.get(i));
		return reversed_operations;
	}

	// Function that takes in the operations filename and the input string and returns the scrambled string
	public String unscramble(String filename, String input) {
		// Setup and initialize values
		ArrayList<String[]> operations = readFileandGetReversedOperations(filename);
		ArrayList<String> input_arr = new ArrayList<String>(Arrays.asList(input.split("")));
		
		// Perform each operation
		for (String[] s_arr : operations) {
			// Swap function can stay the same in unscramble
			if (s_arr[0].equals("swap")) {
				if (s_arr[1].equals("letter"))
					swapPosition(input_arr, s_arr[2], s_arr[5]);
				else
					swapPosition(input_arr, Integer.parseInt(s_arr[2]), Integer.parseInt(s_arr[5]));
			}
			// Reverse function can stay the same in unscramble
			else if (s_arr[0].equals("reverse"))
				reverse(input_arr, Integer.parseInt(s_arr[2]), Integer.parseInt(s_arr[4]));
			// Rotate left will turn to right, Rotate right will turn to left
			else if (s_arr[0].equals("rotate")) {
				if (s_arr[1].equals("right"))
					input_arr = rotateLeft(input_arr, Integer.parseInt(s_arr[2]));
				else if (s_arr[1].equals("left"))
					input_arr = rotateRight(input_arr, Integer.parseInt(s_arr[2]));
				else {
					// Pattern found for this reversal shown below
					int index = input_arr.indexOf(s_arr[6]);
					if (index == 0)
						input_arr = rotateLeft(input_arr, 1);
					else if (index % 2 == 0)
						input_arr = rotateRight(input_arr, 3 - (index / 2));
					else
						input_arr = rotateLeft(input_arr, 1 + (index / 2));
				}
			}
			// Switch the parameters for move to unscramble
			else if (s_arr[0].equals("move"))
				input_arr = move(input_arr, Integer.parseInt(s_arr[5]), Integer.parseInt(s_arr[2]));
		}
		
		// Build the unscrambled String and return it
		String returning = "";
		for (String s : input_arr)
			returning += s;
		
		return returning;
	}

	public static void main(String[] argvs) {
		Star42 pgm = new Star42();
		System.out.println("Unscrambled string of fbgdceah = " + pgm.unscramble("Star41_input.txt", "fbgdceah"));
	}
}
