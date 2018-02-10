import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Star19 {
	// Variables used to store the instructions, outputs, and robots
	private ArrayList<String> instructions;
	private HashMap<String, Robot> robots = new HashMap<String, Robot>();
	private HashMap<String, String> outputs = new HashMap<String, String>();
	
	// Function that reads the file and get the instructions and robots
	public void readFile(String file_name) {
		instructions = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while ((line = br.readLine()) != null) {
				String [] line_splitted = line.split(" ");
				if (line_splitted[0].equals("value"))
					instructions.add(line_splitted[5] + "-" + line_splitted[1]);
				else
					robots.put(line_splitted[1], new Robot(line_splitted[1], 
								new String[] {line_splitted[5],line_splitted[6]}, 
								new String[] {line_splitted[10], line_splitted[11]}));
			}
			br.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	// Function that performs the instructions in the file
	public String perform() {
		String bot_num = "";
		for (String i : instructions) {
			String [] i_splitted = i.split("-");
			String[] new_places = robots.get(i_splitted[0]).give_microchip(i_splitted[1]);
			if (new_places.length == 0)
				continue;
			ArrayList<String> continuing = new ArrayList<String>();
			continuing.add(new_places[0]);
			continuing.add(new_places[1]);
			
			if (check16_17(continuing))
				bot_num = i_splitted[0];
			while (continuing.size() > 0) {
				String[] toRobot = continuing.get(0).split("-");
				int tracker = 0;
				if (toRobot[0].equals("output")) {
					if (!outputs.containsKey(toRobot[1]))
						outputs.put(toRobot[1], toRobot[2]);
					continuing.remove(0);
					continue;
				}
				for (String s : robots.get(toRobot[1]).give_microchip(toRobot[2])) {
					continuing.add(s);
					tracker += 1;
				}
				if (tracker > 0 && check16_17(continuing))
					bot_num = toRobot[1];
					
				continuing.remove(0);
			}
		}
		return bot_num;
	}
	
	// Function that sees if the robot is checking the indicated values
	public boolean check16_17(ArrayList<String> continuing) {
		int first = Integer.parseInt(continuing.get(continuing.size() - 2).split("-")[2]);
		int second = Integer.parseInt(continuing.get(continuing.size() - 1).split("-")[2]);
		String comparing = Math.min(first,second) + "," + Math.max(first,second);
		if (comparing.equals("17,61"))
			return true;
		return false;
	}

class Robot {
	private String name;
	private ArrayList<Integer> microchips;
	private HashMap<String, String[]> low_high;
	
	public Robot(String n, String[] low, String[] high) {
		this.name = n;
		this.microchips = new ArrayList<Integer>();
		this.low_high = new HashMap<String, String[]>();
		this.low_high.put("low", low);
		this.low_high.put("high", high);
	}
	
	public String[] give_microchip(String value) {
		microchips.add(Integer.parseInt(value));
		if (microchips.size() == 2)
			 return distribute();
		return new String[0];
	}
	
	private String[] distribute() {
		String[] returning = new String[2];
		returning[0] = low_high.get("low")[0] + "-" + low_high.get("low")[1] + "-" + Math.min(microchips.get(0), microchips.get(1));
		returning[1] = low_high.get("high")[0] + "-" + low_high.get("high")[1] + "-" + Math.max(microchips.get(0), microchips.get(1));
		microchips.clear();
		return returning;
	}
	
	public String getName() {return name;}
	
	public String getMicrochips() {return microchips.toString().replace("[", "").replace("]", "");}
}

public static void main(String[] args) {
	Star19 pgm = new Star19();
	pgm.readFile("Star19_input.txt");
	System.out.println("Number of the bot that is responsible for comparing value-61 and value-71 microchips = " + pgm.perform());
}

}
