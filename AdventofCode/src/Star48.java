import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Star48 {

	// Function that reads the file and generate a HashMap of coordinates of non-wall places
	public HashMap<String, String> readFileandGenerateMapCoordinates(String file_name) {
		HashMap<String, String> mapcoord = new HashMap<String, String>();
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			
			int row = 0;
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] line_splitted = line.split("");
				for (int col = 0; col < line_splitted.length; col++) {
					if (line_splitted[col].equals("#"))
						continue;
					mapcoord.put(row + "," + col, line_splitted[col]);
				}
				row++;
			}
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		
		return mapcoord;
	}

	// Function used to get all the permutations of the order to visit each number recursively
	public void getPermutations(String number_start, ArrayList<String> numbers_add, HashMap<String, Integer> number_perm) {
		// If all numbers are added, add the permutation to number_perm
		if (numbers_add.size() == 0)
			number_perm.put(number_start + ",0", 0);
		else {
			// Otherwise, recursively get all the permutation additions
			for (int i = 0; i < numbers_add.size(); i++) {
				ArrayList<String> copy_numbers_add = new ArrayList<String>(numbers_add);
				String number_start_added = numbers_add.get(i);
				copy_numbers_add.remove(i);
				getPermutations(number_start + "," + number_start_added, copy_numbers_add, number_perm);
			}
		}
	}

	// Function used to get the fewest steps to visit every number starting at 0
	public int getFewestSteps(String file_name) {
		HashMap<String, String> mapcoords = readFileandGenerateMapCoordinates(file_name);

		// Get the map coordinates of the numbers
		HashMap<String, String> numbers = new HashMap<String, String>();
		for (String s : mapcoords.keySet()) {
			if (mapcoords.get(s).matches("[0-9]")) {
				numbers.put(mapcoords.get(s), s);
			}
		}

		// Get the distances between every number. There are duplicates here but time complexity is such that it can be ignored
		HashMap<String, Integer> number_distances = new HashMap<String, Integer>();
		for (String num1 : numbers.keySet()) {
			for (String num2 : numbers.keySet()) {
				if (num1.equals(num2))
					continue;
				number_distances.put(num1 + "|" + num2, findPath(numbers.get(num1), numbers.get(num2), mapcoords));
			}
		}

		// Get all the number permutations to visit every step
		HashMap<String, Integer> number_permutations = new HashMap<String, Integer>();
		ArrayList<String> startinglist = new ArrayList<String>(numbers.keySet());
		startinglist.remove("0");
		getPermutations("0", startinglist, number_permutations);

		// Get the total steps needed for each permutation and set the value to the permutation
		for (String s : number_permutations.keySet()) {
			String[] s_splitted = s.split(",");
			for (int i = 0; i < s_splitted.length - 1; i++) {
				number_permutations.put(s, number_permutations.get(s) + number_distances.get(s_splitted[i] + "|" + s_splitted[i+1]));
			}
		}
		
		// Get the minimum value needed to visit every step
		int min = Integer.MAX_VALUE;
		for (String s : number_permutations.keySet()) {
			if (number_permutations.get(s) < min)
				min = number_permutations.get(s);
		}
		return min;
	}

	// Function that gets the number of steps needed to get from start to end using a bfs
	public int findPath(String start, String end, HashMap<String, String> mapcoords) {
		HashSet<String> visited = new HashSet<String>();
		Queue<ArrayList<String>> queue = new LinkedList<ArrayList<String>>();
		ArrayList<String> start_arr = new ArrayList<String>();
		start_arr.add(start);
		queue.add(start_arr);
		
		// Keep going until the queue is empty (path is not found), or the path is found
		while (queue.size() > 0) {
			ArrayList<String> popped = queue.poll();
			String last_visited = popped.get(popped.size() - 1);

			// Return path length if end is found. Keep going if coordinate is previously visited
			if (last_visited.equals(end))
				return popped.size() - 1;
			if (visited.contains(last_visited))
				continue;
			
			// Add current coordinate to visited and get the new possible places to visit next
			visited.add(last_visited);
			String[] last_visited_splitted = last_visited.split(",");
			int x = Integer.parseInt(last_visited_splitted[0]);
			int y = Integer.parseInt(last_visited_splitted[1]);
			String[] new_coords = new String[4];
			new_coords[0] = (x+1) + "," + y;
			new_coords[1] = (x-1) + "," + y;
			new_coords[2] = x + "," + (y+1);
			new_coords[3] = x + "," + (y-1);
			
			// Add the new coordinates to the queue as necessary and continue
			for (String s : new_coords) {
				if (mapcoords.containsKey(s)) {
					ArrayList<String> copy_popped = new ArrayList<String>(popped);
					copy_popped.add(s);
					queue.add(copy_popped);
				}
			}
		}
		
		return -1;
	}

	public static void main(String[] arvs) {
		Star48 pgm = new Star48();
		System.out.println("Fewest steps to visit every number starting at 0 and returning to 0 = " + pgm.getFewestSteps("Star47_input.txt"));
	}
}
