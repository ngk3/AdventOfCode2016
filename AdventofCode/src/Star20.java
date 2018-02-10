import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Star20 {
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
	public void perform() {
		for (String i : instructions) {
			String [] i_splitted = i.split("-");
			String[] new_places = robots.get(i_splitted[0]).give_microchip(i_splitted[1]);
			if (new_places.length == 0)
				continue;
			ArrayList<String> continuing = new ArrayList<String>();
			continuing.add(new_places[0]);
			continuing.add(new_places[1]);
			
			while (continuing.size() > 0) {
				String[] toRobot = continuing.get(0).split("-");
				
				if (toRobot[0].equals("output")) {
					if (!outputs.containsKey(toRobot[1]))
						outputs.put(toRobot[1], toRobot[2]);
					continuing.remove(0);
					continue;
				}
				for (String s : robots.get(toRobot[1]).give_microchip(toRobot[2]))
					continuing.add(s);
					
				continuing.remove(0);
			}
		}
	}
	
	// Function used to get the multiplication of the chips in outputs 0, 1, 2
	public int getOutputs012() {
		return Integer.parseInt(outputs.get("0")) * Integer.parseInt(outputs.get("1")) * Integer.parseInt(outputs.get("2"));
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
	Star20 pgm = new Star20();
	pgm.readFile("Star19_input.txt");
	pgm.perform();
	System.out.println("Multiplication of outputs 0, 1, 2 = " + pgm.getOutputs012());
}

}
