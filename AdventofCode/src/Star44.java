import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Star44 {
	
	// Class representing each node
	class Node {
		int size;
		int used;
		int avail;
		float use;
		
		public Node(String n_size, String n_used, String n_avail, String n_use) {
			size = Integer.parseInt(n_size);
			used = Integer.parseInt(n_used);
			avail = Integer.parseInt(n_avail);
			use = Float.parseFloat(n_use);
		}
		
		public Node(int max_x, int max_y) {
			size = max_x;
			used = max_y;
		}
	}

	// Comparator used for the PriorityQueue in a* search using manhattan distance
	class NodeCompare implements Comparator<ArrayList<String>> {
		@Override
		public int compare(ArrayList<String> x_arr, ArrayList<String> y_arr) {
			String x = x_arr.get(x_arr.size() - 1);
			String y = y_arr.get(y_arr.size() - 1);
			double x_dist = Math.sqrt(Math.pow(32 - Integer.parseInt(x.split(",")[0]), 2) + Math.pow(Integer.parseInt(x.split(",")[1]), 2));
			double y_dist = Math.sqrt(Math.pow(32 - Integer.parseInt(y.split(",")[0]), 2) + Math.pow(Integer.parseInt(y.split(",")[1]), 2));
			if (x_dist < y_dist)
				return -1;
			else
				return 1;
		}
	}

	// Function that reads the input file and creates a HashMap of nodes (coords = key)
	public HashMap<String, Node> readFileandBuildNodes(String filename) {
		HashMap<String, Node> nodes = new HashMap<String, Node>();
		int max_x = 0;
		int max_y = 0;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] line_splitted = line.replaceAll("[T%]", "").split("[ ]+");
				String[] node_coord_splitted = line_splitted[0].split("-");
				
				String x_str = node_coord_splitted[1].replace("x", "");
				String y_str = node_coord_splitted[2].replace("y", "");
				nodes.put(x_str + "," + y_str, new Node(line_splitted[1], line_splitted[2], line_splitted[3], line_splitted[4]));
				
				// Find the max x and max y values which is set to key "MAX"
				if (Integer.parseInt(x_str) > max_x) 
					max_x = Integer.parseInt(x_str);
				if (Integer.parseInt(y_str) > max_y) 
					max_y = Integer.parseInt(y_str);
			}
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		nodes.put("MAX", new Node(max_x, max_y));
		
		return nodes;
	}

	// Helper function used to copy an ArrayList and add the value add to the end of the ArrayList
	public ArrayList<String> copyArrayListandAdd(ArrayList<String> arr, String add) {
		ArrayList<String> returning = new ArrayList<String>();
		for (String s : arr)
			returning.add(s);
		returning.add(add);
		return returning;
	}

	// Function to find the path from the empty node to the node maxx,y0
	public int findPath(HashMap<String, Node> nodes, String empty_loc, int x_loc, int max_y) {
		// Initialize the priority queue using NodeCompare and begin path traversal
		PriorityQueue<ArrayList<String>> queue = new PriorityQueue<ArrayList<String>>(10, new NodeCompare());
		ArrayList<String> visited = new ArrayList<String>();
		
		ArrayList<String> start = new ArrayList<String>();
		start.add(empty_loc);
		queue.add(start);
		
		while (queue.size() > 0) {
			// Pop from queue and set up
			ArrayList<String> popped = queue.poll();
			String popped_string = popped.get(popped.size() - 1);
			String[] popped_splitted = popped_string.split(",");
			int popped_x = Integer.parseInt(popped_splitted[0]);
			int popped_y = Integer.parseInt(popped_splitted[1]);
			
			// Check if location is found
			if (popped_string.equals(x_loc + ",0"))
				return popped.size();
			// Check boundaries
			if (popped_x < 0 || popped_x > x_loc || popped_y < 0 || popped_y >= max_y)
				continue;
			// Check if visited previously
			if (visited.contains(popped_string))
				continue;
			// Add to visited
			visited.add(popped_string);
			// Check size
			if (nodes.get(popped.get(popped.size() - 1)).use > nodes.get(empty_loc).size)
				continue;

			// Add the neighboring node coordinates to the queue
			queue.add(copyArrayListandAdd(popped, (popped_x + 1) + "," + popped_splitted[1]));
			queue.add(copyArrayListandAdd(popped, (popped_x - 1) + "," + popped_splitted[1]));
			queue.add(copyArrayListandAdd(popped, popped_splitted[0] + "," + (popped_y + 1)));
			queue.add(copyArrayListandAdd(popped, popped_splitted[0] + "," + (popped_y - 1)));		
		}
		return 0;
	}

	// Function that gets the number of steps needed for the data to arrive at x0,y0
	public int getNumStepsForGoal(String filename) {
		// Build the nodes , get max x, and get the empty node
		HashMap<String, Node> nodes = readFileandBuildNodes(filename);
		int data_loc_x = nodes.get("MAX").size;
		String empty_key = "";
		
		for (String s : nodes.keySet()) {
			if (s.equals("MAX"))
				continue;
			if (nodes.get(s).used == 0) {
				empty_key = s;
				break;
			}
		}
		
		// The number of steps needed is the path from empty node to the node maxx,y0 and then 5 * (maxx - 1)
		return findPath(nodes, empty_key, data_loc_x - 1, nodes.get("MAX").used) + (5 * (data_loc_x - 1));
	}

	public static void main(String[] argvs) {
		Star44 pgm = new Star44();
		System.out.println("Min num of steps to get data to goal x0,y0 = " + pgm.getNumStepsForGoal("Star43_input.txt"));
	}
}
