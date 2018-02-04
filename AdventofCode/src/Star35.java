import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Star35 {
	
	// Function that reads the input file and generates the first row of the room
	public ArrayList<char[]> readFileandGetFirstRow(String file_name) {
		ArrayList<char[]> room = new ArrayList<char[]>();
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			
			char[] first_row = new char[line.length()];
			for (int i = 0; i < line.length(); i++)
				first_row[i] = line.charAt(i);
			room.add(first_row);
			
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		return room;
	}

	// Function used to check what the next tile will be according to the ruleset and given the left, middle, and right tiles
	public char getTile(char left, char middle, char right) {
		if (left == '^')
			if (right == '.' && (middle == '^' || middle == '.'))
				return '^';
			else
				return '.';
		else if (right == '^')
			if (left == '.' && (middle == '^' || middle == '.'))
				return '^';
			else
				return '.';
		else
			return '.';
	}

	// Function used to get the next row of the room given the previous row
	public char[] getNextRow(char[] previous_row) {
		char[] next_row = new char[previous_row.length];
		
		// First and last tile have to be found out slightly differently, otherwise iterate through tiles and add to the array
		next_row[0] = getTile('.', previous_row[0], previous_row[1]);
		for (int i = 1; i < previous_row.length - 1; i++)
			next_row[i] = getTile(previous_row[i - 1], previous_row[i], previous_row[i+1]);
		next_row[previous_row.length - 1] = getTile(previous_row[previous_row.length - 2], previous_row[previous_row.length - 1], '.');
		return next_row;
	}

	// Function used to generate the room given an input file and the number of rows to generate
	public ArrayList<char[]> generateRoom(String file_name, int num_rows) {
		ArrayList<char[]> room = readFileandGetFirstRow(file_name);
		
		// Generate num_rows rows
		for (int i = 0; i < num_rows - 1; i++)
			room.add(getNextRow(room.get(i)));
		return room;
	}

	// Function that counts the number of safe tiles in the room
	public int countSafeTiles(ArrayList<char[]> room) {
		int count = 0;
		for (char[] cr : room)
			for (char c : cr)
				if (c == '.')
					count += 1;
		return count;
	}

	public static void main(String[] argvs) {
		Star35 pgm = new Star35();
		System.out.println("Number of safe tiles = " + pgm.countSafeTiles(pgm.generateRoom("Star35_input.txt", 40)));
	}
	// Part 2 Answer = 19993564
	// No need to change previous code, call System.out.println(countSafeTiles(generateRoom("day18input.txt", 400000)))
}
