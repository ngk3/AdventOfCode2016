import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Star25 {
	// Part 1 Answer = 82	
	
	// Function to find the number of bits that are 1 in the binary representative of calculation
	public int calcBinaryNumberofOnes(int calculation) {
		String binary = Integer.toBinaryString(calculation);
		int ones = 0;
		for (String b : binary.split(""))
			if (b.equals("1"))
				ones++;
		return ones;
	}	

	// Function that checks if the location at (x,y) is a wall based on the ruleset
	public boolean isWall(int x, int y, int fav_num) {
		int calculation = (x*x) + (3*x) + (2*x*y) + y + (y*y);
		calculation += fav_num;
		if (calcBinaryNumberofOnes(calculation) % 2 == 0)
			return false;
		else
			return true;
	}

	// function used to get the fewest steps to get to (endx, endy) from (1,1) using a bfs
	public int getFewestSteps(int end_x, int end_y, int fav_num) {	
		// Setup the queue and visited
		Queue<ArrayList<String>> queue = new LinkedList<ArrayList<String>>();
		ArrayList<String> start = new ArrayList<String>();
		start.add("1,1");
		queue.add(start);
		
		HashSet<String> visited = new HashSet<String>();
		
		// If queue is empty, no path is found...
		while (queue.size() > 0) {
			// Get the path traveled thus far
			ArrayList<String> popped_list = queue.poll();
			String popped = popped_list.get(popped_list.size() - 1);
			
			// If destination has been reached, return the number of steps taken
			if (popped.equals(end_x + "," + end_y))
				return popped_list.size() - 1;
			
			// If previously visited, continue
			if (visited.contains(popped))
				continue;
			visited.add(popped);
			
			// Otherwise, get the coordinates if traveling N,S,E,W and add to queue if not a wall and valid coordinates (>0)
			String[] popped_splitted = popped.split(",");
			int popped_x = Integer.parseInt(popped_splitted[0]);
			int popped_y = Integer.parseInt(popped_splitted[1]);
			
			ArrayList<Integer[]> addQueue = new ArrayList<Integer[]>();
			addQueue.add(new Integer[]{popped_x + 1, popped_y});
			addQueue.add(new Integer[]{popped_x - 1, popped_y});
			addQueue.add(new Integer[]{popped_x, popped_y + 1});
			addQueue.add(new Integer[]{popped_x, popped_y - 1});
			
			for (Integer[] i : addQueue) {
				if (i[0] < 0 || i[1] < 0)
					continue;
				if (!isWall(i[0], i[1], fav_num)) {
					ArrayList<String> copy = new ArrayList<String>(popped_list);
					copy.add(i[0] + "," + i[1]);
					queue.add(copy);
				}
			}
		}
		
		return -1;
	}

	public static void main(String[] argvs) {
		Star25 pgm = new Star25();
		System.out.println("Fewest steps to get to (31,39) = " + pgm.getFewestSteps(31,39,1362));
	}
}
