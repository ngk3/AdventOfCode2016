import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Star41 {
	
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

	// Function that rotates input based on the index of x
	public ArrayList<String> rotate(ArrayList<String> input, String x) {
		// Set the rotation value as necessary
		int rotation = input.indexOf(x) + 1;
		if (rotation > 4)
			rotation += 1;
		// Rotate right by rotation
		return rotateRight(input, rotation);
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

	// Function that reads the file and get the operations
	public ArrayList<String[]> readFileandGetOperations(String filename) {
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
		return operations;
	}

	// Function that takes in the operations filename and the input string and returns the scrambled string
	public String scramble(String filename, String input) {
		// Setup and initialize values
		ArrayList<String[]> operations = readFileandGetOperations(filename);
		ArrayList<String> input_arr = new ArrayList<String>(Arrays.asList(input.split("")));
		
		// Perform each operation
		for (String[] s_arr : operations) {
			if (s_arr[0].equals("swap")) {
				if (s_arr[1].equals("letter"))
					swapPosition(input_arr, s_arr[2], s_arr[5]);
				else
					swapPosition(input_arr, Integer.parseInt(s_arr[2]), Integer.parseInt(s_arr[5]));
			}
			else if (s_arr[0].equals("reverse"))
				reverse(input_arr, Integer.parseInt(s_arr[2]), Integer.parseInt(s_arr[4]));
			else if (s_arr[0].equals("rotate")) {
				if (s_arr[1].equals("right"))
					input_arr = rotateRight(input_arr, Integer.parseInt(s_arr[2]));
				else if (s_arr[1].equals("left"))
					input_arr = rotateLeft(input_arr, Integer.parseInt(s_arr[2]));
				else
					input_arr = rotate(input_arr, s_arr[6]);
			}
			else if (s_arr[0].equals("move"))
				input_arr = move(input_arr, Integer.parseInt(s_arr[2]), Integer.parseInt(s_arr[5]));
		}
		
		// Build the scrambled String and return it
		String returning = "";
		for (String s : input_arr)
			returning += s;
		
		return returning;
	}

	public static void main(String[] argvs) {
		Star41 pgm = new Star41();
		System.out.println("Scrambled string of abcdefgh = " + pgm.scramble("Star41_input.txt", "abcdefgh"));
	}
}
