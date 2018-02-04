import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Star33 {
	
	public String getMD5Hash(String passcode) {
		// Get the MD5 hash
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(passcode.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			// Get the first 4 characters
			return hashtext.substring(0, 4);
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	// Function that returns an ArrayList of coordinates that can be moved to
	public ArrayList<int[]> getPossibleMovements(String passcode, int x, int y) {
		// Index 0 = Up, Index 1 = Down, Index 2 = Left, Index 3 = Right
		ArrayList<int[]> can_move = new ArrayList<int[]>();
		String fourhash = getMD5Hash(passcode);
		
		// Add the coordinates if door is opened, null otherwise
		for(int i = 0; i < 4; i++) {
			char character = fourhash.charAt(i);
			if (character == 'b' || character == 'c' || character == 'd' || character == 'e' || character == 'f')
				if (i == 0 && y - 1 > -1)
					can_move.add(new int[]{x, y-1});
				else if (i == 1 && y + 1 < 4)
					can_move.add(new int[]{x, y+1});
				else if (i == 2 && x - 1 > -1)
					can_move.add(new int[]{x-1, y});
				else if (i == 3 && x + 1 < 4)
					can_move.add(new int[]{x+1, y});
				else
					can_move.add(null);
			else
				can_move.add(null);
		}
		return can_move;
	}

	// Function that returns the shortest path that goes to the vault
	public String getShortestPath(String passcode, int x, int y) {
		// Perform a BFS
		Queue<String[]> queue = new LinkedList<String[]>();
		queue.add(new String[]{passcode, "" + x, "" + y});

		// Continue doing this until there are no more paths
		while (queue.size() > 0) {
			String[] temp = queue.poll();
			int temp_x = Integer.parseInt(temp[1]);
			int temp_y = Integer.parseInt(temp[2]);
			
			// Exit and return if path is found
			if (temp_x == 3 && temp_y == 3)
				return temp[0];

			// Find the possible movements, and add the appropriate String[] to the queue with the new coordinates and passcode
			ArrayList<int[]> can_move = getPossibleMovements(temp[0], temp_x, temp_y);
			for (int i = 0; i < 4; i++) {
				int[] xymovement = can_move.get(i);

				if (xymovement != null) {
					if (i == 0)
						queue.add(new String[] {temp[0] + "U", "" + xymovement[0], "" + xymovement[1]});
					else if (i == 1)
						queue.add(new String[] {temp[0] + "D", "" + xymovement[0], "" + xymovement[1]});
					else if (i == 2)
						queue.add(new String[] {temp[0] + "L", "" + xymovement[0], "" + xymovement[1]});
					else
						queue.add(new String[] {temp[0] + "R", "" + xymovement[0], "" + xymovement[1]});
				}
			}
		}
		return passcode;
	}

	public static void main(String[] argvs) {
		Star33 pgm = new Star33();
		System.out.println("The shortest path found = " + pgm.getShortestPath("ioramepc", 0, 0).replaceFirst("ioramepc",  ""));
	}
}
