
public class Star25 {
static int[][] map;
	
	public static int calcBinary(int calculation) {
		String binary = Integer.toBinaryString(calculation);
		int ones = 0;
		for (String b : binary.split(""))
			if (b.equals("1"))
				ones++;
		return ones;
	}
	
	public static void generateMap(int x, int y, int fav_num) {
		map = new int[x][y];
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				int calculation = (i*i) + (3*i) + (2*i*j) + j + (j*j);
				calculation += fav_num;
				map[i][j] = calculation;
			}
		}
		
		for(int i = 0; i < x; i++)
			for (int j = 0; j < y; j++)
				if (calcBinary(map[i][j]) % 2 == 0)
					map[i][j] = 0;
				else
					map[i][j] = 1;		
	}

	public static void printMap() {
		for(int i = 0; i < map[0].length; i++) {
			System.out.print(i + " ");
			for(int j = 0; j < map.length; j++) {
				if (map[j][i] == 0)
					System.out.print(".");
				else
					System.out.print("#");
			}
			System.out.println();
		}
	}
	
	public static String[] get_neighbors(String pos) {
		String[] pos_splitted = pos.split(",");
    	int x = Integer.parseInt(pos_splitted[0]);
    	int y = Integer.parseInt(pos_splitted[1]);
    	String[] around = {(x+1) + "," + y, (x-1) + "," + y, x + "," + (y+1), x + "," + (y - 1)};
    	return around;
	}
	
	public static boolean check_valid(String pos) {
		return true;
	}
	
	public static ArrayList<String> bfs(String start, String end, ArrayList<String> path) {
        if (start.equals(end)) {
            path.add(start);
            return path;
        }

        Queue<String> queue = new LinkedList<String>();
        Queue<String> visited = new LinkedList<String>();
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            String pos = queue.poll();
            if (!visited.contains(pos)) {
            	visited.offer(pos);

            	String[] around = get_neighbors(pos);
            
            	int index = 0;
            	while (index != 4) {
            		String next_place = around[index];

            		path.add(next_place);
            		path.add(pos);

            		if (next_place.equals(end)) {
            			return path;
            		} else {
                    	if (!visited.contains(next_place) && check_valid(next_place)) {
                    		queue.offer(next_place);
                    	}
            		}
            		index++;
            	}
            }
        }
        return null;
	}
	
	public void testMain(Object[] args) 
	{	
		generateMap(10, 7, 10);
		printMap();
		ArrayList<String> path = bfs("1,1","7,4");
		for(String s : path)
			System.out.println(s);
		//generateMap(50, 50, 1362);
		//printMap();
	}
}
