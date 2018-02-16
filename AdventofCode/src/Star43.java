import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Star43 {

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
		
		// Function used to check if this node A is viable with the other B
		public boolean checkViable(Node other) {
			if (this.used == 0)
				return false;
			if (other.avail < this.used)
				return false;
			return true;
		}
	}

	// Function that reads the input file and creates a HashMap of nodes (coords = key)
	public HashMap<String, Node> readFileandBuildNodes(String filename) {
		HashMap<String, Node> nodes = new HashMap<String, Node>();
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
			}
			br.close();
		} catch (Exception e) {e.printStackTrace();}
		
		return nodes;
	}

	// Function that counts the number of viable pair nodes
	public int countNumViableNodes(String filename) {
		HashMap<String, Node> nodes = readFileandBuildNodes(filename);
		int count_viable = 0;
		
		// Compare each node to another besides itself to see viable nodes
		for (String key : nodes.keySet()) {
			for (String s : nodes.keySet()) {
				if (key.equals(s))
					continue;
				if (nodes.get(key).checkViable(nodes.get(s)))
					count_viable++;
			}
		}
		return count_viable;
	}

	public static void main(String[] argvs) {
		Star43 pgm = new Star43();
		System.out.println(pgm.countNumViableNodes("Star43_input.txt"));
	}
}
